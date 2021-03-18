package com.softlynx.util;

import javax.servlet.http.HttpServletRequest;

public class UtilServlets {

	/**
	 * Returns the name of the host serving the given request. If a
	 * X-Forwarded-Host-Header is present (e.g. running behind proxy server), it
	 * will be returned, otherwise request.getServerName() (with optional port) is
	 * returned.
	 */
	public static String getHost(HttpServletRequest request) {
		if (request.getHeader("X-Forwarded-Host") != null) {
			return request.getHeader("X-Forwarded-Host");
		} else {
			return request.getServerName() + (request.getServerPort() != 80 ? ":" + request.getServerPort() : "");
		}
	}

	/**
	 * Returns the plain name (without port) of the host serving the given request.
	 * If a X-Forwarded-Host-Header is present (e.g. running behind proxy server),
	 * it will be returned, otherwise request.getServerName() is returned.
	 */
	public static String getServerName(HttpServletRequest request) {
		if (request.getHeader("X-Forwarded-Host") != null) {
			return request.getHeader("X-Forwarded-Host");
		} else {
			return request.getServerName();
		}
	}

	/**
	 * Returns the url of the host serving the given request.
	 * schema://authority[:port]
	 */
	public static String getServerURL(HttpServletRequest request) {
		return (request.isSecure() ? "https://" : "http://") + getHost(request);
	}

	/**
	 * Returns the server url with context path for the given request.
	 */
	public static String getContextURL(HttpServletRequest request) {
		return (request.isSecure() ? "https://" : "http://") + getHost(request) + request.getContextPath();
	}

	/**
	 * Returns the server url with context path for the given request.
	 */
	public static String getHostIP(HttpServletRequest request) {
		return request.getLocalAddr();
	}
}
