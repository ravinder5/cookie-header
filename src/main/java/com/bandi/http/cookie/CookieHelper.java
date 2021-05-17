package com.bandi.http.cookie;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Exposes method to create a Set-Cookie header including the SameSite attribute.
 * 
 * The SameSite is defined in an RFC: https://tools.ietf.org/html/draft-west-first-party-cookies-07
 * 
 * The main cookie functionality is defined in RFC6265 (https://tools.ietf.org/html/rfc6265#section-5.2) but also provides some backwards compatibility 
 * to RFC2109 (https://tools.ietf.org/html/rfc2109) e.g. for comments.
 * 
 * @author Ravinder
 * @author Lonzak
 */
public final class CookieHelper {

	public static final String DOMAIN = "Domain";
	public static final String PATH = "Path";
	public static final String SAME_SITE = "SameSite";
	public static final String SECURE = "Secure";
	public static final String HTTP_ONLY = "HttpOnly";
	public static final String MAX_AGE = "Max-Age";
	public static final String COMMENT = "Comment";
	public static final String SET_COOKIE = "Set-Cookie";
	public static final String SEPERATOR = "; ";
	public static final String ASSIGN = "=";

	//hide default constructor
	private CookieHelper() {}

	/**
	 * Creates a string with key value pairs used in a HttpCookie with ; delimiter
	 * 
	 * @param cookieName	 name of the cookie which can not be null
	 * @param cookieValue    value of the cookie which can not be null
	 * @param domain
	 *                        Host to which the cookie will be sent. If omitted, defaults to the host of the current document URL, not including
	 *                        subdomains. Contrary to earlier specifications, leading dots in domain names (.example.com) are ignored. Multiple
	 *                        host/domain values are not allowed, but if a domain is specified, then subdomains are always included.
	 * @param path
	 *                        (optional) A path that must exist in the requested URL, or the browser won't send the Cookie header. The forward slash
	 *                        (/) character is interpreted as a directory separator, and subdirectories will be matched as well: for Path=/docs,
	 *                        /docs, /docs/Web/, and /docs/Web/HTTP will all match.
	 * @param sameSite
	 *                        (optional) Controls whether a cookie is sent with cross-site and same-site requests.
	 * @param isSecure
	 *                        (optional) when true a cookie is sent to the server when a request is made with the https: scheme (except on localhost).
	 * @param isHttpOnly
	 *                        (optional) Forbids JavaScript from accessing the cookie, for example, through the Document.cookie property. Note that a
	 *                        cookie that has been created with HttpOnly will still be sent with JavaScript-initiated requests.
	 * @param maxAge
	 *                        Number of seconds until the cookie expires. A zero or negative number will expire the cookie immediately. If both
	 *                        Expires and Max-Age are set, Max-Age has precedence.
	 * @param comment
	 *                        (Optional, backwards compatibility) Because cookies can contain private information about a user, the Cookie attribute
	 *                        allows an origin server to document its intended use of a cookie. The user can inspect the information to decide whether
	 *                        to initiate or continue a session with this cookie.
	 * 
	 * @return returns a string with key value pairs used in a HttpCookie with ; delimiter
	 */
	public static String createSetCookieHeader(String cookieName, String cookieValue, String domain, String path, SameSite sameSite, boolean isSecure,
			boolean isHttpOnly, Integer maxAge, String comment) {

		StringBuilder cookieString = new StringBuilder();

		if (cookieName == null || cookieName.trim().isEmpty() || cookieValue == null || cookieValue.trim().isEmpty()) {
			throw new IllegalArgumentException("Cookie name or value can not be empty (" + cookieName + "=" + cookieValue);
		}
		cookieString.append(cookieName).append(ASSIGN).append(cookieValue).append(SEPERATOR);

		if (domain != null && !domain.trim().isEmpty()) {
			cookieString.append(DOMAIN).append(ASSIGN).append(domain).append(SEPERATOR);
		}

		if (path != null && !path.trim().isEmpty()) {
			cookieString.append(PATH).append(ASSIGN).append(path).append(SEPERATOR);
		}

		if (sameSite != null) {
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
		if (comment != null) {
			cookieString.append(COMMENT).append(ASSIGN).append(comment).append(SEPERATOR);
		}
		return cookieString.toString().trim();
	}

	/**
	 * Creates a string with key value pairs used in a HttpCookie with ; delimiter
	 * 
	 * @param cookie       to use. Must not be null.
	 * @param sameSite     attribute of the cookie. Can be null. In this case browsers treat it as Lax.
	 * @return a string with key value pairs used in a HttpCookie with ; delimiter
	 */
	public static String createSetCookieHeader(Cookie cookie, SameSite sameSite) {

		if (cookie == null) {
			throw new IllegalArgumentException("The cookie parameter must not be null!");
		}

		return createSetCookieHeader(cookie.getName(), cookie.getValue(), cookie.getDomain(), cookie.getPath(), sameSite, cookie.getSecure(),
				cookie.isHttpOnly(), cookie.getMaxAge(), cookie.getComment());
	}

	/**
	 * Creates a Set-Cookie header and adds it to HttpServletResponse
	 * 
	 * @param httpServletResponse
	 * @param cookie
	 * @param sameSite
	 */
	public static void createSetCookieHeader(HttpServletResponse httpServletResponse, Cookie cookie, SameSite sameSite) {

		if (cookie == null) {
			throw new IllegalArgumentException("The cookie parameter must not be null!");
		}

		if (httpServletResponse == null || httpServletResponse.isCommitted()) {
			throw new IllegalArgumentException("The response is null or already commited, so no cookie can be set!");
		}

		String cookieHeader = createSetCookieHeader(cookie.getName(), cookie.getValue(), cookie.getDomain(), cookie.getPath(), sameSite,
				cookie.getSecure(), cookie.isHttpOnly(), cookie.getMaxAge(), cookie.getComment());

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
			String path, SameSite sameSite, boolean isSecure, boolean isHttpOnly, Integer maxAge, String comment) {
		String cookieHeader = createSetCookieHeader(cookieName, cookieValue, domain, path, sameSite, isSecure, isHttpOnly, maxAge, comment);
		httpServletResponse.addHeader(SET_COOKIE, cookieHeader);
	}
}
