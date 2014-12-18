package com.gmail.czheng035.moves.api;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGetHC4;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtilsHC4;

import android.net.Uri;

import com.gmail.czheng035.moves.api.entity.DailySummary;
import com.gmail.czheng035.moves.api.net.HttpResourcesFactory;

public class DailySummaryApi extends BaseApi {

	private static final String PATH = "/user/summary/daily/";
	
	/**
	 * 
	 * @param date
	 *            mandatory
	 * @param updatedSince
	 *            optional
	 * @param timeZone
	 *            optional
	 * @return
	 */
	public DailySummary singleDay(Date date, Date updatedSince, String timeZone) {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		Uri.Builder uriBuilder = new Uri.Builder().scheme(SCHEME)
				.authority(AUTHORITY).path(BASE_PATH + PATH + df.format(date))
				.appendQueryParameter("access_token", ApiManager.getAccessToken());
		if (updatedSince != null) {

		}
		if (timeZone != null) {
			uriBuilder = uriBuilder.appendQueryParameter("timeZone", timeZone);
		}

		CloseableHttpClient httpClient = HttpResourcesFactory.getHttpClient();
		HttpGetHC4 request = new HttpGetHC4(uriBuilder.build().toString());
		CloseableHttpResponse response = null;

		try {
			response = httpClient.execute(request);
			HttpEntity responseEntity = response.getEntity();
			String result = EntityUtilsHC4.toString(responseEntity);
			return DailySummary.fromJson(result);
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
