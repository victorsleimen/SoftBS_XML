package com.softlynx.util;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * General output formatting functions - mainly for helping in JSPs
 */
public final class UtilFormatOut {

	final static Logger logger = LoggerFactory.getLogger(UtilFormatOut.class);
	public static final String module = UtilFormatOut.class.getName();

	// ------------------- quantity format handlers -------------------
	private static final DecimalFormat quantityDecimalFormat = new DecimalFormat("#,##0.###");

	// ------------------- percentage format handlers -------------------
	private static final DecimalFormat percentageDecimalFormat = new DecimalFormat("##0.##%");

	private UtilFormatOut() {
	}

	public static String safeToString(Object obj) {
		if (obj != null) {
			return obj.toString();
		} else {
			return "";
		}
	}

	/**
	 * Formats a Double representing a price into a string
	 * 
	 * @param price
	 *            The price Double to be formatted
	 * @return A String with the formatted price
	 */
	public static String formatPrice(Double price) {
		if (price == null)
			return "";
		return formatPrice(price.doubleValue());
	}

	/**
	 * Formats a Double into a properly spelled out number string based on Locale
	 * 
	 * @param amount
	 *            The amount Double to be formatted
	 * @param locale
	 *            The Locale used to format the number
	 * @return A String with the formatted number
	 */
	public static String formatSpelledOutAmount(Double amount, Locale locale) {
		return formatSpelledOutAmount(amount.doubleValue(), locale);
	}

	/**
	 * Formats a Double representing a percentage into a string
	 * 
	 * @param percentage
	 *            The percentage Double to be formatted
	 * @return A String with the formatted percentage
	 */
	public static String formatPercentage(Double percentage) {
		if (percentage == null)
			return "";
		return formatPercentage(percentage.doubleValue());
	}

	/**
	 * Formats a BigDecimal representing a percentage into a string
	 * 
	 * @param percentage
	 *            The percentage Decimal to be formatted
	 * @return A String with the formatted percentage
	 */
	public static String formatPercentage(BigDecimal percentage) {
		if (percentage == null)
			return "";
		return percentageDecimalFormat.format(percentage);
	}

	/**
	 * Formats a double representing a percentage into a string
	 * 
	 * @param percentage
	 *            The percentage double to be formatted
	 * @return A String with the formatted percentage
	 */
	public static String formatPercentage(double percentage) {
		return percentageDecimalFormat.format(percentage);
	}

	/**
	 * Formats an Long representing a quantity into a string
	 * 
	 * @param quantity
	 *            The quantity Long to be formatted
	 * @return A String with the formatted quantity
	 */
	public static String formatQuantity(Long quantity) {
		if (quantity == null)
			return "";
		else
			return formatQuantity(quantity.doubleValue());
	}

	/**
	 * Formats an int representing a quantity into a string
	 * 
	 * @param quantity
	 *            The quantity long to be formatted
	 * @return A String with the formatted quantity
	 */
	public static String formatQuantity(long quantity) {
		return formatQuantity((double) quantity);
	}

	/**
	 * Formats an Integer representing a quantity into a string
	 * 
	 * @param quantity
	 *            The quantity Integer to be formatted
	 * @return A String with the formatted quantity
	 */
	public static String formatQuantity(Integer quantity) {
		if (quantity == null)
			return "";
		else
			return formatQuantity(quantity.doubleValue());
	}

	/**
	 * Formats an int representing a quantity into a string
	 * 
	 * @param quantity
	 *            The quantity int to be formatted
	 * @return A String with the formatted quantity
	 */
	public static String formatQuantity(int quantity) {
		return formatQuantity((double) quantity);
	}

	/**
	 * Formats a Float representing a quantity into a string
	 * 
	 * @param quantity
	 *            The quantity Float to be formatted
	 * @return A String with the formatted quantity
	 */
	public static String formatQuantity(Float quantity) {
		if (quantity == null)
			return "";
		else
			return formatQuantity(quantity.doubleValue());
	}

	/**
	 * Formats a float representing a quantity into a string
	 * 
	 * @param quantity
	 *            The quantity float to be formatted
	 * @return A String with the formatted quantity
	 */
	public static String formatQuantity(float quantity) {
		return formatQuantity((double) quantity);
	}

