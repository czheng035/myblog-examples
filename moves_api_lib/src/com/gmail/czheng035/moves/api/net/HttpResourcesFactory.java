package com.gmail.czheng035.moves.api.net;

import org.apache.http.client.CookieStore;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class HttpResourcesFactory {
	
	private HttpResourcesFactory() {}

	private static class LazyHolder {
		private static final CloseableHttpClient HTTP_CLIENT = HttpClients
				.createDefault();
		private static final CookieStore COOKIE_STORE = new BasicCookieStore();
	}

	public static CloseableHttpClient getHttpClient() {
		return LazyHolder.HTTP_CLIENT;
	}

	public static CookieStore getCookieStore() {
		return LazyHolder.COOKIE_STORE;
	}
}
