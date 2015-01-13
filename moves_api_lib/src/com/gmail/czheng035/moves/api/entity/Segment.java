package com.gmail.czheng035.moves.api.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gmail.czheng035.moves.api.ActivityTypeRepo;
import com.gmail.czheng035.moves.api.ApiManager;

public class Segment {
	private String type = null;	
	private Date startTime = null;
	private Date endTime = null;
	private Place place = null;
	private List<ActivityInstantce> activities = null;
	private Date lastUpdate = null;
	
	public static List<Segment> fromJson(JSONArray json)
			throws JSONException {
		List<Segment> segmentList = new ArrayList<Segment>();

		for (int i = 0; i < json.length(); i++) {
			segmentList.add(fromJson(json.getJSONObject(i)));
		}

		return segmentList;
	}

	public static Segment fromJson(JSONObject json)
			throws JSONException {
		Segment segment = new Segment();
		
		if (json.has("type") && json.get("type") != JSONObject.NULL) 
			segment.type = json.getString("type");

		DateFormat dfISO8601 = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ", Locale.US);
		
		if (json.has("startTime") && json.get("startTime") != JSONObject.NULL) {
			try {
				segment.startTime = dfISO8601.parse(json.getString("startTime"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		if (json.has("endTime") && json.get("endTime") != JSONObject.NULL) {
			try {
				segment.endTime = dfISO8601.parse(json.getString("endTime"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		if (json.has("place") && json.get("place") != JSONObject.NULL)
			segment.place = Place.fromJson(json.getJSONObject("place"));

		if (json.has("activities") && json.get("activities") != JSONObject.NULL)
			segment.activities = ActivityInstantce.fromJson(json.getJSONArray("activities"));
		
		if (json.has("lastUpdate") && json.get("lastUpdate") != JSONObject.NULL) {
			try {
				segment.lastUpdate = dfISO8601.parse(json.getString("lastUpdate"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return segment;
	}

	public String getType() {
		return type;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public Place getPlace() {
		return place;
	}

	public List<ActivityInstantce> getActivities() {
		return activities;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}
}
