package com.gmail.czheng035.moves.api.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DailySummary extends BaseEntity {
	
	private Date date;
	private List<ActivitySummary> activitySummaryList;
	private int caloriesIdle;
	private Date lastUpdate;
	
	public static DailySummary fromJson(JSONObject json) throws JSONException {
		DailySummary dailySummary = new DailySummary();
		
		DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.US);
		DateFormat dfISO8601 = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ", Locale.US);
		
		if (json.has("date") && json.get("date") != JSONObject.NULL) {
			try {
				dailySummary.date = df.parse(json.getString("date"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else dailySummary.date = null;
		
		if (json.has("summary") && json.get("summary") != JSONObject.NULL) {
			JSONArray arrayActivitySummaries = json
					.getJSONArray("summary");
			dailySummary.activitySummaryList = ActivitySummary
					.fromJson(arrayActivitySummaries);
		} else dailySummary.activitySummaryList = null;
			
		if (json.has("caloriesIdle") && json.get("caloriesIdle") != JSONObject.NULL) {
			dailySummary.caloriesIdle = json.getInt("caloriesIdle");
		} else dailySummary.caloriesIdle = 0;
		
		if (json.has("lastUpdate") && json.get("lastUpdate") != JSONObject.NULL) {
			try {
				dailySummary.lastUpdate = dfISO8601.parse(json.getString("lastUpdate"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else dailySummary.lastUpdate = null;
		
		if (json.has("error") && json.get("error") != JSONObject.NULL) {
			dailySummary.error = json.getString("error");
		} else dailySummary.error = null;

		return dailySummary;
	}

	public List<ActivitySummary> getActivitySummaryList() {
		return activitySummaryList;
	}
	
	public Date getDate() {
		return date;
	}

	public int getCaloriesIdle() {
		return caloriesIdle;
	}

	public Date getLastUpdate() {
		return lastUpdate;
	}
}
