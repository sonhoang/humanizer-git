/**
 * 
 */
package org.humanizer.rating.objects;

import org.humanizer.rating.objects.Rater;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author sonhv
 *   
 * Contains object definition for Rater
 */
public class Rater {
	String _id;
	String _rev;
	String email;
	String password;
	String verified;
	
	/**
	 * @author sonhv
	 *
	 * Init an Rater object from json string
	 */	
	public Rater init(String input){
		try{
			JsonParser parser = new JsonParser();
			JsonObject obj = parser.parse(input).getAsJsonObject();
			JsonArray arr = parser.parse(obj.get("rows").toString()).getAsJsonArray();
			JsonObject obj2 = (JsonObject) arr.get(0);
			_id = obj2.get("id").getAsString();
			_rev = "";
			email = obj2.get("key").getAsString();
			password = obj2.get("value").getAsString();
			
		}catch (Exception ex){
			ex.printStackTrace();
		}
		return this;
		
	}
	
	public String getEmail(){
		return email;
	}
	
	public String getPassword(){
		return password;
	}
	
}

