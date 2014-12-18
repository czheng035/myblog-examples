package com.gmail.czheng035.moves.api.entity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityType {
	private String activity;
	private String group;
	private boolean isGeo;
	private boolean isPlace;
	private String color;
	private List<String> unitList;

	public static ActivityType fromJson(JSONObject json) throws JSONException {
		ActivityType activityType = new ActivityType();
		activityType.activity = json.getString("activity");
		String unitsStr = json.getString("units");
		if (unitsStr.contains(",")) {
			String[] unitsArray = unitsStr.split(",");
			activityType.unitList = new ArrayList<String>();
			for (int i = 0; i < unitsArray.length; i++)
				activityType.unitList.add(unitsArray[i]);
		}
		return activityType;
	}

	public String getActivity() {
		return activity;
	}

	public String getGroup() {
		return group;
	}

	public boolean isGeo() {
		return isGeo;
	}

	public boolean isPlace() {
		return isPlace;
	}

	public String getColor() {
		return color;
	}

	public List<String> getUnitList() {
		return unitList;
	}
}
