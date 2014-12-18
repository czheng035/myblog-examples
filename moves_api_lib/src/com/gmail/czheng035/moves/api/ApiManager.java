package com.gmail.czheng035.moves.api;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPostHC4;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtilsHC4;
import org.json.JSONObject;

import android.net.Uri;

import com.gmail.czheng035.moves.api.exception.ApiContextNotInitialized;
import com.gmail.czheng035.moves.api.net.HttpResourcesFactory;

public class ApiManager {
	private static String clientId = null;
	private static String clientSecret = null;
	private static String redirectUri = null;
	
	private static String accessToken = null;
	private static String refreshToken = null;
	
	public static boolean initApiContext(String clientId, String clientSecret,
			String redirectUri, String authCode) {
		ApiManager.clientId = clientId;
		ApiManager.clientSecret = clientSecret;
		ApiManager.redirectUri = redirectUri;

		Uri uri = new Uri.Builder().scheme("https")
				.authority("api.moves-app.com").path("/oauth/v1/access_token")
				.appendQueryParameter("grant_type", "authorization_code")
				.appendQueryParameter("code", authCode)
				.appendQueryParameter("client_id", clientId)
				.appendQueryParameter("client_secret", clientSecret)
				.appendQueryParameter("redirect_uri", redirectUri).build();

		CloseableHttpClient httpClient = HttpResourcesFactory.getHttpClient();
		HttpPostHC4 request = new HttpPostHC4(uri.toString());
		CloseableHttpResponse response = null;
		try {
			response = httpClient.execute(request);
			HttpEntity responseEntity = response.getEntity();
			
			JSONObject json = new JSONObject(EntityUtilsHC4.toString(responseEntity));
			ApiManager.accessToken = json.getString("access_token");
			ApiManager.refreshToken = json.getString("refresh_token");
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
	
	public static String getAccessToken() {
		if (accessToken == null) throw new ApiContextNotInitialized();
		else return accessToken;
	}
}
