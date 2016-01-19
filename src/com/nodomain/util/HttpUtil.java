package com.nodomain.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

public class HttpUtil {
	public static HttpURLConnection initHttpSession(String url, CookieUtility cookies, String proxy) throws IOException {
		HttpURLConnection http = null;

		if (proxy != null) {
			if (proxy.split(":").length == 2) {
				String ip = proxy.split(":")[0];
				int port;
				try {
					port = Integer.parseInt(proxy.split(":")[1]);
				} catch (NumberFormatException e) {
					port = -1;
				}

				if (port > 10 && ip.contains(".") && ip.length() >= 7) {
					Proxy oProxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(ip, port));
					http = (HttpURLConnection) (new URL(url)).openConnection(oProxy);
				}
			} else if (proxy.split(":").length > 2) {
				// http://stackoverflow.com/a/1433296
				System.out
						.println("Proxy authentication is still not supported. See code commentary for dev reference.");
			}
		}

		if (http == null) {
			http = (HttpURLConnection) (new URL(url)).openConnection();
		}
		http.setRequestProperty("user-agent", Constants.USER_AGENT);
		http.setRequestProperty("Accept", "*/*");
		http.setReadTimeout(Constants.MAX_HTTP_READ_TIMEOUT);
		http.setConnectTimeout(Constants.MAX_HTTP_CONNECT_TIMEOUT);

		if (cookies != null) {
			cookies.saveCookies(http);
		}

		return http;
	}

	public static HttpURLConnection initHttpSession(String url, CookieUtility cookies) throws IOException {
		return initHttpSession(url, cookies, null);
	}

	public static HttpURLConnection initHttpSession(String url) throws IOException {
		return initHttpSession(url, null, null);
	}

	public static String downloadPage(final InputStream is) throws IOException {
		final StringBuilder sb = new StringBuilder();
		byte[] buffer = new byte[Constants.BUFFER_SIZE];

		int size;
		while ((size = is.read(buffer)) != -1)
			sb.append(new String(buffer, 0, size));

		return sb.toString();
	}
}
