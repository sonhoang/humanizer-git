/**
 * 
 */
package org.humanizer.rating.objects;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author sonhv
 *
 * Contains object definition for Tasks loading Rater
 */
public class TasksByRater {
	int total_row;
	int offset;
	String[] rows;
	
	List data = new ArrayList();
	/**
	 * @author sonhv
	 *
	 * Init Taks for Rater from json string
	 */	
	public TasksByRater init(String input, String check_keyword){
		data.clear();
		Gson gson = new Gson();
		//gson.fromJson(json, typeOfT)
		JsonParser parser = new JsonParser();
		JsonObject obj = parser.parse(input).getAsJsonObject();
		JsonArray arr = parser.parse(obj.get("rows").toString()).getAsJsonArray();
		for (int i = 0; i < arr.size(); i ++){
			ArrayList elem = new ArrayList();
			JsonObject obj2 = (JsonObject) arr.get(i);
			String task = obj2.get("id").getAsString();
			//elem.add(keyword);
			JsonObject doc = parser.parse(obj2.get("doc").toString()).getAsJsonObject();
			String keyword = doc.get("name").toString();
			if (!check_keyword.equals(keyword)){
				continue;
			}			
			//elem.add(keyword);
			System.out.println(keyword);
			JsonObject search = parser.parse(doc.get("search").toString()).getAsJsonObject();
			JsonArray results = parser.parse(search.get("results").toString()).getAsJsonArray();
			for (int j = 0; j < results.size(); j ++){
				String url = results.get(j).toString();
				elem.add(url);
				//System.out.println(url);
			}
			data = elem;
			//data.add(elem);
		}
		
		return this;
	
	}
	
	/**
	 * @author sonhv
	 *
	 * Init Taks for Rater from json string
	 */	
	public TasksByRater init(String input){
		data.clear();
		Gson gson = new Gson();
		//gson.fromJson(json, typeOfT)
		JsonParser parser = new JsonParser();
		JsonObject obj = parser.parse(input).getAsJsonObject();
		JsonArray arr = parser.parse(obj.get("rows").toString()).getAsJsonArray();
		for (int i = 0; i < arr.size(); i ++){
			ArrayList elem = new ArrayList();
			JsonObject obj2 = (JsonObject) arr.get(i);
			String task = obj2.get("id").getAsString();
			elem.add(task);
			JsonObject doc = parser.parse(obj2.get("doc").toString()).getAsJsonObject();			
			String keyword = doc.get("name").toString();
			elem.add(keyword);
			System.out.println(keyword);
			JsonObject search = parser.parse(doc.get("search").toString()).getAsJsonObject();
			JsonArray results = parser.parse(search.get("results").toString()).getAsJsonArray();
			for (int j = 0; j < results.size(); j ++){
				String url = results.get(j).toString();
				elem.add(url);
				//System.out.println(url);
			}
			data.add(elem);
		}
		
		//obj.
		return this;
	}
	
	public List getData(){
		return data;
	}
	
	
}


