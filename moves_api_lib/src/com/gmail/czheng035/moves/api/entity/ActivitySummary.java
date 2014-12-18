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
		List<ActivitySummary> activitySummariesList = new ArrayList<ActivitySummary>();

		for (int i = 0; i < json.length(); i++) {
			activitySummariesList.add(fromJson4Android(json.getJSONObject(i)));
		}

		return activitySummariesList;
	}

	public static ActivitySummary fromJson4Android(JSONObject json)
			throws JSONException {
		ActivitySummary activitySummary = new ActivitySummary();
		activitySummary.activity = json.getString("activity");
		activitySummary.group = json.getString("group");
		activitySummary.duration = json.getInt("duration");

		ApiManager am = ApiManager.getInstance();
		ActivityTypeRepo activityTypeRepo = am.getActivityTypeRepo();
		Map<String, ActivityType> activityTypeMap = activityTypeRepo.getActivityTypeMap();

		ActivityType activityType = activityTypeMap.get(activitySummary.getActivity());
		activitySummary.unitValueMap = new HashMap<String, Integer>();
		for (String unit : activityType.getUnitList()) {
			if (!unit.equals("calories"))
				activitySummary.unitValueMap.put(unit, json.getInt(unit));
		}

		return activitySummary;
	}

	public static ActivitySummary fromJson(JSONObject json)
			throws JSONException {
		ActivitySummary activitySummary = new ActivitySummary();
		activitySummary.activity = json.getString("activity");
		activitySummary.group = json.getString("group");
		activitySummary.duration = json.getInt("duration");
		
		ApiManager am = ApiManager.getInstance();
		ActivityTypeRepo activityTypeRepo = am.getActivityTypeRepo();
		Map<String, ActivityType> activityTypeMap = activityTypeRepo.getActivityTypeMap();

		ActivityType activityType = activityTypeMap.get(activitySummary.getActivity());
		activitySummary.unitValueMap = new HashMap<String, Integer>();
		for (String unit : activityType.getUnitList()) {
			activitySummary.unitValueMap.put(unit, json.getInt(unit));
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
