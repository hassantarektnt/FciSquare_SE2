package com.services;

import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

public class GetPositionTest {

	Services getposition = new Services();

	@SuppressWarnings("unchecked")
	@Test
	public void test1() {

		String js = getposition.Getuserlastposition("2", "hazem");
		JSONObject json = new JSONObject();
		json.put("email", "hazem");
		json.put("name", "Hazem");
		json.put("lat", 30.0131);
		json.put("long", 31.2089);
		Assert.assertEquals(json.toJSONString(), js);
	}

	@Test
	public void test2() {

		String js = getposition.Getuserlastposition("1",
				"samuelsamer@gmail.com");
		String str = "not in followers";
		Assert.assertEquals(str, js);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void getfollowersTest1() {
		String js = getposition.Getfollowers("4");
		JSONObject json = new JSONObject();
		json.put("id", 2);
		json.put("email", "samuelsamer952@gmail.com");
		json.put("name", "Samuel");
		json.put("lat", 30.0131);
		json.put("long", 31.2089);

		Assert.assertEquals(json.toJSONString() + " ", js);
	}

	@Test
	public void getfollowersTest2() {
		String js = getposition.Getfollowers("10");
		String str = "";
		Assert.assertEquals(str, js);
	}
}
