package com.softlynx.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javassist.NotFoundException;

/**
 * Generic Property Accessor with Cache - Utilities for working with properties
 * files.
 * <p>
 * PropertiesUtil divides properties files into two classes: non-locale-specific
 * - which are used for application parameters, configuration settings, etc; and
 * locale-specific - which are used for UI labels, system messages, etc. Each
 * class of properties files is kept in its own cache.
 * </p>
 * <p>
 * The locale-specific class of properties files can be in any one of three
 * formats: the standard text-based key=value format (*.properties file), the
 * Java XML properties format, and the OFBiz-specific XML file format (see the
 * <a href=
 * "#xmlToProperties(java.io.InputStream,%20java.util.Locale,%20java.util.Properties)">xmlToProperties</a>
 * method).
 * </p>
 */
@SuppressWarnings("serial")
public final class UtilProperties implements Serializable {

	final static Logger logger = LoggerFactory.getLogger(UtilProperties.class);
	public static final String module = UtilProperties.class.getName();

	private UtilProperties() {
	}

	private static final Set<String> propertiesNotFound = new HashSet<String>();

	/**
	 * Returns a new <code>Properties</code> instance created from
	 * <code>fileName</code>.
	 * <p>
	 * This method is intended for low-level framework classes that need to read
	 * properties files before OFBiz has been fully initialized.
	 * </p>
	 * 
	 * @param fileName
	 *            The full name of the properties file ("foo.properties")
	 * @return A new <code>Properties</code> instance created from
	 *         <code>fileName</code>, or <code>null</code> if the file was not found
	 * @throws IllegalArgumentException
	 *             if <code>fileName</code> is empty
	 * @throws IllegalStateException
	 *             if there was a problem reading the file
	 */
	public static Properties createProperties(String fileName) {
		UtilAssert.notEmpty("fileName", fileName);
		InputStream inStream = null;
		try {
			URL url = Thread.currentThread().getContextClassLoader().getResource(fileName);
			if (url == null) {
				return null;
			}
			inStream = url.openStream();
			Properties properties = new Properties();
			properties.load(inStream);
			return properties;
		} catch (Exception e) {
			throw new IllegalStateException("Exception thrown while reading " + fileName + ": " + e);
		} finally {
			if (inStream != null) {
				try {
					inStream.close();
				} catch (IOException e) {
					System.out.println("Exception thrown while closing InputStream: " + e);
				}
			}
		}
	}

	/**
	 * Converts a Locale instance to a candidate Locale list. The list is ordered
	 * most-specific to least-specific. Example:
	 * <code>localeToCandidateList(Locale.US)</code> would return a list containing
	 * <code>en_US</code> and <code>en</code>.
	 * 
	 * @return A list of candidate locales.
	 */
	public static List<Locale> localeToCandidateList(Locale locale) {
		List<Locale> localeList = new LinkedList<Locale>();
		localeList.add(locale);
		String localeString = locale.toString();
		int pos = localeString.lastIndexOf("_", localeString.length());
		while (pos != -1) {
			localeString = localeString.substring(0, pos);
			localeList.add(new Locale(localeString));
			pos = localeString.lastIndexOf("_", localeString.length());
		}
		return localeList;
	}

	/**
	 * Create a localized resource name based on a resource name and a locale.
	 * 
	 * @param resource
	 *            The desired resource
	 * @param locale
	 *            The desired locale
	 * @param removeExtension
	 *            Remove file extension from resource String
	 * @return Localized resource name
	 */
	public static String createResourceName(String resource, Locale locale, boolean removeExtension) {
		String resourceName = resource;
		if (removeExtension) {
			if (resourceName.endsWith(".xml")) {
				resourceName = resourceName.replace(".xml", "");
			} else if (resourceName.endsWith(".properties")) {
				resourceName = resourceName.replace(".properties", "");
			}
		}
		if (locale != null) {
			if (UtilValidate.isNotEmpty(locale.toString())) {
				resourceName = resourceName + "_" + locale;
			}
		}
		return resourceName;
	}

	public static boolean isPropertiesResourceNotFound(String resource, Locale locale, boolean removeExtension) {
		return propertiesNotFound.contains(createResourceName(resource, locale, removeExtension));
	}
	
	private SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

	public static String getString(Properties properties, String property) throws NotFoundException {
		if (properties.containsKey(property)) {
			return properties.getProperty(property);
		}

		throw new NotFoundException("Property: '" + property + "' has not been defined.");
	}

	public static String getString(Properties properties, String property, String defaultValue) {
		if (properties.containsKey(property)) {
			return properties.getProperty(property);
		}

		return defaultValue;
	}

	public boolean getBoolean(Properties properties, String property) throws NotFoundException {
		if (properties.containsKey(property)) {
			return properties.getProperty(property).equalsIgnoreCase("true");
		}

		throw new NotFoundException("Property: '" + property + "' has not been defined.");
	}

	public boolean getBoolean(Properties properties, String property, boolean defaultValue) {
		if (properties.containsKey(property)) {
			return properties.getProperty(property).equalsIgnoreCase("true");
		}

		return defaultValue;
	}

	public Integer getInteger(Properties properties, String property) throws NotFoundException {
		if (properties.containsKey(property)) {
			return Integer.valueOf(properties.getProperty(property));
		}

		throw new NotFoundException("Property: '" + property + "' has not been defined.");
	}

	public Integer getInteger(Properties properties, String property, int defaultValue) {
		return getInteger(properties, property, Integer.valueOf(defaultValue));
	}

	public Integer getInteger(Properties properties, String property, Integer defaultValue) {
		if (properties.containsKey(property)) {
			return Integer.valueOf(properties.getProperty(property));
		}

		return defaultValue;
	}

	public Double getDouble(Properties properties, String property) throws NotFoundException {
		if (properties.containsKey(property)) {
			return Double.valueOf(properties.getProperty(property));
		}

		throw new NotFoundException("Property: '" + property + "' has not been defined.");
	}

	public Double getDouble(Properties properties, String property, double defaultValue) {
		return getDouble(properties, property, Double.valueOf(defaultValue));
	}

	public Double getDouble(Properties properties, String property, Double defaultValue) {
		if (properties.containsKey(property)) {
			return Double.valueOf(properties.getProperty(property));
		}

		return defaultValue;
	}

	public Date getDate(Properties properties, String property) throws NotFoundException, ParseException {
		if (properties.containsKey(property)) {
			return dateFormat.parse(properties.getProperty(property));
		}

		throw new NotFoundException("Property: '" + property + "' has not been defined.");
	}

	public Date getDate(Properties properties, String property, Date defaultValue) {
		return getDate(properties, property, defaultValue);
	}

}
