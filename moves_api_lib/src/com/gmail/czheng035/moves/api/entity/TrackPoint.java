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

public class TrackPoint {
	private Location location = null;
	private Date time = null;

	public static List<TrackPoint> fromJson(JSONArray json)
			throws JSONException {
		List<TrackPoint> trackPointList = new ArrayList<TrackPoint>();

		for (int i = 0; i < json.length(); i++) {
			trackPointList.add(fromJson(json.getJSONObject(i)));
		}

		return trackPointList;
	}

	public static TrackPoint fromJson(JSONObject json) throws JSONException {
		TrackPoint trackPoint = new TrackPoint();

		trackPoint.location = Location.fromJson(json);

		DateFormat dfISO8601 = new SimpleDateFormat("yyyyMMdd'T'HHmmssZ",
				Locale.US);

		if (json.has("time") && json.get("time") != JSONObject.NULL) {
			try {
				trackPoint.time = dfISO8601.parse(json.getString("time"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}

		return trackPoint;
	}

	public Location getLocation() {
		return location;
	}

	public Date getTime() {
		return time;
	}
}
