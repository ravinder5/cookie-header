package com.bandi.http.cookie;

import javax.servlet.http.Cookie;

import org.junit.Assert;
import org.junit.Test;

public class CookieHeaderTests {

	@Test
	public void testCookieGeneration() {
		Cookie jsession = new Cookie("COOKIE", "testvalue");
		jsession.setDomain("foo.foo.org");
		jsession.setPath("/foo/config");
		jsession.setSecure(true);
		jsession.setMaxAge(5000);
		
		String generatedCookie = CookieHeader.createSetCookieHeader(jsession, SameSite.NONE);
		Assert.assertEquals("COOKIE=testvalue; Domain=foo.foo.org; Path=/foo/config; SameSite=None; Secure; Max-Age=5000; ",generatedCookie);
	}
	
	@Test
	public void testRoutingCookie() {
		Cookie route = new Cookie("ROUTEID",".serverid");
		route.setPath("/");
		
		String generatedCookie = CookieHeader.createSetCookieHeader(route, null);
		Assert.assertEquals("ROUTEID=.serverid; Path=/; Max-Age=-1;", generatedCookie);
	}
	
	@Test
	public void testJSessionCookie() {
		
		Cookie route = new Cookie("JSESSIONID","someID.2345SASDFASF");
		route.setPath("/fooserver/home");
		
		String generatedCookie = CookieHeader.createSetCookieHeader(route, null);
		Assert.assertEquals("JSESSIONID=someID.2345SASDFASF; Path=/fooserver/home; Max-Age=-1;", generatedCookie);
	}
	
	//TODO the other methods should also be tested...

}