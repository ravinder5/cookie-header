package com.bandi.http.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Exposes method to create a Set-Cookie header with SameSite attributes
 * @author Ravinder
 */
public final class CookieHeader {

	public static final String DOMAIN = "Domain";
	public static final String PATH = "Path";
	public static final String SAME_SITE = "SameSite";
	public static final String SECURE = "Secure";
	public static final String HTTP_ONLY = "HttpOnly";
	public static final String MAX_AGE = "Max-Age";
	public static final String NONE = "none";
	public static final String SET_COOKIE = "Set-Cookie";
	public static final String SEPERATOR = "; ";
	public static final String ASSIGN = "=";

	private CookieHeader() {}

	/**
	 * Creates a string with key value pairs used in a HttpCookie with ; delimiter
	 * 
	 * @param cookieName name of the cookie which can not be null
	 * @param cookieValue value of the cookie which can not be null
	 * @param domain Host to which the cookie will be sent. If omitted, defaults to the host of the current document URL, not including subdomains. Contrary to earlier specifications, leading dots in domain names (.example.com) are ignored. Multiple host/domain values are not allowed, but if a domain is specified, then subdomains are always included.
	 * @param path (optional) A path that must exist in the requested URL, or the browser won't send the Cookie header. The forward slash (/) character is interpreted as a directory separator, and subdirectories will be matched as well: for Path=/docs, /docs, /docs/Web/, and /docs/Web/HTTP will all match. 
     * @param sameSite (optional) Controls whether a cookie is sent with cross-site and same-site requests.
	 * @param isSecure (optional) when true a cookie is sent to the server when a request is made with the https: scheme (except on localhost). 
	 * @param isHttpOnly (optional) Forbids JavaScript from accessing the cookie, for example, through the Document.cookie property. Note that a cookie that has been created with HttpOnly will still be sent with JavaScript-initiated requests.
	 * @param maxAge Number of seconds until the cookie expires. A zero or negative number will expire the cookie immediately. If both Expires and Max-Age are set, Max-Age has precedence.
	 * @return returns a string with key value pairs used in a HttpCookie with ; delimiter
	 */
	public static String createSetCookieHeader(String cookieName, String cookieValue, String domain, String path, SameSite sameSite, boolean isSecure,
			boolean isHttpOnly, Integer maxAge) {
		
		StringBuilder cookieString = new StringBuilder();
		
		if(cookieName==null || cookieName.trim().isEmpty() || cookieValue==null || cookieValue.trim().isEmpty()) {
			throw new IllegalArgumentException("Cookie name or value can not be empty ("+cookieName+"="+cookieValue);
		}
		cookieString.append(cookieName).append(ASSIGN).append(cookieValue).append(SEPERATOR);
		
		if(domain !=null && !domain.trim().isEmpty()) {
			cookieString.append(DOMAIN).append(ASSIGN).append(domain).append(SEPERATOR);
		}
		
		if(path!=null && !path.trim().isEmpty()) {
			cookieString.append(PATH).append(ASSIGN).append(path).append(SEPERATOR);
		}
		
		if(sameSite!=null) {
			cookieString.append(SAME_SITE).append(ASSIGN).append(sameSite.getValue()).append(SEPERATOR);
		}
		
		if (isSecure) {
			cookieString.append(SECURE + SEPERATOR);
		}
		if (isHttpOnly) {
			cookieString.append(HTTP_ONLY + SEPERATOR);
		}
		if (maxAge != null) {
			cookieString.append(MAX_AGE).append(ASSIGN).append(maxAge).append(SEPERATOR);
		}
		return cookieString.toString().trim();
	}

	/**
	 * Creates a string with key value pairs used in a HttpCookie with ; delimiter
	 * 
	 * @param cookie
	 * @param sameSite
	 * @return
	 */
	public static String createSetCookieHeader(Cookie cookie, SameSite sameSite) {
		return createSetCookieHeader(cookie.getName(), cookie.getValue(), cookie.getDomain(), cookie.getPath(), sameSite, cookie.getSecure(),
				cookie.isHttpOnly(), cookie.getMaxAge());
	}

	/**
	 * Creates a Set-Cookie header and adds it to HttpServletResponse
	 * 
	 * @param httpServletResponse
	 * @param cookie
	 * @param sameSite
	 */
	public static void createSetCookieHeader(HttpServletResponse httpServletResponse, Cookie cookie, SameSite sameSite) {
		String cookieHeader = createSetCookieHeader(cookie.getName(), cookie.getValue(), cookie.getDomain(), cookie.getPath(), sameSite,
				cookie.getSecure(), cookie.isHttpOnly(), cookie.getMaxAge());

		httpServletResponse.addHeader(SET_COOKIE, cookieHeader);
	}

	/**
	 * Creates a Set-Cookie header and adds it to HttpServletResponse
	 * 
	 * @param httpServletResponse
	 * @param cookieName
	 * @param cookieValue
	 * @param domain
	 * @param path
	 * @param sameSite
	 * @param isSecure
	 * @param isHttpOnly
	 * @param maxAge
	 */
	public static void createSetCookieHeader(HttpServletResponse httpServletResponse, String cookieName, String cookieValue, String domain,
			String path, SameSite sameSite, boolean isSecure, boolean isHttpOnly, Integer maxAge) {
		String cookieHeader = createSetCookieHeader(cookieName, cookieValue, domain, path, sameSite, isSecure, isHttpOnly, maxAge);
		httpServletResponse.addHeader(SET_COOKIE, cookieHeader);
	}
}
