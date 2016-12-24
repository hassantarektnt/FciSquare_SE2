package Comment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import History.HistoryList;

import com.models.DBConnection;
import com.models.UserModel;
import com.mysql.jdbc.Statement;

public class CommentControll {
	public static void increaseCommentsNum(int checkInId) throws SQLException {
		Connection conn = DBConnection.getActiveConnection();
		String sql = "UPDATE check_ins SET number_of_comments = (number_of_comments+1) where check_in_id = ? ;";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, String.valueOf(checkInId));
		stmt.executeUpdate();

	}

	public static ArrayList<Comment> getCheckInComments(int check_in_id)
			throws SQLException {
		ArrayList<Comment> comments = new ArrayList<Comment>();
		Connection conn = DBConnection.getActiveConnection();
		String sql = "select * from check_ins inner join comments where  check_ins.check_in_id= ? and check_ins.check_in_id= comments.comment_check_in_id";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, check_in_id);
		ResultSet rs = stmt.executeQuery();

		while (rs.next()) {
			Comment c = new Comment(rs.getInt("comment_check_in_id"),
					rs.getInt("comment_user_id"),
					rs.getString("comment_description"));
			c.setComment_date(rs.getString("comment_date"));
			c.setComment_id(rs.getInt("comment_id"));
			c.setComment_user(UserModel.getUser(c.getComment_user_id()));
			comments.add(c);

		}

		return comments;
	}

	public static Comment addComment(Comment comment) throws SQLException {
		Connection conn = DBConnection.getActiveConnection();
		String sql = "Insert into comments (`comment_check_in_id`,`comment_user_id`,`comment_date`,`comment_description` ) VALUES  (?,?,?,?) ";
		PreparedStatement stmt;

		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, String.valueOf(comment.getComment_check_in_id()));
		stmt.setString(2, String.valueOf(comment.getComment_user_id()));
		stmt.setString(3, comment.getComment_date());
		stmt.setString(4, comment.getComment_description());
		stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();
		rs.next();
		comment.setComment_id(rs.getInt(1));
		CommentControll.increaseCommentsNum(comment.getComment_check_in_id());
		HistoryList.AddToHistory(comment.getComment_user_id(), rs.getInt(1), 2);
		return comment;
	}

	public static void DeleteComment(int id) throws SQLException {
		Connection conn = DBConnection.getActiveConnection();
		String sql = "select `comment_check_in_id` from comments where `comment_id` = ? ";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, id);
		System.out.println(id);
		ResultSet rs = stmt.executeQuery();
		rs.next();
		int checkinid = rs.getInt("comment_check_in_id");
		sql = "select `number_of_comments` from check_ins where `check_in_id` = ? ";
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, checkinid);
		rs = stmt.executeQuery();
		rs.next();
		int numofcomments = rs.getInt(1);
		sql = "Update check_ins set `number_of_comments` = ? where `check_in_id` = ?";
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, (numofcomments - 1));
		stmt.setInt(2, checkinid);
		stmt.executeUpdate();
		sql = "delete FROM comments WHERE `comment_id` = ? ";
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, id);
		stmt.executeUpdate();
		sql = "select * from notification where `actionid` = ? and `type` = ?";
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setInt(1, id);
		stmt.setInt(2, 2);
		rs = stmt.executeQuery();
		if (rs.next() == true) {
			sql = "delete FROM notification WHERE `actionid` = ? and `type` = ?";
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, id);
			stmt.setInt(2, 2);
			stmt.executeUpdate();
		}
		//HistoryList.DeleteAction(id, 2);
	}

}
