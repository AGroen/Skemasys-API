package com.nodomain.util;

/**
 * Cookies aren't used, but the class needs to be here to reuse code in HttpUtil
 * 
 * @author Anders Gr�n
 *
 */
public class CookieUtility {
	private CookieUtility() {}
	
	public void saveCookies(java.net.HttpURLConnection http) {throw new RuntimeException();}
}