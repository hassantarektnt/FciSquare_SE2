package notification;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.models.DBConnection;
import com.mysql.jdbc.Statement;

public class LikeObserver extends NotificationObserver {

	@Override
	public boolean AddNotification( NotificationModel obj ) {
		
		try {
			obj.desc = (obj.name+" Likes your check in ") ;
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Insert into notification (`check_in_id`,`user1_ID`,`user2_ID` , `description` , `type` , `actionid`) VALUES  (?,?,?,?,?,?)";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setInt(1, obj.checkinid);
			stmt.setInt(2,obj.userid);
			stmt.setInt(3,obj.userid1);
			stmt.setString(4,obj.desc );
			stmt.setInt(5,obj.type);
			stmt.setInt(6,obj.getActionid()) ;
			
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
		
		return false;
	}

}
