package com.softlynx.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zul.theme.Themes;

public class UtilCookies {

	/**
	 * Sets a cookie with given name and value in given response.
	 */
	public static void set(HttpServletResponse response, String name, String value, int expiry) {
		Cookie cookie = new Cookie(name, value);
		cookie.setPath("/");
		cookie.setMaxAge(expiry);
		response.addCookie(cookie);
	}

	/**
	 * Sets a cookie with given name and value in given response. Given expiry sets
	 * the maximum age for a cookie in seconds.
	 */
	public static void set(HttpServletResponse response, String name, String value) {
		set(response, name, value, -1);
	}

	/**
	 * Reads a cookie with given name from given request. Returns null if cookie
	 * with given name doesn't exist.
	 */
	public static String get(HttpServletRequest request, String name) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(name)) {
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	/**
	 * Reads a cookie with given name from given request. Returns given defaultValue
	 * if cookie with given name doesn't exist.
	 */
	public static String get(HttpServletRequest request, String name, String defaultValue) {
		String value = UtilCookies.get(request, name);
		if (value != null) {
			return value;
		} else {
			return defaultValue;
		}
	}

	/**
	 * If a request parameter with given name is set, transfer it to cookie.
	 * Otherwise return cookie with given name, use given defaultValue if cookie
	 * doesn't exist. Given expiry sets the maximum age for a cookie in seconds.
	 */
	public static String pick(HttpServletRequest request, HttpServletResponse response, String name,
			String defaultValue, int expiry) {
		String value = request.getParameter(name);
		if (value != null) {
			UtilCookies.set(response, name, value, expiry);
		} else {
			value = UtilCookies.get(request, name, defaultValue);
		}
		return value;
	}

	/**
	 * If a request parameter with given name is set, transfer it to cookie.
	 * Otherwise return cookie with given name, use given defaultValue if cookie
	 * doesn't exist.
	 */
	public static String pick(HttpServletRequest request, HttpServletResponse response, String name,
			String defaultValue) {
		return pick(request, response, name, defaultValue, -1);
	}

	/**
	 * Dynamically switching themes using Cookies
	 * 
	 * @param themName
	 */
	public static void switchTheme(String themName) {
		Themes.setTheme(Executions.getCurrent(), themName);
		Executions.sendRedirect("");
	}
}
