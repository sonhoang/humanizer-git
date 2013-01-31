package com.cstsolution.sra;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class Rater {
	String _id;
	String _rev;
	String email;
	String password;
	String verified;
	public Rater init(String input){
		try{
			JsonParser parser = new JsonParser();
			JsonObject obj = parser.parse(input).getAsJsonObject();
			JsonArray arr = parser.parse(obj.get("rows").toString()).getAsJsonArray();
			JsonObject obj2 = (JsonObject) arr.get(0);
			_id = obj2.get("id").toString();
			_rev = "";
			email = obj2.get("key").toString();
			password = obj2.get("value").toString();
			
		}catch (Exception ex){
			
		}
		return this;
		
	}
}


