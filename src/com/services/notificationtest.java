package com.services;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class notificationtest {
	Services notification = new Services();

	@SuppressWarnings("unchecked")
	@Test
	public void testaddnotification() throws SQLException {

		String js = notification.AddNotification(1, 1, 2, "sherif", 1, 1);
		JSONObject json = new JSONObject();
		json.put("operation", "Done");

		Assert.assertEquals(json.toJSONString(), js);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testgetnotification() throws SQLException {

		String js = notification.GetNotification(2);
		String total = new String("");

		JSONObject json = new JSONObject();
		json.put("id", 8);
		json.put("description", "sherif Likes your check in ");
		json.put("checkinid", 2);
		total += " " + json.toJSONString();
		Assert.assertEquals(total, js);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	@Test
	public void notificationaction() throws SQLException {

		String js = notification.NotificationAction(4);
		JSONObject json = new JSONObject();

		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date("2016/04/26 20:35:31");

		json.put("check_in_id", 4);
		json.put("user_id", 1);
		json.put("place_id", 1);
		json.put("date", dateFormat.format(date));
		json.put("description", "very good");
		json.put("numberOfComments", 0);
		json.put("numberOfLikes", 0);
		json.put("place_name", "pyramids");
		json.put("place_rate", 0.0);
		json.put("user_email", "mhmdsamir@gmail.com");
		json.put("user_name", "mohamed");
		String result = " ";
		Assert.assertEquals(json.toJSONString() + result, js);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void testdeletenotification() throws SQLException {

		String js = notification.DeleteNotification(4);
		JSONObject json = new JSONObject();
		json.put("operation", "Done");

		Assert.assertEquals(json.toJSONString(), js);
	}

}
