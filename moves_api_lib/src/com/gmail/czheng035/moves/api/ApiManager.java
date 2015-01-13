package com.gmail.czheng035.moves.api;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPostHC4;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtilsHC4;
import org.json.JSONObject;

import android.net.Uri;

import com.gmail.czheng035.moves.api.exception.ApiManagerNotInitialized;

/**
 * ApiManager is the only singleton factory which holds single instances used
 * for the whole library. The ApiManager singleton must be initialized by call
 * init() before the first use.
 * 
 * @author Cheng Zheng
 * 
 */
public class ApiManager {


	private String clientId = null;
	private String clientSecret = null;
	private String redirectUri = null;

	private String refreshToken = null;
	private String accessToken = null;
	private Date expire = null;
	
	private Map<Integer, Class<?>> androidActivityMap = null;

	private ActivityTypeRepo activityTypeRepo;

	private CloseableHttpClient httpClient;

	private ApiManager() {
		httpClient = HttpClients.createDefault();
	}

	private static class LazyHolder {
		private static final ApiManager INSTANCE = new ApiManager();
	}

	public static ApiManager getInstance() {
		return LazyHolder.INSTANCE;
	}

	public boolean init(String clientId, String clientSecret,
			String redirectUri, String authCode, Map<Integer, Class<?>> androidActivityMap) {
		this.clientId = clientId;
		this.clientSecret = clientSecret;
		this.redirectUri = redirectUri;
		this.refreshToken = authCode;
		
		this.androidActivityMap = androidActivityMap;

		Uri uri = new Uri.Builder().scheme("https")
				.authority("api.moves-app.com").path("/oauth/v1/access_token")
				.appendQueryParameter("grant_type", "authorization_code")
				.appendQueryParameter("code", authCode)
				.appendQueryParameter("client_id", clientId)
				.appendQueryParameter("client_secret", clientSecret)
				.appendQueryParameter("redirect_uri", redirectUri).build();

		HttpPostHC4 request = new HttpPostHC4(uri.toString());
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(request);
			HttpEntity responseEntity = response.getEntity();

			JSONObject json = new JSONObject(
					EntityUtilsHC4.toString(responseEntity));
			accessToken = json.getString("access_token");
			refreshToken = json.getString("refresh_token");

			activityTypeRepo = new ActivityTypeRepo(accessToken, httpClient);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
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

	public String getAccessToken() {
		if (accessToken == null)
			throw new ApiManagerNotInitialized();
		else
			return accessToken;
	}

	public ActivityTypeRepo getActivityTypeRepo() {
		if (activityTypeRepo == null)
			throw new ApiManagerNotInitialized();
		else
			return activityTypeRepo;
	}
	
	public Map<Integer, Class<?>> getAndroidActivityMap() {
		if (androidActivityMap == null)
			throw new ApiManagerNotInitialized();
		else
			return androidActivityMap;
	}

	public CloseableHttpClient getCloseableHttpClient() {
		return httpClient;
	}
	
	public boolean hasValidAccessToken() {
		if (accessToken != null) return true;
		else return false;
	}
}
