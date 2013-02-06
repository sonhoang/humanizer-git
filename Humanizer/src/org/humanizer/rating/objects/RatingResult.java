/**
 * 
 */
package org.humanizer.rating.objects;

/**
 * @author sonhv
 *
 */
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

/**
 * @author sonhv
 *
 * Rating result object
 */
public class RatingResult {
	public String task_id;
	public String url;
	public String rater;
	public String relevance;
	public String note;
	public String query;
	
	
	public boolean init(String input, String query_check, String task_check, String url_check, String rater_check){
		boolean ret = false;
		try{
			Gson gson = new Gson();
			//gson.fromJson(json, typeOfT)
			JsonParser parser = new JsonParser();
			JsonObject obj = parser.parse(input).getAsJsonObject();
			JsonArray arr = parser.parse(obj.get("rows").toString()).getAsJsonArray();
			boolean bIsFound = false;
			for (int i = 0; i < arr.size(); i ++){
				ArrayList elem = new ArrayList();
				JsonObject obj2 = (JsonObject) arr.get(i);
				//String task = obj2.get("id").getAsString();
				//elem.add(keyword);
				JsonObject doc = parser.parse(obj2.get("doc").toString()).getAsJsonObject();
				String url = doc.get("url").getAsString();
				String rater = doc.get("rater").getAsString();
				String task = doc.get("task_id").getAsString();
				String query = doc.get("query").getAsString();
				String relevance = doc.get("relevance").getAsString();
				String note = doc.get("note").getAsString();
				if ((url.equals(url_check)) && (rater.equals(rater_check)) && 
						(task.equals(task_check)) && (query.equals(query_check))){
					bIsFound = true;
					//set data
					this.note = note;
					this.relevance = relevance;
					return true;
				}
				//data.add(elem);
			}
			
			//ret = true;
			return ret;
		}catch (Exception ex){
			
		}
		return ret;
		
	}
}

