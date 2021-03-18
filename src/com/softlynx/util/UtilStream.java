package com.softlynx.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Scanner;

public class UtilStream {

	public static void nbTransfer(InputStream is, OutputStream os, String label) throws IOException {
		int nread;
		byte buf[] = new byte[8192];

		while ((is.available() > 0) && ((nread = is.read(buf)) != -1)) {
			if (label.length() > 0) {
				System.out.println(label + new String(buf, 0, nread));
			}
			os.write(buf, 0, nread);
		}
	}

	public static void nbTransfer(InputStream is, OutputStream os) throws IOException {
		nbTransfer(is, os, "");
	}

	public static void transfer(InputStream is, OutputStream os, String label) throws IOException {
		int nread;
		byte buf[] = new byte[8192];

		while ((nread = is.read(buf)) != -1) {
			if (label.length() > 0) {
				System.out.println(label + new String(buf, 0, nread));
			}
			os.write(buf, 0, nread);
		}
	}

	public static void transfer(InputStream is, OutputStream os) throws IOException {
		transfer(is, os, "");
	}

	public static void nbTransfer(InputStream is, OutputStream os, String label, int numTries, long sleepInterval)
			throws IOException {
		int nread;
		byte buf[] = new byte[8192];

		while (available(is, numTries, sleepInterval) && ((nread = is.read(buf)) != -1)) {
			if (label.length() > 0) {
				System.out.println(label + new String(buf, 0, nread));
			}
			os.write(buf, 0, nread);
		}
	}

	public static void nbTransfer(InputStream is, OutputStream os, int numTries, long sleepInterval)
			throws IOException {
		nbTransfer(is, os, "", numTries, sleepInterval);
	}

	public static boolean available(InputStream is, int numTries, long sleepInterval) {
		try {
			for (int i = 0; i < numTries; i++) {
				if (is.available() > 0)
					return true;

				try {
					Thread.sleep(sleepInterval);
				} catch (Exception ex) {
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

	public static String readToString(InputStream is) {
		try (Scanner scanner = new Scanner(is).useDelimiter("\\A")) {
			String str = scanner.hasNext() ? scanner.next() : "";
			scanner.close();
			return str;
		}
	}

	public static String readFile(File file) throws IOException {
		int nread;
		byte buf[] = new byte[8192];
		String content = "";

		FileInputStream fis = new FileInputStream(file);
		while ((nread = fis.read(buf)) != -1) {
			content += new String(buf, 0, nread);
		}
		fis.close();
		return content;
	}
}