 package notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.models.DBConnection;
import com.mysql.jdbc.Statement;

public class CommentObserver extends NotificationObserver {

	@Override
	public boolean AddNotification(NotificationModel obj ) {
		try {
			obj.desc = (obj.name+" Comment on your check in ") ;
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Insert into notification (`check_in_id`,`user1_ID`,`user2_ID` , `description` , `type` , `actionid`) VALUES  (?,?,?,?,?,?)";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, String.valueOf(obj.checkinid));
			stmt.setString(2,String.valueOf(obj.userid) );
			stmt.setString(3, String.valueOf(obj.userid1));
			stmt.setString(4,obj.desc );
			stmt.setString(5,String.valueOf(obj.type) );
			stmt.setInt(6,obj.actionid);
			
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				return true ;
			}
			return false ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean Action(int checkinid) {
		// TODO Auto-generated method stub
		return false;
	}

}