	/**
	 * Formats an Double representing a quantity into a string
	 * 
	 * @param quantity
	 *            The quantity Double to be formatted
	 * @return A String with the formatted quantity
	 */
	public static String formatQuantity(Double quantity) {
		if (quantity == null)
			return "";
		else
			return formatQuantity(quantity.doubleValue());
	}

	/**
	 * Formats an BigDecimal representing a quantity into a string
	 * 
	 * @param quantity
	 *            The quantity BigDecimal to be formatted
	 * @return A String with the formatted quantity
	 */
	public static String formatQuantity(BigDecimal quantity) {
		if (quantity == null)
			return "";
		else
			return quantityDecimalFormat.format(quantity);
	}

	/**
	 * Formats an double representing a quantity into a string
	 * 
	 * @param quantity
	 *            The quantity double to be formatted
	 * @return A String with the formatted quantity
	 */
	public static String formatQuantity(double quantity) {
		return quantityDecimalFormat.format(quantity);
	}

	public static String formatPaddedNumber(long number, int numericPadding) {
		StringBuilder outStrBfr = new StringBuilder(Long.toString(number));
		while (numericPadding > outStrBfr.length()) {
			outStrBfr.insert(0, '0');
		}
		return outStrBfr.toString();
	}

	public static String formatPaddingRemove(String original) {
		if (original == null)
			return null;
		StringBuilder orgBuf = new StringBuilder(original);
		while (orgBuf.length() > 0 && orgBuf.charAt(0) == '0') {
			orgBuf.deleteCharAt(0);
		}
		return orgBuf.toString();
	}

	// ------------------- date handlers -------------------

