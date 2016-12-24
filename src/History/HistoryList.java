package History;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.models.DBConnection;
import com.services.Services;
public class HistoryList {
	
	
	public static String DispalyHistory ( int userid ) throws SQLException {
		Connection conn = DBConnection.getActiveConnection();
		String sql = "select * from history where `userid` = ?";
		PreparedStatement stmt ;
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, userid);
		ResultSet rs = stmt.executeQuery();
		String total = "" ;
		while (rs.next()) {
			int actionid = rs.getInt(3) ;
			int type = rs.getInt(4) ;
			Services obj = new Services () ;
			if (type == 4 ) {
			total += obj.GetPlace(actionid)+" " ;
			}
			else if (type == 1 ) total+=obj.getCheckIn(actionid)+" " ;
			else if (type == 2 ) {
				sql = "select * from comments where `comment_id` = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, actionid);
				ResultSet rs1 = stmt.executeQuery();
				rs1.next() ;
				total += obj.getCheckIn(rs1.getInt(2))+" " ;
			}
			else {
				sql = "select * from pepole_likes_check_in where `like_id` = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, actionid);
				ResultSet rs2 = stmt.executeQuery();
				rs2.next() ;
				total += obj.getCheckIn(rs2.getInt(3))+" " ;
			}
		}
		return total ;
	}
	
	public static void AddToHistory ( int userid , int actionid , int type ) throws SQLException{
		Connection conn = DBConnection.getActiveConnection();
		String sql = "Insert into history (`userid`,`actionid`,`type`) VALUES  (?,?,?)";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, userid);
		stmt.setInt(2,actionid);
		stmt.setInt(3,type);
		stmt.executeUpdate();
	}
		
	public static void DeleteAction (int actionid , int type ) throws SQLException{
		Connection conn = DBConnection.getActiveConnection();
		String sql = "delete from history where `actionid` = ? and `type` = ? " ;
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, actionid);
		stmt.setInt(2,type);
		stmt.executeUpdate();
	}

}
