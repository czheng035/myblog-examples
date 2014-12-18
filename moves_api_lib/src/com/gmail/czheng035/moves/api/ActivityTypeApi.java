package com.gmail.czheng035.moves.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGetHC4;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtilsHC4;
import org.json.JSONArray;

import android.net.Uri;

import com.gmail.czheng035.moves.api.entity.ActivityType;
import com.gmail.czheng035.moves.api.net.HttpResourcesFactory;


public class ActivityTypeApi extends BaseApi {

	private static final String PATH = "/activities";
	private static Map<String, ActivityType> activityTypeMap = null;	
	
	synchronized public static Map<String, ActivityType> getActivityTypeMap() {
		if (activityTypeMap != null) return activityTypeMap;
		else {
			Uri.Builder uriBuilder = new Uri.Builder().scheme(SCHEME)
					.authority(AUTHORITY).path(BASE_PATH + PATH)
					.appendQueryParameter("access_token", ApiManager.getAccessToken());
			
			CloseableHttpClient httpClient = HttpResourcesFactory.getHttpClient();
			HttpGetHC4 request = new HttpGetHC4(uriBuilder.build().toString());
			CloseableHttpResponse response = null;

			try {
				response = httpClient.execute(request);
				HttpEntity responseEntity = response.getEntity();
				String result = EntityUtilsHC4.toString(responseEntity);
				
				JSONArray activityTypes = new JSONArray(result);
				activityTypeMap = new HashMap<String, ActivityType>();
				for (int i = 0; i < activityTypes.length(); i++) {
					ActivityType activityType =  ActivityType.fromJson(activityTypes.getJSONObject(i));
					activityTypeMap.put(activityType.getActivity(), activityType);
				}
				return activityTypeMap;
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
}
