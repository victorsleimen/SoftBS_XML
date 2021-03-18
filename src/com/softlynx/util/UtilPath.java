package com.softlynx.util;

import java.io.File;

public class UtilPath {
	public static String getRelativePath(File file) {
		if (file == null) {
			return null;
		}

		String filePath = file.getAbsolutePath();
		String userDir = System.getProperty("user.dir");

		if (userDir != null && userDir.length() > 0 && filePath.startsWith(userDir)) {
			// Including the file separator.
			filePath = filePath.substring(userDir.length() + File.separator.length());
		}
		return filePath;
	}
}