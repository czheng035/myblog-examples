package com.gmail.czheng035.moves.api.entity;

import org.json.JSONException;
import org.json.JSONObject;

public class Location {
	private double latitude = 0;
	private double longitude = 0;
	
	public static Location fromJson(JSONObject json)
			throws JSONException {
		Location location = new Location();
		
		if (json.has("lat") && json.get("lat") != JSONObject.NULL) 
			location.latitude = json.getDouble("lat");
		
		if (json.has("lon") && json.get("lon") != JSONObject.NULL) 
			location.longitude = json.getDouble("lon");
		
		return location;
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}
}
