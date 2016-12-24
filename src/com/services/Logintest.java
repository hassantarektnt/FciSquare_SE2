package com.services;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import com.models.UserModel;

public class Logintest {
   UserModel obj  = new UserModel ();
   Services login = new Services ();

   @SuppressWarnings("unchecked")
@Test
   public void testlogin1()
   {   
    String js = login.login("mhmdsamir@gmail.com", "123");
    JSONObject json = new JSONObject() ;
    json.put("id",1);
	json.put("name","mohamed");
	json.put("email","mhmdsamir@gmail.com");
	json.put("pass","123");
	json.put("lat", 30.0310036);
	json.put("long",31.2127736);
    Assert.assertEquals( json.toJSONString() , js);
   }
   
   @SuppressWarnings("unchecked")
   @Test
   public void testlogin2() {   
	    String js = login.login("mhmdsamir@gmail.com", "12");
	    JSONObject json = new JSONObject() ;
	    json.put("operation","faild");
	    Assert.assertEquals( json.toJSONString() , js);
	   }

   @SuppressWarnings("unchecked")
   @Test
   public void testlogin3() {   
	    String js = login.login("mhmdsamir@gmail", "123");
	    JSONObject json = new JSONObject() ;
	    json.put("operation","faild");
	    Assert.assertEquals( json.toJSONString() , js);
	   }
      
   @SuppressWarnings("unchecked")
   @Test
   public void testlogin4() {   
	    String js = login.login("mhmdsamir@gmail", "12");
	    JSONObject json = new JSONObject() ;
	    json.put("operation","faild");
	    Assert.assertEquals( json.toJSONString() , js);
	   }

   @Test
   public void TestLoginUserModel1 () {
	   obj =  UserModel.login("mhmdsamir@gmail.com", "123") ;
	  // UserModel obj2 = new UserModel () ;
	  /* obj2.setEmail("mhmdsamir@gmail.com");
	   obj2.setId(1);
	   obj2.setName("mohamed");
	   obj2.setLat(30.0310036);
	   obj2.setLon(31.2127736);
	   obj2.setPass("123");*/
	   boolean check = false ;
	   if ( obj.getEmail().equals("mhmdsamir@gmail.com") && obj.getId()== 1 && 
			   obj.getLat() == 30.0310036 && obj.getLon() == 31.2127736 && obj.getName().equals("mohamed") && obj.getPass().equals("123")  ) 
		   check = true ;
	   Assert.assertEquals( check , true );
   }

   @Test
   public void TestLoginUserModel2 () {
	   obj =  UserModel.login("mhmdsamir@gmail.", "123") ;
	   UserModel obj2 = null ;
	
	   Assert.assertEquals( obj , obj2 );
   }

   @Test
   public void TestLoginUserModel3 () {
	   obj =  UserModel.login("mhmdsamir@gmail.com", "12") ;
	   UserModel obj2 = null ;
	
	   Assert.assertEquals( obj , obj2 );
   }

   @Test
   public void TestLoginUserModel4 () {
	   obj =  UserModel.login("mhmdsamir@gmail.", "12") ;
	   UserModel obj2 = null ;
	
	   Assert.assertEquals( obj , obj2 );
   }



}