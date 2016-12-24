package com.models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import CheckIn.CheckInModel;

import com.mysql.jdbc.Statement;

public class UserModel {

	private String name;
	private String email;
	private String pass;
	private Integer id;
	private Double lat;
	private Double lon;
	ArrayList<CheckInModel> checkIns = new ArrayList<CheckInModel>();

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}

	public static boolean FollowUser(String email, String ID) {

		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from users where `email` = ? ";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("id") == null) {
					return false;
				} else {
					sql = "Insert into follower_relation (`user_ID`,`follower_ID`) VALUES  (?,?)";
					stmt = conn.prepareStatement(sql,
							Statement.RETURN_GENERATED_KEYS);
					stmt.setString(1, ID);
					stmt.setString(2, rs.getString("id"));
					stmt.executeUpdate();
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static ArrayList<UserModel> GetUsers() throws SQLException {
		Connection conn = DBConnection.getActiveConnection();
		String sql = "select * from users";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		ResultSet rs = stmt.executeQuery();
		ArrayList<UserModel> Result = new ArrayList<UserModel>();
		while (rs.next()) {
			UserModel user = new UserModel();
			user.id = rs.getInt(1);
			user.email = rs.getString("email");
			user.pass = rs.getString("password");
			user.name = rs.getString("name");
			user.lat = rs.getDouble("lat");
			user.lon = rs.getDouble("long");
			Result.add(user);
		}

		return Result;

	}

	public static boolean UnFollow(String email, String ID) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from users where `email` = ? ";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				if (rs.getString("id") == null) {
					System.out.println("wefwefw");
					return false;
				} else {
					sql = "delete FROM follower_relation WHERE (`user_ID` = ? and `follower_ID` = ? ) or (`user_ID` = ? and `follower_ID` = ? )";
					stmt = conn.prepareStatement(sql,
							Statement.RETURN_GENERATED_KEYS);
					stmt.setString(1, ID);
					stmt.setString(2, rs.getString("id"));
					stmt.setString(3, rs.getString("id"));
					stmt.setString(4, ID);
					stmt.executeUpdate();
					return true;
				}
			}
			return false;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	public static UserModel addNewUser(String name, String email, String pass) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Insert into users (`name`,`email`,`password`) VALUES  (?,?,?)";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, name);
			stmt.setString(2, email);
			stmt.setString(3, pass);
			stmt.executeUpdate();
			ResultSet rs = stmt.getGeneratedKeys();
			if (rs.next()) {
				UserModel user = new UserModel();
				user.id = rs.getInt(1);
				user.email = email;
				user.pass = pass;
				user.name = name;
				user.lat = 0.0;
				user.lon = 0.0;
				return user;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static UserModel login(String email, String pass) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from users where `email` = ? and `password` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, email);
			stmt.setString(2, pass);
			ResultSet rs = stmt.executeQuery();
			if (rs.next()) {
				UserModel user = new UserModel();
				user.id = rs.getInt(1);
				user.email = rs.getString("email");
				user.pass = rs.getString("password");
				user.name = rs.getString("name");
				user.lat = rs.getDouble("lat");
				user.lon = rs.getDouble("long");
				return user;
			}
			return null;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static boolean updateUserPosition(Integer id, Double lat, Double lon) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Update users set `lat` = ? , `long` = ? where `id` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setDouble(1, lat);
			stmt.setDouble(2, lon);
			stmt.setInt(3, id);
			stmt.executeUpdate();
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static ArrayList<UserModel> Getfollowers(Integer id) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "select * from users inner join follower_relation where  follower_relation.user_ID= ? and follower_relation.follower_ID=users.id";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, id);
			ResultSet rs = stmt.executeQuery();
			ArrayList<UserModel> Result = new ArrayList<UserModel>();

			while (rs.next()) {
				UserModel user = new UserModel();
				user.id = rs.getInt(1);
				user.email = rs.getString("email");
				user.pass = rs.getString("password");
				user.name = rs.getString("name");
				user.lat = rs.getDouble("lat");
				user.lon = rs.getDouble("long");
				Result.add(user);
			}

			return Result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static UserModel Getuserlastposition(String my_id, String email) {
		ArrayList<UserModel> user_List = new ArrayList<UserModel>();
		user_List = UserModel.Getfollowers(Integer.parseInt(my_id));
		for (int i = 0; i < user_List.size(); i++) {
			UserModel user_2 = user_List.get(i);
			if (user_2.getEmail().equals(email)) {
				try {
					Connection conn = DBConnection.getActiveConnection();
					String sql = "Select * from users where `email` = ?";
					PreparedStatement stmt;
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, email);
					ResultSet rs = stmt.executeQuery();
					if (rs.next()) {
						UserModel user = new UserModel();
						user.id = rs.getInt(1);
						user.email = rs.getString("email");
						user.pass = rs.getString("password");
						user.name = rs.getString("name");
						user.lat = rs.getDouble("lat");
						user.lon = rs.getDouble("long");
						return user;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}

		}
		UserModel user = new UserModel();
		user.email = "$$$";
		return user;

	}

	public static UserModel getUser(int id) {
		try {
			Connection conn = DBConnection.getActiveConnection();
			String sql = "Select * from users where `id` = ?";
			PreparedStatement stmt;
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, String.valueOf(id));
			ResultSet rs = stmt.executeQuery();
			rs.next();
			UserModel user = new UserModel();
			user.id = rs.getInt(1);
			user.email = rs.getString("email");
			user.pass = rs.getString("password");
			user.name = rs.getString("name");
			user.lat = rs.getDouble("lat");
			user.lon = rs.getDouble("long");
			return user;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
