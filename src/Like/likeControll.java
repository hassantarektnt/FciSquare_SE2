package Like;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import History.HistoryList;
import com.models.DBConnection;
import com.mysql.jdbc.Statement;

public class likeControll {

	public static like DeleteLike(int user_id, int check_in_id)
			throws SQLException {
		like like1 = new like();
		like1.setLike_check_in_id(check_in_id);
		like1.setUser_like_id(user_id);
		if (likeControll.checkLike(like1)) {
			like1.setLike_id(-1);
			return like1;
		}

		Connection conn = DBConnection.getActiveConnection();
		String sql = "select * FROM pepole_likes_check_in WHERE `user_like_id` = ? and `like_check_in_id` = ?";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, String.valueOf(user_id));
		stmt.setString(2, String.valueOf(check_in_id));
		ResultSet rs = stmt.executeQuery();
		rs.next();
		int id = rs.getInt(1);
		sql = "delete FROM pepole_likes_check_in WHERE `user_like_id` = ? and `like_check_in_id` = ?";
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, String.valueOf(user_id));
		stmt.setString(2, String.valueOf(check_in_id));
		stmt.executeUpdate();
		sql = "UPDATE check_ins SET number_of_likes = (number_of_likes-1) where check_in_id = ? ;";
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, String.valueOf(check_in_id));
		stmt.executeUpdate();
		like1.setLike_id(0);
		sql = "select *from notification where `actionid` = ? and `type` = ?";
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, id);
		stmt.setInt(2, 1);
		rs = stmt.executeQuery();
		if (rs.next() == true) {
			sql = "delete FROM notification WHERE `actionid` = ? and `type` = ?";
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, id);
			stmt.setInt(2, 1);
			stmt.executeUpdate();
		}
		HistoryList.DeleteAction(id, 3);

		return like1;

	}

	public static like addlike(like like1) throws SQLException {

		if (!likeControll.checkLike(like1)) {
			like1.setLike_id(0);
			return like1;
		}

		Connection conn = DBConnection.getActiveConnection();
		String sql = "Insert into pepole_likes_check_in (`user_like_id`,`like_check_in_id` ) VALUES  (?,?) ";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		stmt.setString(1, String.valueOf(like1.getUser_like_id()));
		stmt.setString(2, String.valueOf(like1.getLike_check_in_id()));

		stmt.executeUpdate();

		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		like1.setLike_id(rs.getInt(1));
		likeControll.increaseLikesNum(like1.getLike_check_in_id());
		HistoryList.AddToHistory(like1.getUser_like_id(), rs.getInt(1), 3);
		return like1;
	}

	public static boolean checkLike(like like1) throws SQLException {
		Connection conn = DBConnection.getActiveConnection();
		String sql = "SELECT * FROM pepole_likes_check_in;";
		java.sql.Statement statement = conn.createStatement();
		ResultSet res = statement.executeQuery(sql);

		while (res.next()) {

			if (res.getInt("user_like_id") == like1.getUser_like_id()
					&& res.getInt("like_check_in_id") == like1
							.getLike_check_in_id()) {
				return false;
			}

		}

		return true;
	}

	public static void increaseLikesNum(int checkInId) throws SQLException {
		Connection conn = DBConnection.getActiveConnection();
		String sql = "UPDATE check_ins SET number_of_likes = (number_of_likes+1) where check_in_id = ? ;";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, String.valueOf(checkInId));
		stmt.executeUpdate();

	}

}
