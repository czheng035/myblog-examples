package com.gmail.czheng035.moves.api.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.gmail.czheng035.moves.api.ActivityTypeApi;

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

	private static ActivitySummary fromJson4Android(JSONObject json)
			throws JSONException {
		ActivitySummary activitySummary = new ActivitySummary();
		activitySummary.activity = json.getString("activity");
		activitySummary.group = json.getString("group");
		activitySummary.duration = json.getInt("duration");

		ActivityType activityType = ActivityTypeApi.getActivityTypeMap().get(
				activitySummary.getActivity());
		activitySummary.unitValueMap = new HashMap<String, Integer>();
		for (String unit : activityType.getUnitList()) {
			if (!unit.equals("calories"))
				activitySummary.unitValueMap.put(unit, json.getInt(unit));
		}

		return activitySummary;
	}

	private static ActivitySummary fromJson(JSONObject json)
			throws JSONException {
		ActivitySummary activitySummary = new ActivitySummary();
		activitySummary.activity = json.getString("activity");
		activitySummary.group = json.getString("group");
		activitySummary.duration = json.getInt("duration");

		ActivityType activityType = ActivityTypeApi.getActivityTypeMap().get(
				activitySummary.getActivity());
		activitySummary.unitValueMap = new HashMap<String, Integer>();
		for (String unit : activityType.getUnitList()) {
			activitySummary.unitValueMap.put(unit, json.getInt(unit));
		}

		return activitySummary;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public Map<String, Integer> getUnitValueMap() {
		return unitValueMap;
	}

	public void setUnitValueMap(Map<String, Integer> unitValueMap) {
		this.unitValueMap = unitValueMap;
	}
}
