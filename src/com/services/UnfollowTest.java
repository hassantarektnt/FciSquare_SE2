package com.services;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

//  UnfollowUser -  update position
public class UnfollowTest {

	Services UnfollowUser = new Services();

	@SuppressWarnings("unchecked")
	@Test
	public void test1() {

		String js = UnfollowUser.UnFollowUser("1", "samuelsamer952@gmail.com");
		JSONObject json = new JSONObject();
		json.put("operation", "Done");
		Assert.assertEquals(json.toJSONString(), js);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test2() {

		String js = UnfollowUser.FollowUser("1", "samuelsamer@gmail.com");
		JSONObject json = new JSONObject();
		json.put("operation", "Faild");
		Assert.assertEquals(json.toJSONString(), js);
	}

	// ////////////////////////////////////////////////////////////
	// update position

	@SuppressWarnings("unchecked")
	@Test
	public void updatePositionTest() {

		String js = UnfollowUser.updatePosition("1", "23.5", "12.35");
		JSONObject json = new JSONObject();
		json.put("status", 1);
		Assert.assertEquals(json.toJSONString(), js);
	}

}
