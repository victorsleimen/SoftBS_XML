package com.softlynx.util;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.Reader;
import java.io.StringReader;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.security.KeyFactory;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * KeyStoreUtil - Utilities for getting KeyManagers and TrustManagers
 */
public final class UtilKeyStore {

	final static Logger logger = LoggerFactory.getLogger(UtilKeyStore.class);
	public static final String module = UtilKeyStore.class.getName();

	private UtilKeyStore() {
	}

	public static KeyStore getStore(URL url, String password) throws IOException, GeneralSecurityException {
		return getStore(url, password, "jks");
	}

	public static KeyStore getStore(URL url, String password, String type)
			throws IOException, GeneralSecurityException {
		if (type == null) {
			throw new IOException("Invalid keystore type; null");
		}
		KeyStore ks = KeyStore.getInstance(type);
		InputStream in = url.openStream();
		try {
			ks.load(in, password.toCharArray());
		} finally {
			in.close();
		}
		return ks;
	}

	public static KeyStore getSystemTrustStore() throws IOException, GeneralSecurityException {
		String javaHome = System.getProperty("java.home");
		String fileName = System.getProperty("javax.net.ssl.trustStore");
		String password = System.getProperty("javax.net.ssl.trustStorePassword");
		if (password == null) {
			password = "changeit";
		}

		KeyStore ks = KeyStore.getInstance("jks");
		File keyFile = null;
		if (fileName != null) {
			keyFile = UtilFile.getFile(fileName);
		} else {
			keyFile = UtilFile.getFile(javaHome + "/lib/security/jssecacerts");
			if (!keyFile.exists() || !keyFile.canRead()) {
				keyFile = UtilFile.getFile(javaHome + "/lib/security/cacerts");
			}
		}

		if (keyFile.exists() && keyFile.canRead()) {
			InputStream in = new FileInputStream(keyFile);
			try {
				ks.load(in, password.toCharArray());
			} finally {
				in.close();
			}
		} else {
			ks.load(null, "changeit".toCharArray());
		}
		return ks;
	}

	public static X509Certificate readCertificate(byte[] certChain) throws CertificateException {
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		ByteArrayInputStream bais = new ByteArrayInputStream(certChain);
		return (X509Certificate) cf.generateCertificate(bais);
	}

	public static Map<String, String> getCertX500Map(java.security.cert.X509Certificate cert) {
		return getX500Map(cert.getSubjectDN());
	}

	public static Map<String, String> getX500Map(Principal x500) {
		Map<String, String> x500Map = new HashMap<String, String>();

		String name = x500.getName().replaceAll("\\\\,", "&com;");
		String[] x500Opts = name.split("\\,");
		for (String opt : x500Opts) {
			if (opt.indexOf("=") > -1) {
				String[] nv = opt.split("\\=", 2);
				x500Map.put(nv[0].replaceAll("&com;", ","), nv[1].replaceAll("&com;", ","));
			}
		}

		return x500Map;
	}

	public static void importPKCS8CertChain(KeyStore ks, String alias, byte[] keyBytes, String keyPass,
			byte[] certChain)
			throws InvalidKeySpecException, NoSuchAlgorithmException, CertificateException, KeyStoreException {
		// load the private key
		KeyFactory kf = KeyFactory.getInstance("RSA");
		PKCS8EncodedKeySpec keysp = new PKCS8EncodedKeySpec(keyBytes);
		PrivateKey pk = kf.generatePrivate(keysp);

		// load the cert chain
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		ByteArrayInputStream bais = new ByteArrayInputStream(certChain);

		Collection<? extends Certificate> certCol = cf.generateCertificates(bais);
		Certificate[] certs = new Certificate[certCol.toArray().length];
		if (certCol.size() == 1) {
			logger.info("Single certificate; no chain", module);
			bais = new ByteArrayInputStream(certChain);
			Certificate cert = cf.generateCertificate(bais);
			certs[0] = cert;
		} else {
			logger.info("Certificate chain length : " + certCol.size(), module);
			certs = certCol.toArray(new Certificate[certCol.size()]);
		}

		ks.setKeyEntry(alias, pk, keyPass.toCharArray(), certs);
	}

	public static String certToString(Certificate cert) throws CertificateEncodingException {
		byte[] certBuf = cert.getEncoded();
		StringBuilder buf = new StringBuilder();
		buf.append("-----BEGIN CERTIFICATE-----\n");
		buf.append(new String(Base64.encodeBase64Chunked(certBuf)));
		buf.append("\n-----END CERTIFICATE-----\n");
		return buf.toString();
	}

	public static Certificate pemToCert(String certString) throws IOException, CertificateException {
		return pemToCert(new StringReader(certString));
	}

	public static Certificate pemToCert(File certFile) throws IOException, CertificateException {
		return pemToCert(new FileInputStream(certFile));
	}

	public static Certificate pemToCert(InputStream is) throws IOException, CertificateException {
		return pemToCert(new InputStreamReader(is));
	}

	public static Certificate pemToCert(Reader r) throws IOException, CertificateException {
		String header = "-----BEGIN CERTIFICATE-----";
		String footer = "-----END CERTIFICATE-----";

		BufferedReader reader = new BufferedReader(r);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);

		String line;

		// ignore up to the header
		while ((line = reader.readLine()) != null && !line.equals(header)) {
		}

		// no header found
		if (line == null) {
			throw new IOException("Error reading certificate, missing BEGIN boundary");
		}

		// in between the header and footer is the actual certificate
		while ((line = reader.readLine()) != null && !line.equals(footer)) {
			line = line.replaceAll("\\s", "");
			ps.print(line);
		}

		// no footer found
		if (line == null) {
			throw new IOException("Error reading certificate, missing END boundary");
		}
		ps.close();

		// decode the buffer to a X509Certificate

		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		byte[] certBytes = Base64.decodeBase64(baos.toByteArray());
		return cf.generateCertificate(new ByteArrayInputStream(certBytes));
	}

	public static String pemToPkHex(String certString) throws IOException, CertificateException {
		Certificate cert = pemToCert(certString);
		return UtilString.toHexString(cert.getPublicKey().getEncoded());
	}
}
