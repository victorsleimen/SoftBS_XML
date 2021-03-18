package com.softlynx.util;

import java.security.MessageDigest;

public class UtilMd5Encode {

	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	public static String byteArrayToHexString(byte[] b) {

		StringBuffer resultSb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			resultSb.append(byteToHexString(b[i]));
		}
		return resultSb.toString();
	}

	private static String byteToHexString(byte b) {

		int n = b;
		int d1 = 0;
		int d2 = 0;
		if (n < 0) {
			n = 256 + n;
			d1 = n / 16;
			d2 = n % 16;
		}
		return hexDigits[d1] + hexDigits[d2];
	}

	public static String MD5Encode(String origin) {

		String resultString = null;
		try {
			resultString = new String(origin);
			MessageDigest md = MessageDigest.getInstance("MD5");
			resultString = byteArrayToHexString(md.digest(resultString.getBytes()));
		} catch (Exception e) {

		}
		return resultString;
	}
}
