package com.gmail.czheng035.moves.api.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gmail.czheng035.moves.api.ActivityTypeRepo;
import com.gmail.czheng035.moves.api.ApiManager;

public class ActivitySummary {
	private String activity;
	private String group;
	private int duration;
	private Map<String, Integer> unitValueMap;

	public static List<ActivitySummary> fromJson(JSONArray json)
			throws JSONException {
		List<ActivitySummary> activitySummaryList = new ArrayList<ActivitySummary>();

		for (int i = 0; i < json.length(); i++) {
			activitySummaryList.add(fromJson(json.getJSONObject(i)));
		}

		return activitySummaryList;
	}

	public static ActivitySummary fromJson(JSONObject json)
			throws JSONException {
		ActivitySummary activitySummary = new ActivitySummary();
		
		if (json.has("activity") && json.get("activity") != JSONObject.NULL) 
			activitySummary.activity = json.getString("activity");
		else activitySummary.activity = null;
		
		if (json.has("group") && json.get("group") != JSONObject.NULL) 
			activitySummary.group = json.getString("group");
		else activitySummary.group = null;
		
		if (json.has("duration") && json.get("duration") != JSONObject.NULL) 
			activitySummary.duration = json.getInt("duration");
		else activitySummary.duration = 0;
		
		ApiManager am = ApiManager.getInstance();
		ActivityTypeRepo activityTypeRepo = am.getActivityTypeRepo();
		Map<String, ActivityType> activityTypeMap = activityTypeRepo.getActivityTypeMap();

		ActivityType activityType = activityTypeMap.get(activitySummary.getActivity());
		activitySummary.unitValueMap = new HashMap<String, Integer>();
		for (String unit : activityType.getUnitList()) {
			if (json.has(unit) && json.get(unit) != JSONObject.NULL) 
				activitySummary.unitValueMap.put(unit, json.getInt(unit));
			else activitySummary.unitValueMap.put(unit, 0);
		}

		return activitySummary;
	}

	public String getActivity() {
		return activity;
	}

	public String getGroup() {
		return group;
	}

	public int getDuration() {
		return duration;
	}

	public Map<String, Integer> getUnitValueMap() {
		return unitValueMap;
	}
}
