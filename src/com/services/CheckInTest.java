package com.services;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import Comment.Comment;

public class CheckInTest {
	Services checkin = new Services();
	//zawad el ids 
	public static int getlastCheckinId() {
		return 25;
	}

	public static int getlastlikeid() {
		return 17;
	}

	public static int getlastCommentid() {
		return 20;
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testMakeCheckin() throws SQLException {
		String js;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		js = checkin.makeCheckIn(1, 1, "nice");
		JSONObject json = new JSONObject();
		ArrayList<Comment> comments = new ArrayList<Comment>();
		json.put("check_in_id", getlastCheckinId());
		json.put("user_id", 1);
		json.put("place_id", 1);
		json.put("date", dateFormat.format(date));
		json.put("description", "nice");
		json.put("numberOfComments", 0);
		json.put("numberOfLikes", 0);
		json.put("comments", comments);

		Assert.assertEquals(json.toJSONString(), js);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteCheckin() throws SQLException {
		String js;
		js = checkin.DeleteCheckIn(getlastCheckinId()-1);
		JSONObject json = new JSONObject();
		json.put("operation", "Done");
		Assert.assertEquals(json.toJSONString(), js);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testLikeCheckin2() throws SQLException {
		String js;
		js = checkin.likeCheckIn(3, 1);

		JSONObject json = new JSONObject();

		json.put("like_id", getlastlikeid());
		json.put("like_check_in_id", 1);
		json.put("user_like_id", 3);

		Assert.assertEquals(json.toJSONString(), js);
	}

	@Test
	public void testLikeCheckin() throws SQLException {
		String js;
		js = checkin.likeCheckIn(1, 1);
		String json = "liked before";
		Assert.assertEquals(json, js);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testCommentCheckin() throws SQLException {

		String js;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();

		js = checkin.commentCheckIn(1, 1, "nice");
		JSONObject json = new JSONObject();

		json.put("comment_id", getlastCommentid());
		json.put("comment_check_in_id", 1);
		json.put("comment_user_id", 1);
		json.put("comment_date", dateFormat.format(date));
		json.put("comment_description", "nice");

		Assert.assertEquals(json.toJSONString(), js);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteCommentCheckin() throws SQLException {

		String js;
		js = checkin.Deletecomment(getlastCommentid()-1);
		JSONObject json = new JSONObject();

		json.put("operation", "Done");

		Assert.assertEquals(json.toJSONString(), js);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteLikeCheckin1() throws SQLException {

		String js;
		js = checkin.DeleteLike(3, 1);
		JSONObject json = new JSONObject();

		json.put("operation", "Done");

		Assert.assertEquals(json.toJSONString(), js);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testDeleteLikeCheckin2() throws SQLException {

		String js;
		js = checkin.DeleteLike(2, 2);
		JSONObject json = new JSONObject();

		json.put("operation", "Failed not liked the Check in before !!");

		Assert.assertEquals(json.toJSONString(), js);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Test
	public void testgetUserCheckIns() throws SQLException {
		String js, res = "";
		js = checkin.getUserCheckIns(3);
		JSONObject json = new JSONObject();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date("2016/05/17 01:01:44");
		dateFormat.format(date);
		json.put("check_in_id", 9);
		json.put("user_id", 3);
		json.put("place_id", 1);
		json.put("date", dateFormat.format(date));
		json.put("description", "nice");
		json.put("numberOfComments", 0);
		json.put("numberOfLikes", 0);
		json.put("place_name", "pyramids");
		json.put("place_rate", 0.0);
		json.put("user_email", "hazem");
		json.put("user_name", "Hazem");
		res = json.toJSONString() + " ";
		json = new JSONObject();
		date = new Date("2016/05/17 01:18:57");
		json.put("check_in_id", 15);
		json.put("user_id", 3);
		json.put("place_id", 1);
		json.put("date", dateFormat.format(date));
		json.put("description", "good");
		json.put("numberOfComments", 0);
		json.put("numberOfLikes", 0);
		json.put("place_name", "pyramids");
		json.put("place_rate", 0.0);
		json.put("user_email", "hazem");
		json.put("user_name", "Hazem");
		res += json.toJSONString() + " ";
		Assert.assertEquals(res, js);
	}

	@Test
	public void testgetUserCheckIns2() throws SQLException {
		String js, res = "";
		js = checkin.getUserCheckIns(4);

		Assert.assertEquals(res, js);
	}

	@SuppressWarnings("static-access")
	@Test
	public void testgetCheckInComments() throws SQLException {
		String js, res = "";
		js = checkin.getCheckInComments(5);

		Assert.assertEquals(res, js);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Test
	public void testgetCheckIn() throws SQLException {
		String js;
		js = checkin.getCheckIn(15);
		JSONObject json = new JSONObject();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date("2016/05/17 01:18:57");

		json.put("check_in_id", 15);
		json.put("user_id", 3);
		json.put("place_id", 1);
		json.put("date", dateFormat.format(date));
		json.put("description", "good");
		json.put("numberOfComments", 0);
		json.put("numberOfLikes", 0);
		json.put("place_name", "pyramids");
		json.put("place_rate", 0.0);
		json.put("user_email", "hazem");
		json.put("user_name", "Hazem");

		Assert.assertEquals(json.toJSONString(), js);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Test
	public void testgetMyFollowersCheckIns() throws SQLException {
		String js, res = "";
		js = checkin.getMyFollowersCheckIns(2);
		JSONObject json = new JSONObject();
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date("2016/05/17 01:01:44");
		dateFormat.format(date);
		json.put("check_in_id", 9);
		json.put("user_id", 3);
		json.put("place_id", 1);
		json.put("date", dateFormat.format(date));
		json.put("description", "nice");
		json.put("numberOfComments", 0);
		json.put("numberOfLikes", 0);
		json.put("place_name", "pyramids");
		json.put("place_rate", 0.0);
		json.put("user_email", "hazem");
		json.put("user_name", "Hazem");
		res = json.toJSONString() + " ";
		json = new JSONObject();
		date = new Date("2016/05/17 01:18:57");
		json.put("check_in_id", 15);
		json.put("user_id", 3);
		json.put("place_id", 1);
		json.put("date", dateFormat.format(date));
		json.put("description", "good");
		json.put("numberOfComments", 0);
		json.put("numberOfLikes", 0);
		json.put("place_name", "pyramids");
		json.put("place_rate", 0.0);
		json.put("user_email", "hazem");
		json.put("user_name", "Hazem");
		res += json.toJSONString() + " ";

		Assert.assertEquals(res, js);
	}

}
