package com.services;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

//  FollowUser 
public class FollowUserTest {

	Services FollowUser = new Services();

	@SuppressWarnings("unchecked")
	@Test
	public void test1() {

		String js = FollowUser.FollowUser("1", "samuelsamer952@gmail.com");
		JSONObject json = new JSONObject();
		json.put("operation", "Done");
		Assert.assertEquals(json.toJSONString(), js);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void test2() {

		String js = FollowUser.FollowUser("1", "samuelsamer@gmail.com");
		JSONObject json = new JSONObject();
		json.put("operation", "Faild");
		Assert.assertEquals(json.toJSONString(), js);
	}

}
