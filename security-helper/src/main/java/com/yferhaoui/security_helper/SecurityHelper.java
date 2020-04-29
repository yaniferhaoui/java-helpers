package com.yferhaoui.security_helper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

public final class SecurityHelper {

	private final static String DEFAULT_ASYMETRIC_ALGORITHM = "RSA";

	public final static KeyPair generateKeyPair() throws NoSuchAlgorithmException {
		final KeyPairGenerator kpg = KeyPairGenerator.getInstance(SecurityHelper.DEFAULT_ASYMETRIC_ALGORITHM);
		kpg.initialize(2048);
		return kpg.generateKeyPair();
	}

	public final static String getStringOfPublicKey(final PublicKey publicKey) {
		return Base64.getEncoder().encodeToString(publicKey.getEncoded());
	}

	public final static String getStringOfPrivateKey(final PrivateKey privateKey) {
		return Base64.getEncoder().encodeToString(privateKey.getEncoded());
	}

	public final static PrivateKey getPrivateKeyFromString(final String privateKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException {

		final byte[] bytes = Base64.getDecoder().decode(privateKey);
		final PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(bytes);
		final KeyFactory kf = KeyFactory.getInstance(SecurityHelper.DEFAULT_ASYMETRIC_ALGORITHM);
		return kf.generatePrivate(keySpec);
	}

	public final static PublicKey getPublicKeyFromString(final String publicKey)
			throws NoSuchAlgorithmException, InvalidKeySpecException {

		final byte[] bytes = Base64.getDecoder().decode(publicKey);
		final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
		final KeyFactory kf = KeyFactory.getInstance(SecurityHelper.DEFAULT_ASYMETRIC_ALGORITHM);
		return kf.generatePublic(keySpec);
	}

	public final static void storePairKeyInFile(final File file, final KeyPair keyPair) throws IOException {
		final BufferedWriter writer = new BufferedWriter(new FileWriter(file));
		writer.write("PublicKey=" + SecurityHelper.getStringOfPublicKey(keyPair.getPublic()) + "\n");
		writer.write("PrivateKey=" + SecurityHelper.getStringOfPrivateKey(keyPair.getPrivate()));
		writer.close();
	}
}
