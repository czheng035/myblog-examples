package com.gmail.czheng035.moves.api;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGetHC4;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtilsHC4;
import org.json.JSONArray;
import org.json.JSONObject;

import android.net.Uri;

import com.gmail.czheng035.moves.api.entity.DailyActivities;

public class DailyActivityApi extends BaseApi {
	
	private static final String PATH = "/user/activities/daily/";
	
	private String accessCode;
	private CloseableHttpClient httpclient;
	
	public DailyActivityApi(String accessCode, CloseableHttpClient httpclient) {
		this.accessCode = accessCode;
		this.httpclient = httpclient;
	}
	
	public DailyActivities singleDay(Date date, Date updatedSince, String timeZone) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.US);
		Uri.Builder uriBuilder = new Uri.Builder().scheme(SCHEME)
				.authority(AUTHORITY).path(BASE_PATH + PATH + df.format(date))
				.appendQueryParameter("access_token", accessCode);
		
		if (updatedSince != null) {
			DateFormat dfISO8601 = new SimpleDateFormat("yyyyMMdd’T’HHmmssZ", Locale.US);
			uriBuilder = uriBuilder.appendQueryParameter("updatedSince", dfISO8601.format(updatedSince));
		}
		
		if (timeZone != null) {
			uriBuilder = uriBuilder.appendQueryParameter("timeZone", timeZone);
		}

		HttpGetHC4 request = new HttpGetHC4(uriBuilder.build().toString());
		CloseableHttpResponse response = null;

		try {
			response = httpclient.execute(request);
			HttpEntity responseEntity = response.getEntity();
			String result = EntityUtilsHC4.toString(responseEntity);
			
			if (result.charAt(0) == '[') {
				JSONArray dailyActivitiesJArray = new JSONArray(result);
				return DailyActivities.fromJson(dailyActivitiesJArray.getJSONObject(0));
			} else {	// Error message is a JSON object
				JSONObject error = new JSONObject(result);
				return DailyActivities.fromJson(error);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (response != null) {
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
