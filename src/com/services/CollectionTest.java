package com.services;

import java.sql.SQLException;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;


// save list - get place - save place - add new place -delete saved place
public class CollectionTest {
	Services service = new Services();

	@SuppressWarnings("unchecked")
	@Test
	public void saveListTist() throws SQLException {
		String js = service.SaveList(4);
		JSONObject json = new JSONObject();
		json.put("operation", "faild");
		Assert.assertEquals(json.toJSONString(), js);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void saveListTest() {
		String js = service.SaveList(1);
		JSONObject json = new JSONObject();
		json.put("numOfCheckIns", 2);
		json.put("id", 1);
		json.put("rate", 0.0);
		json.put("description", "historical place");
		json.put("name", "pyramids");
		json.put("numOfReq", 5);
		json.put("longitude", 3.2);
		json.put("latitude", 2.2);
		Assert.assertEquals(json.toJSONString(), js);
	}

	// /////////////////////////////////////////////////////////
	@SuppressWarnings("unchecked")
	@Test
	public void getPlaceTist() throws SQLException {
		String js = service.GetPlace(1);
		JSONObject json = new JSONObject();
		json.put("numOfCheckIns", 4);
		json.put("id", 1);
		json.put("rate", 0.0);
		json.put("description", "historical place");
		json.put("name", "pyramids");
		json.put("numOfReq", 5);
		json.put("longitude", 3.2);
		json.put("latitude", 2.2);
		Assert.assertEquals(json.toJSONString(), js);
	}

	// /////////////////////////////////////////////////////////////////
	@SuppressWarnings("unchecked")
	@Test
	public void savePlaceTest1() {
		String js = service.SavePlace(4, "pyramids");
		JSONObject json = new JSONObject();
		json.put("operation", "Done");
		Assert.assertEquals(json.toJSONString(), js);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void savePlaceTest2() {
		String js = service.SavePlace(4, "Alex");
		JSONObject json = new JSONObject();
		json.put("operation", "faild");
		Assert.assertEquals(json.toJSONString(), js);
	}

	// /////////////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	@Test
	public void addNewPlaceTest1() {
		String js = service.AddPlace("Opra", "musicplace", 2.2, 2.3);
		JSONObject json = new JSONObject();
		json.put("id", 11);
		json.put("desc", "musicplace");
		json.put("name", "Opra");
		json.put("lat", 2.3);
		json.put("long", 2.2);
		Assert.assertEquals(json.toJSONString(), js);
	}

	@SuppressWarnings("unchecked")
	@Test
	public void addNewPlaceTest2() {
		String js = service.AddPlace("Opra", "musicplace", 2.2, 2.3);
		JSONObject json = new JSONObject();
		json.put("operation", "faild");
		Assert.assertEquals(json.toJSONString(), js);
	}

	// ////////////////////////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	@Test
	public void deletSavedPlace() throws SQLException {
		String js = service.DeleteSavedPlace(7, 4);
		JSONObject json = new JSONObject();
		json.put("operation", "Done");
		Assert.assertEquals(json.toJSONString(), js);
	}
	// /////////////////////////////////////////////////////////////////////////

}
