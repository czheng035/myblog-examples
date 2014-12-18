package com.gmail.czheng035.moves.api.entity;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DailySummary {
	Date date;
	List<ActivitySummary> activitySummaryList;
	int caloriesIdle;
	Date lastUpdate;

	public static DailySummary fromJson(String json) throws JSONException {
		DailySummary dailySummary = new DailySummary();
		JSONArray array = new JSONArray(json);
		JSONObject objDailySummary = array.getJSONObject(0);

		if (objDailySummary.get("summary") == JSONObject.NULL)
			dailySummary.activitySummaryList = null;
		else {
			JSONArray arrayActivitySummaries = objDailySummary
					.getJSONArray("summary");
			dailySummary.activitySummaryList = ActivitySummary
					.fromJson(arrayActivitySummaries);
		}

		return dailySummary;
	}

	public List<ActivitySummary> getActivitySummaryList() {
		return activitySummaryList;
	}

	public void setActivitySummaryList(List<ActivitySummary> activitySummaryList) {
		this.activitySummaryList = activitySummaryList;
	}
}
