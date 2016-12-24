package com.services;

import java.sql.SQLException;
import java.util.ArrayList;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import notification.CommentObserver;
import notification.LikeObserver;
import notification.NotificationControll;
import notification.NotificationModel;
import notification.NotificationObserver;
import org.json.simple.JSONObject;
import CheckIn.CheckInControll;
import CheckIn.CheckInModel;
import Comment.Comment;
import Comment.CommentControll;
import History.HistoryList;
import Like.like;
import Like.likeControll;
import Place.PlaceControll;
import Place.PlaceModel;
import com.models.UserModel;

@Path("/")
public class Services {

	/*
	 * @GET
	 * 
	 * @Path("/signup")
	 * 
	 * @Produces(MediaType.TEXT_HTML) public Response signUp(){ return
	 * Response.ok(new Viewable("/Signup.jsp")).build(); }
	 */

	@SuppressWarnings("unchecked")
	@POST
	@Path("/signup")
	@Produces(MediaType.TEXT_PLAIN)
	public String signUp(@FormParam("name") String name,
			@FormParam("email") String email, @FormParam("pass") String pass) {
		UserModel user = UserModel.addNewUser(name, email, pass);
		JSONObject json = new JSONObject();
		if (user == null)
			json.put("operation", "faild");
		else {
			json.put("id", user.getId());
			json.put("name", user.getName());
			json.put("email", user.getEmail());
			json.put("pass", user.getPass());
			json.put("lat", user.getLat());
			json.put("long", user.getLon());
		}
		return json.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/login")
	@Produces(MediaType.TEXT_PLAIN)
	public String login(@FormParam("email") String email,
			@FormParam("pass") String pass) {
		UserModel user = UserModel.login(email, pass);
		JSONObject json = new JSONObject();
		if (user == null)
			json.put("operation", "faild");
		else {
			json.put("id", user.getId());
			json.put("name", user.getName());
			json.put("email", user.getEmail());
			json.put("pass", user.getPass());
			json.put("lat", user.getLat());
			json.put("long", user.getLon());
		}
		return json.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/followuser")
	@Produces(MediaType.TEXT_PLAIN)
	public String FollowUser(@FormParam("id") String id,
			@FormParam("email") String email) {
		boolean check = UserModel.FollowUser(email, id);
		JSONObject json = new JSONObject();
		if (check == true)
			json.put("operation", "Done");

		else
			json.put("operation", "Faild");
		return json.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/unfollowuser")
	@Produces(MediaType.TEXT_PLAIN)
	public String UnFollowUser(@FormParam("id") String id,
			@FormParam("email") String email) {
		boolean check = UserModel.UnFollow(email, id);
		JSONObject json = new JSONObject();
		if (check == true)
			json.put("operation", "Done");

		else
			json.put("operation", "Faild");
		return json.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/updatePosition")
	@Produces(MediaType.TEXT_PLAIN)
	public String updatePosition(@FormParam("id") String id,
			@FormParam("lat") String lat, @FormParam("long") String lon) {
		Boolean status = UserModel.updateUserPosition(Integer.parseInt(id),
				Double.parseDouble(lat), Double.parseDouble(lon));
		JSONObject json = new JSONObject();
		json.put("status", status ? 1 : 0);
		return json.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/getusers")
	@Produces(MediaType.TEXT_PLAIN)
	public String GetUsers() throws SQLException {
		ArrayList<UserModel> user_List = new ArrayList<UserModel>();
		user_List = UserModel.GetUsers();
		String result = "";

		for (int i = 0; i < user_List.size(); i++) {
			UserModel user = user_List.get(i);
			JSONObject json = new JSONObject();
			json.put("id", user.getId());
			json.put("name", user.getName());
			json.put("email", user.getEmail());
			json.put("lat", user.getLat());
			json.put("long", user.getLon());
			result += json.toJSONString() + " ";
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/getfollowers")
	@Produces(MediaType.TEXT_PLAIN)
	public String Getfollowers(@FormParam("id") String id) {
		ArrayList<UserModel> user_List = new ArrayList<UserModel>();
		user_List = UserModel.Getfollowers(Integer.parseInt(id));
		String result = "";

		for (int i = 0; i < user_List.size(); i++) {
			UserModel user = user_List.get(i);
			JSONObject json = new JSONObject();
			json.put("id", user.getId());
			json.put("name", user.getName());
			json.put("email", user.getEmail());
			json.put("lat", user.getLat());
			json.put("long", user.getLon());
			result += json.toJSONString() + " ";
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/getuserlastposition")
	@Produces(MediaType.TEXT_PLAIN)
	public String Getuserlastposition(@FormParam("id") String id,
			@FormParam("email") String email) {

		UserModel user = UserModel.Getuserlastposition(id, email);
		JSONObject json = new JSONObject();
		json.put("name", user.getName());
		json.put("email", user.getEmail());
		json.put("lat", user.getLat());
		json.put("long", user.getLon());
		if (json.get("email") == "$$$") {
			return "not in followers";
		} else {
			return json.toJSONString();
		}

	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/addnewplace")
	@Produces(MediaType.TEXT_PLAIN)
	public String AddPlace(@FormParam("name") String name,
			@FormParam("desc") String desc, @FormParam("long") double lon,
			@FormParam("lat") double lat) {

		PlaceModel place = new PlaceModel(name, desc, lon, lat);
		PlaceControll con = new PlaceControll();
		place = con.AddNewPlace(place);
		JSONObject json = new JSONObject();
		if (place == null)
			json.put("operation", "faild");
		else {
			json.put("id", place.getId());
			json.put("name", place.getName());
			json.put("desc", place.getDesc());
			json.put("lat", place.getLatitude());
			json.put("long", place.getLongitude());
		}
		return json.toJSONString();

	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/saveplace")
	@Produces(MediaType.TEXT_PLAIN)
	public String SavePlace(@FormParam("userid") int user_id,
			@FormParam("name") String name) {

		PlaceControll con = new PlaceControll();
		boolean check = con.SavePlace(user_id, name);
		JSONObject json = new JSONObject();
		if (check == false) {
			json.put("operation", "faild");
		} else
			json.put("operation", "Done");
		return json.toJSONString();

	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/deletesaveplace")
	@Produces(MediaType.TEXT_PLAIN)
	public String DeleteSavedPlace(@FormParam("userid") int userid,
			@FormParam("placeid") int placeid) throws SQLException {
		PlaceControll obj = new PlaceControll();
		obj.DeleteSavedPlace(placeid, userid);
		JSONObject json = new JSONObject();
		json.put("operation", "Done");
		return json.toJSONString();

	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/savelist")
	@Produces(MediaType.TEXT_PLAIN)
	public String SaveList(@FormParam("userid") int user_id) {
		PlaceControll con = new PlaceControll();
		ArrayList<PlaceModel> places = con.GetSavedPlaceList(user_id);
		String total = new String("");
		JSONObject json = new JSONObject();
		if (places == null) {
			json.put("operation", "faild");
			total = json.toJSONString();
		} else {
			for (int i = 0; i < places.size(); i++) {
				PlaceModel place = new PlaceModel();
				json = new JSONObject();
				place = places.get(i);
				json.put("id", place.getId());
				json.put("name", place.getName());
				json.put("description", place.getDesc());
				json.put("latitude", place.getLatitude());
				json.put("longitude", place.getLongitude());
				json.put("numOfReq", place.getNumOfReq());
				json.put("numOfCheckIns", place.getNumOfCheckIns());
				json.put("rate", place.getRate());
				total += json.toJSONString() + "\n";
			}

		}

		return total;
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/getallplaces")
	@Produces(MediaType.TEXT_PLAIN)
	public String GetAllPlaces() throws SQLException {
		PlaceControll con = new PlaceControll();
		ArrayList<PlaceModel> places = con.GetAllPlaces();
		String total = new String("");
		JSONObject json = new JSONObject();
		for (int i = 0; i < places.size(); i++) {
			PlaceModel place = new PlaceModel();
			json = new JSONObject();
			place = places.get(i);
			json.put("id", place.getId());
			json.put("name", place.getName());
			json.put("description", place.getDesc());
			json.put("latitude", place.getLatitude());
			json.put("longitude", place.getLongitude());
			json.put("numOfReq", place.getNumOfReq());
			json.put("numOfCheckIns", place.getNumOfCheckIns());
			json.put("rate", place.getRate());
			total += json.toJSONString() + "\n";
		}

		return total;
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/getplace")
	@Produces(MediaType.TEXT_PLAIN)
	public String GetPlace(@FormParam("id") int id) throws SQLException {
		PlaceModel place = PlaceControll.GetPlace(id);
		JSONObject json = new JSONObject();

		json = new JSONObject();
		json.put("id", place.getId());
		json.put("name", place.getName());
		json.put("description", place.getDesc());
		json.put("latitude", place.getLatitude());
		json.put("longitude", place.getLongitude());
		json.put("numOfReq", place.getNumOfReq());
		json.put("numOfCheckIns", place.getNumOfCheckIns());
		json.put("rate", place.getRate());
		return json.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/addnotification")
	@Produces(MediaType.TEXT_PLAIN)
	public String AddNotification(@FormParam("checkinid") int checkinid,
			@FormParam("userid") int userid, @FormParam("userid1") int userid1,
			@FormParam("name") String name,
			@FormParam("actionid") int actionid, @FormParam("type") int type) {
		NotificationModel obj = new NotificationModel(checkinid, userid,
				userid1, checkinid, name, type, actionid);
		NotificationObserver notifi = null;
		if (type == 1)
			notifi = new LikeObserver();
		if (type == 2)
			notifi = new CommentObserver();
		boolean check = notifi.AddNotification(obj);
		JSONObject json = new JSONObject();
		if (check == true)
			json.put("operation", "Done");
		else
			json.put("operation", "Done");

		return json.toJSONString();

	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/getnotification")
	@Produces(MediaType.TEXT_PLAIN)
	public String GetNotification(@FormParam("id") int id) {
		NotificationControll notifi = new NotificationControll();
		JSONObject json = new JSONObject();
		ArrayList<NotificationModel> list = notifi.GetNotifications(id);
		String total = new String("");
		for (int i = 0; i < list.size(); i++) {
			json.put("id", list.get(i).getId());
			json.put("description", list.get(i).getDesc());
			json.put("checkinid", list.get(i).getCheckinid());
			total += " " + json.toJSONString();
		}

		return total;

	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/deletenotification")
	@Produces(MediaType.TEXT_PLAIN)
	public String DeleteNotification(@FormParam("id") int id)
			throws SQLException {
		JSONObject json = new JSONObject();
		NotificationControll obj = new NotificationControll();
		obj.DeleteNotification(id);
		json.put("operation", "Done");
		return json.toJSONString();

	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/makeCheckIn")
	@Produces(MediaType.TEXT_PLAIN)
	public String makeCheckIn(@FormParam("user_id") int check_in_user_id,
			@FormParam("place_id") int check_in_place_id,
			@FormParam("description") String check_in_description)
			throws SQLException {

		System.out.println("  " + check_in_user_id);
		CheckInModel checkIn = new CheckInModel(check_in_description,
				check_in_user_id, check_in_place_id);
		CheckInModel check = CheckInControll.addNewCheckIn(checkIn);

		JSONObject json = new JSONObject();

		json.put("check_in_id", check.getCheck_in_id());
		json.put("user_id", check.getCheck_in_user_id());
		json.put("place_id", check.getCheck_in_place_id());
		json.put("date", check.getDate());
		json.put("description", check.getDescription());
		json.put("numberOfComments", check.getNumberOfComments());
		json.put("numberOfLikes", check.getNumberOfLikes());
		json.put("comments", check.getComments());

		return json.toJSONString();

	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/deletecheckin")
	public String DeleteCheckIn(@FormParam("checkinid") int id)
			throws SQLException {
		CheckInControll.DeleteCheckin(id);
		JSONObject json = new JSONObject();
		json.put("operation", "Done");
		return json.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/likeCheckIn")
	@Produces(MediaType.TEXT_PLAIN)
	public String likeCheckIn(@FormParam("user_id") int user_like_id,
			@FormParam("check_in_id") int like_check_in_id) throws SQLException {
		like like1 = new like();
		like1.setLike_check_in_id(like_check_in_id);
		like1.setUser_like_id(user_like_id);
		like1 = likeControll.addlike(like1);

		JSONObject json = new JSONObject();

		json.put("like_id", like1.getLike_id());
		json.put("like_check_in_id", like1.getLike_check_in_id());
		json.put("user_like_id", like1.getUser_like_id());
		if (like1.getLike_id() == 0) {
			return "liked before";
		}

		return json.toJSONString();

	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/deletecomment")
	@Produces(MediaType.TEXT_PLAIN)
	public String Deletecomment(@FormParam("commentid") int commentid)
			throws SQLException {
		CommentControll.DeleteComment(commentid);
		JSONObject json = new JSONObject();
		json.put("operation", "Done");
		return json.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/DeleteLike")
	@Produces(MediaType.TEXT_PLAIN)
	public String DeleteLike(@FormParam("user_id") int user_id,
			@FormParam("check_in_id") int check_in_id) throws SQLException {
		like like1 = likeControll.DeleteLike(user_id, check_in_id);
		System.out.println();
		JSONObject json = new JSONObject();
		if (like1.getLike_id() == -1) {
			json.put("operation", "Failed not liked the Check in before !!");
		} else {
			json.put("operation", "Done");

		}
		return json.toJSONString();
	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/commentCheckIn")
	@Produces(MediaType.TEXT_PLAIN)
	public String commentCheckIn(
			@FormParam("comment_user_id") int comment_user_id,
			@FormParam("comment_check_in_id") int comment_check_in_id,
			@FormParam("comment_description") String comment_description)
			throws SQLException {
		Comment comment = new Comment(comment_check_in_id, comment_user_id,
				comment_description);

		comment = CommentControll.addComment(comment);

		JSONObject json = new JSONObject();

		json.put("comment_id", comment.getComment_id());
		json.put("comment_check_in_id", comment.getComment_check_in_id());
		json.put("comment_user_id", comment.getComment_user_id());
		json.put("comment_date", comment.getComment_date());
		json.put("comment_description", comment.getComment_description());

		return json.toJSONString();

	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/getUserCheckIns")
	@Produces(MediaType.TEXT_PLAIN)
	public String getUserCheckIns(@FormParam("user_id") int user_id)
			throws SQLException {
		ArrayList<CheckInModel> checkIns = new ArrayList<CheckInModel>();
		checkIns = CheckInControll.getUserCheckIns(user_id);
		String result = "";

		for (int i = 0; i < checkIns.size(); i++) {
			CheckInModel check = checkIns.get(i);
			JSONObject json = new JSONObject();

			json.put("check_in_id", check.getCheck_in_id());
			json.put("user_id", check.getCheck_in_user_id());
			json.put("place_id", check.getCheck_in_place_id());
			json.put("date", check.getDate());
			json.put("description", check.getDescription());
			json.put("numberOfComments", check.getNumberOfComments());
			json.put("numberOfLikes", check.getNumberOfLikes());
			json.put("place_name", check.getMyPlace().getName());
			json.put("place_rate", check.getMyPlace().getRate());
			json.put("user_email", check.getCheck_in_user().getEmail());
			json.put("user_name", check.getCheck_in_user().getName());
			result += json.toJSONString() + " ";

		}
		return result;

	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/getCheckInComments")
	@Produces(MediaType.TEXT_PLAIN)
	public static String getCheckInComments(
			@FormParam("check_in_id") int check_in_id) throws SQLException {
		ArrayList<Comment> comments = new ArrayList<Comment>();
		comments = CommentControll.getCheckInComments(check_in_id);
		String result = "";

		for (int i = 0; i < comments.size(); i++) {
			Comment comment = comments.get(i);
			JSONObject json = new JSONObject();
			json.put("Comment_check_in_id", comment.getComment_check_in_id());
			json.put("Comment_date", comment.getComment_date());
			json.put("Comment_description", comment.getComment_description());
			json.put("Comment_id", comment.getComment_id());
			json.put("Comment_user_id", comment.getComment_user_id());
			json.put("Comment_user_email", comment.getComment_user().getEmail());
			json.put("Comment_user_name", comment.getComment_user().getName());

			result += json.toJSONString() + " ";

		}
		return result;

	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/getCheckIn")
	@Produces(MediaType.TEXT_PLAIN)
	public String getCheckIn(@FormParam("check_in_id") int check_in_id)
			throws SQLException {
		CheckInModel check = CheckInControll.getCheckIn(check_in_id);

		JSONObject json = new JSONObject();
		json.put("check_in_id", check.getCheck_in_id());
		json.put("user_id", check.getCheck_in_user_id());
		json.put("place_id", check.getCheck_in_place_id());
		json.put("date", check.getDate());
		json.put("description", check.getDescription());
		json.put("numberOfComments", check.getNumberOfComments());
		json.put("numberOfLikes", check.getNumberOfLikes());
		json.put("place_name", check.getMyPlace().getName());
		json.put("place_rate", check.getMyPlace().getRate());
		json.put("user_email", check.getCheck_in_user().getEmail());
		json.put("user_name", check.getCheck_in_user().getName());

		return json.toJSONString();

	}

	@POST
	@Path("/notificationaction")
	@Produces(MediaType.TEXT_PLAIN)
	public String NotificationAction(@FormParam("check_in_id") int check_in_id)
			throws SQLException {

		// JSONObject json = new JSONObject();
		return getCheckIn(check_in_id) + "\n" + getCheckInComments(check_in_id);

	}

	@SuppressWarnings("unchecked")
	@POST
	@Path("/getMyFollowersCheckIns")
	@Produces(MediaType.TEXT_PLAIN)
	public String getMyFollowersCheckIns(@FormParam("user_id") int user_id)
			throws SQLException {
		ArrayList<CheckInModel> checkIns = new ArrayList<CheckInModel>();
		checkIns = CheckInControll.getMyFollowersCheckIns(user_id);
		String result = "";

		for (int i = 0; i < checkIns.size(); i++) {
			CheckInModel check = checkIns.get(i);
			JSONObject json = new JSONObject();

			json.put("check_in_id", check.getCheck_in_id());
			json.put("user_id", check.getCheck_in_user_id());
			json.put("place_id", check.getCheck_in_place_id());
			json.put("date", check.getDate());
			json.put("description", check.getDescription());
			json.put("numberOfComments", check.getNumberOfComments());
			json.put("numberOfLikes", check.getNumberOfLikes());
			json.put("place_name", check.getMyPlace().getName());
			json.put("place_rate", check.getMyPlace().getRate());
			json.put("user_email", check.getCheck_in_user().getEmail());
			json.put("user_name", check.getCheck_in_user().getName());

			result += json.toJSONString() + " ";

		}
		return result;

	}

	@POST
	@Path("/historylist")
	@Produces(MediaType.TEXT_PLAIN)
	public String GetHistoryList(@FormParam("userid") int userid)
			throws SQLException {
		return HistoryList.DispalyHistory(userid);

	}

	@GET
	@Path("/ila")
	@Produces(MediaType.TEXT_PLAIN)
	public String ila() {
		return "Hello  from ila";
		
	}
}
