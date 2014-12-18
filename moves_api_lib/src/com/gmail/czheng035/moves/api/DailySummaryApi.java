package com.gmail.czheng035.moves.api;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGetHC4;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtilsHC4;

import android.net.Uri;

import com.gmail.czheng035.moves.api.entity.DailySummary;

public class DailySummaryApi extends BaseApi {

	private static final String PATH = "/user/summary/daily/";
	
	private String accessCode;
	private CloseableHttpClient httpclient;
	
	public DailySummaryApi(String accessCode, CloseableHttpClient httpclient) {
		this.accessCode = accessCode;
		this.httpclient = httpclient;
	}
	
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
				.appendQueryParameter("access_token", accessCode);
		if (updatedSince != null) {

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
