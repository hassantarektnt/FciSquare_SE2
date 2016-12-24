package Place;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import History.HistoryList;

import com.models.DBConnection;
import com.mysql.jdbc.Statement;

public class PlaceControll {

	public PlaceModel AddNewPlace(PlaceModel place) {

		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from places where `name` = ? ";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, place.name);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				return null;
			} else {
				sql = "Insert into places (`name`,`description`,`long`,`lat` , `saving_requests` , `number_of_checkins` , `rate` ) VALUES  (?,?,?,?,?,?,?)";
				stmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, place.name);
				stmt.setString(2, place.desc);
				stmt.setDouble(3, place.latitude);
				stmt.setDouble(4, place.longitude);
				stmt.setInt(5, 0);
				stmt.setInt(6, 0);
				stmt.setDouble(7, 0.0);
				stmt.executeUpdate();
				rs = stmt.getGeneratedKeys();
				if (rs.next()) {
					place.id = rs.getInt(1);
					return place;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	public boolean SavePlace(int user_id, String name) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select id , saving_requests from places where `name` = ? ";

			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, name);

			ResultSet rs = stmt.executeQuery();
			int place_id;
			int req;
			if (rs.next()) {
				place_id = rs.getInt(1);
				req = rs.getInt(2);
			} else
				return false;
			sql = "Select saved_places_ID from saved_places where `places_ID` = ? and  `user_ID` = ? ";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, String.valueOf(place_id));
			stmt.setString(2, String.valueOf(user_id));
			rs = stmt.executeQuery();
			if (rs.next()) {
				return false;
			}

			else {
				sql = "Insert into saved_places (`user_ID`,`places_ID` ) VALUES  (?,?)";
				stmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, String.valueOf(user_id));
				stmt.setString(2, String.valueOf(place_id));
				stmt.executeUpdate();
				 rs = stmt.getGeneratedKeys();
				 rs.next() ;
				int id = rs.getInt(1) ;
				sql = "Update places set `saving_requests` = ? where `id` = ? ";
				stmt = conn.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				stmt.setString(1, String.valueOf(req + 1));
				stmt.setString(2, String.valueOf(place_id));
				stmt.executeUpdate();
				HistoryList.AddToHistory(user_id,id,4) ;
				return true;
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public void DeleteSavedPlace ( int placeid , int userid ) throws SQLException {
		Connection conn = DBConnection.getActiveConnection();
		String sql = "select saved_places_ID from saved_places where `places_ID` = ? and `user_ID` = ?" ;
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, placeid);
		stmt.setInt(2,userid);
		ResultSet rs = stmt.executeQuery();
		int id ;
		rs.next() ; id = rs.getInt("saved_places_ID") ;
		 sql = "delete from saved_places where `places_ID` = ? and `user_ID` = ? " ;
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, placeid);
		stmt.setInt(2,userid);
		stmt.executeUpdate();
		sql = "select `saving_requests` from places where `id` = ? " ;
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, placeid);
	    rs = stmt.executeQuery();
		rs.next() ;
		int numofrequests = rs.getInt(1) ;
		sql = "Update places set `saving_requests` = ? where `id` = ?  " ;
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1,(numofrequests-1));
		stmt.setInt(2,placeid);
		stmt.executeUpdate();
		System.out.println(id);
		HistoryList.DeleteAction(id,4);
	}
	
	public ArrayList<PlaceModel> GetSavedPlaceList(int id) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "select places_ID from saved_places where `user_ID` = ? ";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			ArrayList<Integer> Result = new ArrayList<Integer>();

			int check = 0;

			while (rs.next()) {
				check = 1;
				Result.add(rs.getInt(1));
			}

			if (check == 0) {
				return null;
			}

			ArrayList<PlaceModel> places = new ArrayList<PlaceModel>();

			for (int i = 0; i < Result.size(); i++) {
				sql = "select * from places where `id` = ? ";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, Result.get(i));
				rs = stmt.executeQuery();
				PlaceModel place = new PlaceModel();
				if (rs.next()) {
					place.id = rs.getInt(1);
					place.name = rs.getString("name");
					place.desc = rs.getString("description");
					place.latitude = Double.valueOf(rs.getString(4));
					place.longitude = Double.valueOf(rs.getString(5));
					place.numOfReq = rs.getInt(6);
					place.numOfCheckIns = rs.getInt(7);
					place.rate = rs.getDouble(8);
					places.add(place);
				}
			}
			return places;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public ArrayList<PlaceModel> GetAllPlaces() throws SQLException {
		Connection conn = DBConnection.getActiveConnection();
		String sql = "select * from places ";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		ArrayList<PlaceModel> places = new ArrayList<PlaceModel>();

		while (rs.next()) {
			PlaceModel obj = new PlaceModel();
			obj.id = rs.getInt(1);
			obj.name = rs.getString(2);
			obj.desc = rs.getString(3);
			obj.latitude = rs.getDouble(4);
			obj.longitude = rs.getDouble(5);
			obj.numOfReq = rs.getInt(6);
			obj.numOfCheckIns = rs.getInt(7);
			obj.rate = rs.getDouble(8);
			places.add(obj);
		}

		return places;
	}

	public static PlaceModel GetPlace(int id) throws SQLException {
		Connection conn = DBConnection.getActiveConnection();
		String sql = "select * from places where `id` = ? ";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		PlaceModel obj = new PlaceModel();

		if (rs.next()) {
			obj.id = rs.getInt(1);
			obj.name = rs.getString(2);
			obj.desc = rs.getString(3);
			obj.latitude = rs.getDouble(4);
			obj.longitude = rs.getDouble(5);
			obj.numOfReq = rs.getInt(6);
			obj.numOfCheckIns = rs.getInt(7);
			obj.rate = rs.getDouble(8);
		}

		return obj;

	}

	public static void increasePlaceCheckIns(int place_id) throws SQLException {
		Connection conn = DBConnection.getActiveConnection();
		String sql = "UPDATE places SET number_of_checkins = (number_of_checkins+1) where id = ? ;";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		stmt.setString(1, String.valueOf(place_id));
		stmt.executeUpdate();
	}
}
