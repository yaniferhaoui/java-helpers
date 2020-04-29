package com.yferhaoui.security_helper;

import java.io.IOException;
import java.io.Serializable;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map.Entry;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

import org.junit.jupiter.api.Assertions;

public class SecurityTest {

	public final void testEncryptAndDecrypt()
			throws NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException,
			SignatureException, BadPaddingException, IOException, ClassNotFoundException {

		final StringBuilder sb1 = new StringBuilder("This StringBuilder is a simple java object :)");

		final KeyPair senderKeyPair = SecurityHelper.generateKeyPair();
		final KeyPair receiverKeyPair = SecurityHelper.generateKeyPair();

		final SecureObject secureObject = SecureObject.encrypt(sb1, //
				senderKeyPair.getPublic(), //
				senderKeyPair.getPrivate(), //
				receiverKeyPair.getPublic());

		final Entry<PublicKey, Serializable> e = secureObject.decrypt(receiverKeyPair.getPrivate());
		final StringBuilder sb2 = (StringBuilder) e.getValue();
		Assertions.assertEquals(sb1.toString(), sb2.toString());

		final PublicKey senderPublicKey = e.getKey();
		Assertions.assertEquals(senderKeyPair.getPublic(), senderPublicKey);
	}

	public final void testStringOfPrivateKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
		final KeyPair keyPair = SecurityHelper.generateKeyPair();
		final PrivateKey privateKey1 = keyPair.getPrivate();
		final String privateKeyString = SecurityHelper.getStringOfPrivateKey(privateKey1);
		
		// Now we test equality
		final PrivateKey privateKey2 = SecurityHelper.getPrivateKeyFromString(privateKeyString);
		
		Assertions.assertEquals(privateKey1, privateKey2);
	}

	public final void testStringOfPublicKey() throws NoSuchAlgorithmException, InvalidKeySpecException {
		final KeyPair keyPair = SecurityHelper.generateKeyPair();
		final PublicKey publicKey1 = keyPair.getPublic();
		final String publicKeyString = SecurityHelper.getStringOfPublicKey(publicKey1);
		
		// Now we test equality
		final PublicKey publicKey2 = SecurityHelper.getPublicKeyFromString(publicKeyString);
		
		Assertions.assertEquals(publicKey1, publicKey2);
	}
}
