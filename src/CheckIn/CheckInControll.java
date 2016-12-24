package CheckIn;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import History.HistoryList;
import Place.PlaceControll;
import com.models.DBConnection;
import com.models.UserModel;
import com.mysql.jdbc.Statement;

public class CheckInControll {

	public static CheckInModel addNewCheckIn(CheckInModel checkIn)
			throws SQLException {
		Connection conn = DBConnection.getActiveConnection();
		String sql = "Insert into check_ins (`check_in_user_id`,`check_in_place_id`,`check_in_date`,`check_in_description`,`number_of_likes`,`number_of_comments` ) VALUES  (?,?,?,?,?,?) ";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

		stmt.setString(1, String.valueOf(checkIn.getCheck_in_user_id()));
		stmt.setString(2, String.valueOf(checkIn.getCheck_in_place_id()));
		stmt.setString(3, checkIn.getDate());
		stmt.setString(4, checkIn.getDescription());
		stmt.setString(5, String.valueOf(checkIn.getNumberOfLikes()));
		stmt.setString(6, String.valueOf(checkIn.getNumberOfComments()));
		stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		checkIn.setCheck_in_id(rs.getInt(1));
		PlaceControll.increasePlaceCheckIns(checkIn.getCheck_in_place_id());
		HistoryList
				.AddToHistory(checkIn.getCheck_in_user_id(), rs.getInt(1), 1);

		return checkIn;
	}

	public static void DeleteCheckin(int id) throws SQLException {
		Connection conn = DBConnection.getActiveConnection();
		String sql = "select * from check_ins where `check_in_id` = ? ";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		CheckInModel obj = new CheckInModel();
		obj.setCheck_in_id(rs.getInt(1));
		obj.setCheck_in_user_id(rs.getInt(2));
		obj.setCheck_in_place_id(rs.getInt(3));
		obj.setDate(rs.getString(4));
		obj.setDescription(rs.getString(5));
		obj.setNumberOfLikes(6);
		obj.setNumberOfComments(7);
		sql = "select `number_of_checkins` from places where `id` = ? ";
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, obj.getCheck_in_place_id());
		rs = stmt.executeQuery();
		rs.next();
		int numofcheckins = rs.getInt(1);
		sql = "Update places set `number_of_checkins` = ? where `id` = ?";
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, numofcheckins - 1);
		stmt.setInt(2, obj.getCheck_in_place_id());
		stmt.executeUpdate();
		sql = "delete from comments where `comment_check_in_id` = ? ";
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, obj.getCheck_in_id());
		stmt.executeUpdate();
		sql = "delete from pepole_likes_check_in where `like_check_in_id` = ? ";
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, obj.getCheck_in_id());
		stmt.executeUpdate();
		sql = "delete from check_ins where `check_in_id` = ? ";
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, obj.getCheck_in_id());
		stmt.executeUpdate();
		sql = "delete from notification where `check_in_id` = ? ";
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, obj.getCheck_in_id());
		stmt.executeUpdate();
		//HistoryList.DeleteAction(id, 1);
	}

	public static ArrayList<CheckInModel> getUserCheckIns(int userId)
			throws SQLException {
		Connection conn = DBConnection.getActiveConnection();
		String sql = "select * from users inner join check_ins where  check_ins.check_in_user_id= ? and check_ins.check_in_user_id=users.id";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, userId);
		ResultSet rs = stmt.executeQuery();
		ArrayList<CheckInModel> Result = new ArrayList<CheckInModel>();

		while (rs.next()) {
			CheckInModel r = new CheckInModel();

			r.setCheck_in_id(rs.getInt("check_in_id"));
			r.setCheck_in_place_id(rs.getInt("check_in_place_id"));
			r.setCheck_in_user_id(rs.getInt("check_in_user_id"));
			r.setNumberOfComments(rs.getInt("number_of_comments"));
			r.setNumberOfLikes(rs.getInt("number_of_likes"));
			r.setDate(rs.getString("check_in_date"));
			r.setDescription(rs.getString("check_in_description"));
			UserModel check_in_user = new UserModel();
			check_in_user.setEmail(rs.getString("email"));
			check_in_user.setName(rs.getString("name"));
			r.setCheck_in_user(check_in_user);
			r.setMyPlace(PlaceControll.GetPlace(rs.getInt("check_in_place_id")));
			Result.add(r);
		}

		return Result;
	}

	public static CheckInModel getCheckIn(int checkInId) throws SQLException {
		Connection conn = DBConnection.getActiveConnection();
		String sql = "select * from users inner join check_ins where  check_ins.check_in_id= ? and check_ins.check_in_user_id=users.id;";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, checkInId);
		ResultSet rs = stmt.executeQuery();

		rs.next();

		CheckInModel r = new CheckInModel();

		r.setCheck_in_id(rs.getInt("check_in_id"));
		r.setCheck_in_place_id(rs.getInt("check_in_place_id"));
		r.setCheck_in_user_id(rs.getInt("check_in_user_id"));
		r.setNumberOfComments(rs.getInt("number_of_comments"));
		r.setNumberOfLikes(rs.getInt("number_of_likes"));
		r.setDate(rs.getString("check_in_date"));
		r.setDescription(rs.getString("check_in_description"));
		UserModel check_in_user = new UserModel();
		check_in_user.setEmail(rs.getString("email"));
		check_in_user.setName(rs.getString("name"));
		r.setCheck_in_user(check_in_user);
		r.setMyPlace(PlaceControll.GetPlace(rs.getInt("check_in_place_id")));

		return r;

	}

	public static ArrayList<CheckInModel> getMyFollowersCheckIns(int userId)
			throws SQLException {
		ArrayList<CheckInModel> Result = new ArrayList<CheckInModel>();
		ArrayList<UserModel> user_List = new ArrayList<UserModel>();
		user_List = UserModel.Getfollowers(userId);
		for (int i = 0; i < user_List.size(); i++) {
			Result.addAll(getUserCheckIns(user_List.get(i).getId()));
		}

		return Result;
	}

}
