[![Build Status](https://travis-ci.com/ravinder5/cookie-header.svg?branch=master)](https://travis-ci.com/github/ravinder5/cookie-header)
# cookie-header
Exposes static methods to create Cookie header with SameSite attribute support

## Background:

##### Why did I create this library?
All major browsers have recently rolled out feature to limit third-party cookie sharing by making use of a new attribute called `SameSite`. A lot applications are effected by this change as browsers are no longer sharing cookies unless they see an attribute `Same-Site` while setting `Set-Cookie` header.

##### Adding an attribute is simple why should I use this?
If you use `javax.servlet.http.Cookie` class to create Cookies, they you probably would know that this class doesnot support `SameSite` property. SO the only work around for applications using this calss is to dump it and create `Set-Cookie` header manually. So this library makes it easy for you to do that.

## Sample usage:
```$xslt
package com.tgt.cookie.test

import com.tgt.egs.auth.cookie.CookieHeader;

public class Test {
    private String getCookieHeader() {
            return CookieHeader.createSetCookieHeader(cookieName, cookieValue,
                    cookieConfig.getDomain(), cookieConfig.getPath(), cookieConfig.getSameSite(), cookieConfig.isSecure(),
                    cookieConfig.isHttpOnly(), expiry);
        }
}
```

## Method signatures available

```$xslt
String createSetCookieHeader(String cookieName, String cookieValue, String domain, String path,
                                               String sameSite, boolean isSecure, boolean isHttpOnly, Integer maxAge)
```

```$xslt
String createSetCookieHeader(Cookie cookie, String sameSite)
```

```$xslt
void createSetCookieHeader(HttpServletResponse httpServletResponse, Cookie cookie, String sameSite)
```

```$xslt
void createSetCookieHeader(HttpServletResponse httpServletResponse, String cookieName,
                                               String cookieValue, String domain, String path, String sameSite,
                                               boolean isSecure, boolean isHttpOnly, Integer maxAge)
```

## Contribution Guidelines
Just create a PR with your suggestions
