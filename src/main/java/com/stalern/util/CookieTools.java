package com.stalern.util;

import javax.servlet.http.Cookie;

/**
 * @author stalern
 * @date 2019/11/05~19:54
 */
public class CookieTools {
    public static String getCookieHeaderName(Cookie cookie) {
        return cookie.getName();
    }

    public static String getCookieHeaderValue(Cookie cookie) {
        return cookie.getValue();
    }
}
