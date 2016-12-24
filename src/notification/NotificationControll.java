package notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.models.DBConnection;

public class NotificationControll {
		
	ArrayList <NotificationObserver> list = new ArrayList <NotificationObserver> () ;
	
	public ArrayList<NotificationModel> GetNotifications ( int id ) {
		
		
		try {
			Connection conn = DBConnection.getActiveConnection();			
			String sql = "select * from notification where `user1_ID` = ? ";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
	     	stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			
			ArrayList <NotificationModel> notifications = new ArrayList <NotificationModel> () ;
			
			while (rs.next()) {
				NotificationModel obj = new NotificationModel () ;
				obj.setId(rs.getInt(1));
				obj.setCheckinid(rs.getInt(2));
				obj.setUserid(rs.getInt(3));
				obj.setUserid1(rs.getInt(4));
				obj.setDesc(rs.getString(5));
				obj.setType(rs.getInt(6));
				obj.setActionid(rs.getInt(7));
				notifications.add(obj) ;
			}
			
			return notifications ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		
	}
	
	public void DeleteNotification ( int notifid ) throws SQLException {
		Connection conn = DBConnection.getActiveConnection();
		String sql = "delete from notification where `id` = ?" ;
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, notifid);
		stmt.executeUpdate();
	}
	
	
}
