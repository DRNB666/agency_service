package com.leepsmart.code.common.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class CookieUtil {

    public static void addCookie(String path, HttpServletResponse response, String cookieName, String value, int maxAge) {
        Cookie cookies = new Cookie(cookieName, value);
        cookies.setHttpOnly(true);
        cookies.setMaxAge(maxAge);
        cookies.setPath(path);
        response.addCookie(cookies);
    }

    public static String getCookie(HttpServletRequest request, String cookieName) {
        String cookieValue = "";
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    cookieValue = cookie.getValue();
                }
            }
        }
        return cookieValue;
    }

    public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        cookie.setPath(request.getContextPath());
        response.addCookie(cookie);
    }

    public static void removeAllCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(0);
            cookie.setValue(null);
            cookie.setPath(request.getContextPath());
            response.addCookie(cookie);
        }
    }
}
