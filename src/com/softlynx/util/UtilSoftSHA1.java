package com.softlynx.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * EN: Utility class for encrypting the plain text password as an SHA1 hash.<br>
 * A 'salt' is a used for decrypting the plain password. This 'salt' is loaded
 * from a property file. <br>
 * ----------------------------------------------------------------------------<br>
 * 
 * @see /src/main/resources/securitySalt.properties
 * @author Stephan Gerth
 */
public class UtilSoftSHA1 implements Serializable {

	/**
	 * Serial Version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Error message for missing salt.
	 */
	private static String SALT_FOR_ENCODING_IS_MISSING = "salt for encoding is missing";

	/**
	 * SHA stength as supported by the JVM.<br>
	 * Possible strength = 1, 256, 384, 512<br>
	 */
	private static int SHA_STRENGTH = 512;

	/**
	 * Default constructor.
	 */
	public UtilSoftSHA1() {
		super();
	}

	/**
	 * Create the encrypted SHA1 hash for a plain password with a salt.
	 * 
	 * @param rawPassword
	 *            The plain password.
	 * @param salt
	 *            The salt for using in encryption.
	 * @return A map with rawPassword | MD5 hash
	 */
	public static Map<String, String> doCreatePassword(CharSequence rawPassword) {

		Map<String, String> result = new HashMap<String, String>(0);

		String encPassword = encrypt(rawPassword);
		result.put("rawPassword", rawPassword.toString());
		result.put("md5Hash", encPassword);
		System.out.println(rawPassword + " | " + encPassword);

		return result;
	}

	/**
	 * Creates a random 12-digit password from a defined array of characters and
	 * numbers and encrypt this with a fixed 'salt' to a SHA1 hash.<br>
	 * Used if user forces a new password/key pair, because he has forgotten the
	 * password.<br>
	 * used
	 * chars='abcdefghijklmnopqrstuvwxyzAABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890'<br>
	 * 
	 * @param salt
	 *            The salt for using in decryption.
	 * 
	 * @return a pair of password and the encrypted hash. result.put("rawPassword",
	 *         rawPassword);<br>
	 *         result.put("sha1Hash", sha1Hash);<br>
	 */
	@Deprecated
	public static Map<String, String> doCreateRandomPasswordPair() {

		Map<String, String> result = new HashMap<String, String>(0);

		String chars = "abcdefghijklmnopqrstuvwxyzAABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		int countChars = chars.length();

		String rawPassword = RandomStringUtils.random(12, 0, countChars, true, true, chars.toCharArray());
		String sha1Hash = encrypt(rawPassword);
		result.put("rawPassword", rawPassword);
		result.put("sha1Hash", sha1Hash);

		return result;
	}

	/**
	 * Encrypt the plain text password as an SHA1 hash with a 'salt'.
	 * 
	 * @param rawPass
	 *            The plain text password
	 * @param salt
	 *            The salt for using in encryption.
	 * 
	 * @return SHA1 hash
	 */
	public static String encrypt(CharSequence rawPass) {

		String salt = getSalt();

		if (salt == null || salt.length() < 1) {
			throw new RuntimeException(SALT_FOR_ENCODING_IS_MISSING);
		}

		BCryptPasswordEncoder pe = new BCryptPasswordEncoder(SHA_STRENGTH);
		String encPassword = pe.encode(rawPass);

		return encPassword;
	}

	/**
	 * Checks if the overhanded plain passwort matches the overhanded encrypted
	 * password.
	 * 
	 * @param plainPassword
	 *            The plain password.
	 * @param encryptedPassword
	 *            The encrypted password.
	 * @param salt
	 *            The salt for using in encryption.
	 * @return true if it matches.
	 */
	public static boolean matches(String plainPassword, String encryptedPassword) {

		String salt = getSalt();

		if (salt == null || salt.length() < 1) {
			throw new RuntimeException(SALT_FOR_ENCODING_IS_MISSING);
		}

		if (encrypt(plainPassword).equals(encryptedPassword)) {
			return true;
		}

		return false;
	}

	/**
	 * Gets the 'salt' from property file. salt key = "k2H7be36Ka"
	 * 
	 * @return The salt as string.
	 */
	private static String getSalt() {

		Properties prop = new Properties();
		InputStream input = null;

		try {
			String filename = "securitySalt.properties";
			input = UtilSoftSHA1.class.getClassLoader().getResourceAsStream(filename);

			if (input == null) {
				throw new RuntimeException("file: '" + filename + "' not found on classpath.");
			}

			/**
			 * Load the salt properties file from class path and get the salt.
			 */
			prop.load(input);
			String salt = prop.getProperty("salt");

			return salt.trim();

		} catch (Exception e) {
			throw new RuntimeException(e.getLocalizedMessage());
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Run this main method as java application for creating a SHA1 encrypted
	 * password. Used for setting up demo data.
	 */
	// public static void main(String[] args) {
	// // doCreateRandomPasswordPair();
	// doCreatePassword("heike");
	// }

}
