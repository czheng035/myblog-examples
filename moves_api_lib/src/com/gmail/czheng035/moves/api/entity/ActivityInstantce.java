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

public class ActivityInstantce {
	private String activity = null;
	private String group = null;
	private boolean manual = false;
	private Date startTime = null;
	private Date endTime = null;
	private int duration = 0;
	private Map<String, Integer> unitValueMap = null;
	private List<TrackPoint> trackPoints = null;
	
	public static List<ActivityInstantce> fromJson(JSONArray json)
			throws JSONException {
		List<ActivityInstantce> activityInstantceList = new ArrayList<ActivityInstantce>();

		for (int i = 0; i < json.length(); i++) {
			activityInstantceList.add(fromJson(json.getJSONObject(i)));
		}

		return activityInstantceList;
	}

	public static ActivityInstantce fromJson(JSONObject json)
			throws JSONException {
		ActivityInstantce activityInstantce = new ActivityInstantce();
		
		if (json.has("activity") && json.get("activity") != JSONObject.NULL) 
			activityInstantce.activity = json.getString("activity");
		
		if (json.has("group") && json.get("group") != JSONObject.NULL) 
			activityInstantce.group = json.getString("group");
		
		if (json.has("manual") && json.get("manual") != JSONObject.NULL) 
			activityInstantce.manual = json.getBoolean("manual");

		DateFormat dfISO8601 = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ", Locale.US);
		
		if (json.has("startTime") && json.get("startTime") != JSONObject.NULL) {
			try {
				activityInstantce.startTime = dfISO8601.parse(json.getString("startTime"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		if (json.has("endTime") && json.get("endTime") != JSONObject.NULL) {
			try {
				activityInstantce.endTime = dfISO8601.parse(json.getString("endTime"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		if (json.has("duration") && json.get("duration") != JSONObject.NULL) 
			activityInstantce.duration = json.getInt("duration");
		
		
		ApiManager am = ApiManager.getInstance();
		ActivityTypeRepo activityTypeRepo = am.getActivityTypeRepo();
		Map<String, ActivityType> activityTypeMap = activityTypeRepo.getActivityTypeMap();

		ActivityType activityType = activityTypeMap.get(activityInstantce.getActivity());
		activityInstantce.unitValueMap = new HashMap<String, Integer>();
		for (String unit : activityType.getUnitList()) {
			if (json.has(unit) && json.get(unit) != JSONObject.NULL) 
				activityInstantce.unitValueMap.put(unit, json.getInt(unit));
			else activityInstantce.unitValueMap.put(unit, 0);
		}
		
		if (json.has("trackPoints") && json.get("trackPoints") != JSONObject.NULL) 
			activityInstantce.trackPoints = TrackPoint.fromJson(json.getJSONArray("trackPoints"));

		return activityInstantce;
	}

	public String getActivity() {
		return activity;
	}

	public String getGroup() {
		return group;
	}

	public boolean isManual() {
		return manual;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public int getDuration() {
		return duration;
	}

	public Map<String, Integer> getUnitValueMap() {
		return unitValueMap;
	}

	public List<TrackPoint> getTrackPoints() {
		return trackPoints;
	}
}
