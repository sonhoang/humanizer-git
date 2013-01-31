package com.cstsolution.sra;
import java.util.*;


import com.google.gson.*;

public class TasksByRater {
	int total_row;
	int offset;
	String[] rows;
	
	public List data = new ArrayList();
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
		
		//obj.
		return this;
	
	}
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
	
	public class clsRows{
		String id;
		String key;
		String value;
		clsDoc doc;
		public clsRows(){
			
		}
	}
	
	public class clsDoc{
		String _id;
		String _rev;
		String name;
		clsSearch search;
		String project_id;
		String[] raters;
		clsProgress progress;
		public clsDoc(){
			
		}

	}
	
	public class clsSearch{
		String query;
		String engine;
		String[] results;
		public clsSearch(){
			
		}
		
	}
	
	public class clsProgress{
		String value;	
		public clsProgress(){
			
		}

	}
	
}


