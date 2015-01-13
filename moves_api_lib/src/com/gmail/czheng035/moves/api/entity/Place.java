package com.gmail.czheng035.moves.api.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Place {
	private long id = 0;
	private String name = null;
	private String type = null;
	private String foursquareId = null;
	private List<String> foursquareCategoryIds = null;
	private Location location = null;
	
	public static Place fromJson(JSONObject json)
			throws JSONException {
		Place place = new Place();
		
		if (json.has("id") && json.get("id") != JSONObject.NULL) 
			place.id = json.getLong("id");
		
		if (json.has("name") && json.get("name") != JSONObject.NULL) 
			place.name = json.getString("name");
		
		if (json.has("type") && json.get("type") != JSONObject.NULL) 
			place.type = json.getString("type");
		
		if (json.has("foursquareId") && json.get("foursquareId") != JSONObject.NULL) 
			place.foursquareId = json.getString("foursquareId");
		
		if (json.has("foursquareCategoryIds") && json.get("foursquareCategoryIds") != JSONObject.NULL) {
			place.foursquareCategoryIds = new ArrayList<String>();
			JSONArray jsonArray = json.getJSONArray("foursquareCategoryIds");
			for (int i = 0; i < jsonArray.length(); i++)
				place.foursquareCategoryIds.add(jsonArray.getString(i));
		}
		
		if (json.has("location") && json.get("location") != JSONObject.NULL) 
			place.location = Location.fromJson(json.getJSONObject("locatioin"));
		
		return place;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getFoursquareId() {
		return foursquareId;
	}

	public List<String> getFoursquareCategoryIds() {
		return foursquareCategoryIds;
	}

	public Location getLocation() {
		return location;
	}
}
