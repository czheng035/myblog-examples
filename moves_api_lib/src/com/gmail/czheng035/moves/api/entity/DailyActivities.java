package com.gmail.czheng035.moves.api.entity;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DailyActivities {
	private Date date;
	private List<ActivitySummary> activitySummaryList;
	private List<Segment> segmentList;
	private int caloriesIdle;
	private Date lastUpdate;
	
	public static DailyActivities fromJson(JSONObject json) throws JSONException {
		DailyActivities dailyActivities = new DailyActivities();
		
		DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.US);		
		DateFormat dfISO8601 = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ", Locale.US);
		
		if (json.has("date") && json.get("date") != JSONObject.NULL) {
			try {
				dailyActivities.date = df.parse(json.getString("date"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else dailyActivities.date = null;

		if (json.has("summary") && json.get("summary") != JSONObject.NULL) {
			JSONArray arrayActivitySummaries = json
					.getJSONArray("summary");
			dailyActivities.activitySummaryList = ActivitySummary
					.fromJson(arrayActivitySummaries);
		} else dailyActivities.activitySummaryList = null;
		
		if (json.has("segments") && json.get("segments") != JSONObject.NULL) {
			JSONArray arrayActivitySegments = json
					.getJSONArray("segments");
			dailyActivities.segmentList = Segment.fromJson(arrayActivitySegments);
		} else dailyActivities.segmentList = null;

		if (json.has("caloriesIdle") && json.get("caloriesIdle") != JSONObject.NULL) {
			dailyActivities.caloriesIdle = json.getInt("caloriesIdle");
		} else dailyActivities.caloriesIdle = 0;
		
		if (json.has("lastUpdate") && json.get("lastUpdate") != JSONObject.NULL) {
			try {
				dailyActivities.lastUpdate = dfISO8601.parse(json.getString("lastUpdate"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else dailyActivities.lastUpdate = null;

		return dailyActivities;
	}
	
	public List<ActivityInstantce> getAllActivityInstances() {		
		if (segmentList == null) return null;
		else {
			List<ActivityInstantce> allActivityInstantceList = new ArrayList<ActivityInstantce>();
			for (Segment segment : segmentList) {
				allActivityInstantceList.addAll(segment.getActivities());
			}
			return allActivityInstantceList;
		}
	}
	
	public Date getDate() {
		return date;
	}
	public List<ActivitySummary> getActivitySummaryList() {
		return activitySummaryList;
	}
	public List<Segment> getSegmentList() {
		return segmentList;
	}
	public int getCaloriesIdle() {
		return caloriesIdle;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
}