	/**
	 * Formats a <code>Timestamp</code> into a date-time <code>String</code> using
	 * the default locale and time zone. Returns an empty <code>String</code> if
	 * <code>timestamp</code> is <code>null</code>.
	 * 
	 * @param timestamp
	 *            The <code>Timestamp</code> to format
	 * @return A <code>String</code> with the formatted date/time, or an empty
	 *         <code>String</code> if <code>timestamp</code> is <code>null</code>
	 */
	public static String formatDate(java.sql.Timestamp timestamp) {
		if (timestamp == null)
			return "";
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.FULL);
		java.util.Date date = timestamp;
		return df.format(date);
	}

	/**
	 * Formats a <code>Date</code> into a date-only <code>String</code> using the
	 * specified locale and time zone, or using the specified format.
	 *
	 * @param date
	 *            The date to format
	 * @param dateTimeFormat
	 *            Optional format string
	 * @param locale
	 *            The format locale - can be <code>null</code> if
	 *            <code>dateFormat</code> is not <code>null</code>
	 * @param timeZone
	 *            The format time zone
	 * @return <code>date</code> formatted as a date-only <code>String</code>
	 * @throws NullPointerException
	 *             if any required parameter is <code>null</code>
	 */
	public static String formatDate(Date date, String dateTimeFormat, Locale locale, TimeZone timeZone) {
		return UtilDateTime.toDateFormat(dateTimeFormat, timeZone, locale).format(date);
	}

	/**
	 * Formats a <code>Date</code> into a date-time <code>String</code> using the
	 * specified locale and time zone, or using the specified format.
	 *
	 * @param date
	 *            The date to format
	 * @param dateTimeFormat
	 *            Optional format string
	 * @param locale
	 *            The format locale - can be <code>null</code> if
	 *            <code>dateFormat</code> is not <code>null</code>
	 * @param timeZone
	 *            The format time zone
	 * @return <code>date</code> formatted as a date-time <code>String</code>
	 * @throws NullPointerException
	 *             if any required parameter is <code>null</code>
	 */
	public static String formatDateTime(Date date, String dateTimeFormat, Locale locale, TimeZone timeZone) {
		return UtilDateTime.toDateTimeFormat(dateTimeFormat, timeZone, locale).format(date);
	}

	// ------------------- null string handlers -------------------
	/**
	 * Checks to see if the passed Object is null, if it is returns an empty but
	 * non-null string, otherwise calls toString() on the object
	 * 
	 * @param obj1
	 *            The passed Object
	 * @return The toString() of the passed Object if not null, otherwise an empty
	 *         non-null String
	 */
	public static String makeString(Object obj1) {
		if (obj1 != null) {
			if (obj1 instanceof byte[]) {
				byte[] data = (byte[]) obj1;
				if (data.length > 5120) {
					return "[...binary data]";
				}
				return new String(UtilBase64.base64Encode(data));
			}
			return obj1.toString();
		}
		return "";
	}

	/**
	 * Checks to see if the passed string is null, if it is returns an empty but
	 * non-null string.
	 * 
	 * @param string1
	 *            The passed String
	 * @return The passed String if not null, otherwise an empty non-null String
	 */
	public static String checkNull(String string1) {
		if (string1 != null)
			return string1;
		else
			return "";
	}

	/**
	 * Returns the first passed String if not null, otherwise the second if not
	 * null, otherwise an empty but non-null String.
	 * 
	 * @param string1
	 *            The first passed String
	 * @param string2
	 *            The second passed String
	 * @return The first passed String if not null, otherwise the second if not
	 *         null, otherwise an empty but non-null String
	 */
	public static String checkNull(String string1, String string2) {
		if (string1 != null)
			return string1;
		else if (string2 != null)
			return string2;
		else
			return "";
	}

	/**
	 * Returns the first passed String if not null, otherwise the second if not
	 * null, otherwise the third if not null, otherwise an empty but non-null
	 * String.
	 * 
	 * @param string1
	 *            The first passed String
	 * @param string2
	 *            The second passed String
	 * @param string3
	 *            The third passed String
	 * @return The first passed String if not null, otherwise the second if not
	 *         null, otherwise the third if not null, otherwise an empty but
	 *         non-null String
	 */
	public static String checkNull(String string1, String string2, String string3) {
		if (string1 != null)
			return string1;
		else if (string2 != null)
			return string2;
		else if (string3 != null)
			return string3;
		else
			return "";
	}

	/**
	 * Returns the first passed String if not null, otherwise the second if not
	 * null, otherwise the third if not null, otherwise the fourth if not null,
	 * otherwise an empty but non-null String.
	 * 
	 * @param string1
	 *            The first passed String
	 * @param string2
	 *            The second passed String
	 * @param string3
	 *            The third passed String
	 * @param string4
	 *            The fourth passed String
	 * @return The first passed String if not null, otherwise the second if not
	 *         null, otherwise the third if not null, otherwise the fourth if not
	 *         null, otherwise an empty but non-null String
	 */
	public static String checkNull(String string1, String string2, String string3, String string4) {
		if (string1 != null)
			return string1;
		else if (string2 != null)
			return string2;
		else if (string3 != null)
			return string3;
		else if (string4 != null)
			return string4;
		else
			return "";
	}

	/**
	 * Returns <code>pre + base + post</code> if base String is not null or empty,
	 * otherwise an empty but non-null String.
	 * 
	 * @param base
	 *            The base String
	 * @param pre
	 *            The pre String
	 * @param post
	 *            The post String
	 * @return <code>pre + base + post</code> if base String is not null or empty,
	 *         otherwise an empty but non-null String.
	 */
	public static String ifNotEmpty(String base, String pre, String post) {
		if (UtilValidate.isNotEmpty(base))
			return pre + base + post;
		else
			return "";
	}

	/**
	 * Returns the first passed String if not empty, otherwise the second if not
	 * empty, otherwise an empty but non-null String.
	 * 
	 * @param string1
	 *            The first passed String
	 * @param string2
	 *            The second passed String
	 * @return The first passed String if not empty, otherwise the second if not
	 *         empty, otherwise an empty but non-null String
	 */
	public static String checkEmpty(String string1, String string2) {
		if (UtilValidate.isNotEmpty(string1))
			return string1;
		else if (UtilValidate.isNotEmpty(string2))
			return string2;
		else
			return "";
	}

	/**
	 * Returns the first passed String if not empty, otherwise the second if not
	 * empty, otherwise the third if not empty, otherwise an empty but non-null
	 * String.
	 * 
	 * @param string1
	 *            The first passed String
	 * @param string2
	 *            The second passed String
	 * @param string3
	 *            The third passed String
	 * @return The first passed String if not empty, otherwise the second if not
	 *         empty, otherwise the third if not empty, otherwise an empty but
	 *         non-null String
	 */
	public static String checkEmpty(String string1, String string2, String string3) {
		if (UtilValidate.isNotEmpty(string1))
			return string1;
		else if (UtilValidate.isNotEmpty(string2))
			return string2;
		else if (UtilValidate.isNotEmpty(string3))
			return string3;
		else
			return "";
	}

	// ------------------- web encode handlers -------------------
	/**
	 * Encodes an HTTP URL query String, replacing characters used for other things
	 * in HTTP URL query strings, but not touching the separator characters '?',
	 * '=', and '&amp;'
	 * 
	 * @param query
	 *            The plain query String
	 * @return The encoded String
	 */
	public static String encodeQuery(String query) {
		String retString;

		retString = replaceString(query, "%", "%25");
		retString = replaceString(retString, " ", "%20");
		return retString;
	}

	/**
	 * Encodes a single HTTP URL query value, replacing characters used for other
	 * things in HTTP URL query strings
	 * 
	 * @param query
	 *            The plain query value String
	 * @return The encoded String
	 */
	public static String encodeQueryValue(String query) {
		String retString;

		retString = replaceString(query, "%", "%25");
		retString = replaceString(retString, " ", "%20");
		retString = replaceString(retString, "&", "%26");
		retString = replaceString(retString, "?", "%3F");
		retString = replaceString(retString, "=", "%3D");
		return retString;
	}

	/**
	 * Replaces all occurrences of oldString in mainString with newString
	 * 
	 * @param mainString
	 *            The original string
	 * @param oldString
	 *            The string to replace
	 * @param newString
	 *            The string to insert in place of the old
	 * @return mainString with all occurrences of oldString replaced by newString
	 */
	public static String replaceString(String mainString, String oldString, String newString) {
		return UtilString.replaceString(mainString, oldString, newString);
	}

	/**
	 * Decodes a single query value from an HTTP URL parameter, replacing %ASCII
	 * values with characters
	 * 
	 * @param query
	 *            The encoded query value String
	 * @return The plain, decoded String
	 */
	public static String decodeQueryValue(String query) {
		String retString;

		retString = replaceString(query, "%25", "%");
		retString = replaceString(retString, "%20", " ");
		retString = replaceString(retString, "%26", "&");
		retString = replaceString(retString, "%3F", "?");
		retString = replaceString(retString, "%3D", "=");
		return retString;
	}

	// ------------------- web encode handlers -------------------
	/**
	 * Encodes an XML string replacing the characters '&lt;', '&gt;', '&quot;',
	 * '&#39;', '&amp;'
	 * 
	 * @param inString
	 *            The plain value String
	 * @return The encoded String
	 */
	public static String encodeXmlValue(String inString) {
		String retString = inString;

		retString = UtilString.replaceString(retString, "&", "&amp;");
		retString = UtilString.replaceString(retString, "<", "&lt;");
		retString = UtilString.replaceString(retString, ">", "&gt;");
		retString = UtilString.replaceString(retString, "\"", "&quot;");
		retString = UtilString.replaceString(retString, "'", "&apos;");
		return retString;
	}

	public static String padString(String str, int setLen, boolean padEnd, char padChar) {
		if (str == null) {
			return null;
		}
		if (setLen == 0) {
			return str;
		}
		int stringLen = str.length();
		int diff = setLen - stringLen;
		if (diff < 0) {
			return str.substring(0, setLen);
		} else {
			StringBuilder newString = new StringBuilder();
			if (padEnd) {
				newString.append(str);
			}
			for (int i = 0; i < diff; i++) {
				newString.append(padChar);
			}
			if (!padEnd) {
				newString.append(str);
			}
			return newString.toString();
		}
	}

	public static String makeSqlSafe(String unsafeString) {
		return unsafeString.replaceAll("'", "''");
	}

	public static String formatPrintableCreditCard(String original) {
		if (original == null)
			return null;
		if (original.length() <= 4)
			return original;

		StringBuilder buffer = new StringBuilder();
		for (int i = 0; i < original.length() - 4; i++) {
			buffer.append('*');
		}
		buffer.append(original.substring(original.length() - 4));
		return buffer.toString();
	}
}
