package com.yferhaoui.security_helper;

import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.SignedObject;
import java.util.Map.Entry;
import java.util.AbstractMap.SimpleEntry;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;

import org.apache.commons.lang3.SerializationUtils;

public final class SecureObject {

	private final static String DEFAULT_SYMMETRIC_ALGORITHM = "AES";

	private final byte[] encryptedSecretKey;
	private final byte[] encryptedSymetricEncryptionAlgorithm;
	private final SealedObject sealedObject;

	private SecureObject(final byte[] encryptedSymetricEncryptionAlgorithm, //
			final byte[] encryptedSecretKey, //
			final SealedObject sealedObject) {

		this.encryptedSecretKey = encryptedSecretKey;
		this.encryptedSymetricEncryptionAlgorithm = encryptedSymetricEncryptionAlgorithm;
		this.sealedObject = sealedObject;
	}

	public final static SecureObject encrypt(final Serializable serializableObjectToEncrypt, //
			final PublicKey senderPublicKey, //
			final PrivateKey senderPrivateKey, //
			final PublicKey receiverPublicKey) throws IllegalBlockSizeException, IOException, NoSuchAlgorithmException,
			NoSuchPaddingException, InvalidKeyException, SignatureException, BadPaddingException {

		// Generate a SecretKey
		final KeyGenerator gen = KeyGenerator.getInstance(SecureObject.DEFAULT_SYMMETRIC_ALGORITHM);
		gen.init(256);
		final SecretKey secretKey = gen.generateKey();

		// Asymetric Signature of the sender PublicKey + UnisgnedObject => Message
		final Message message = new Message(senderPublicKey, serializableObjectToEncrypt);
		final Signature signature = Signature.getInstance(Message.DEFAULT_ASYMETRIC_SIGNATURE_ALGORITHM);
		final SignedObject signedobject = new SignedObject(message, senderPrivateKey, signature);

		// Symetric Encryption with the SignedObject
		final Cipher cipher1 = Cipher.getInstance(SecureObject.DEFAULT_SYMMETRIC_ALGORITHM);
		cipher1.init(Cipher.ENCRYPT_MODE, secretKey);
		final SealedObject sealedObject = new SealedObject(signedobject, cipher1);

		// Asymetric Encryption of the SecretKey
		final Cipher cipher2 = Cipher.getInstance(receiverPublicKey.getAlgorithm());
		cipher2.init(Cipher.ENCRYPT_MODE, receiverPublicKey);
		final byte[] encryptedSecretKey = cipher2.doFinal(SerializationUtils.serialize(secretKey));

		// Asymetric Encryption of the symetric encryption algorithm
		cipher2.init(Cipher.ENCRYPT_MODE, receiverPublicKey);
		final byte[] encryptedSymetricEncryptionAlgorithm = cipher2
				.doFinal(SecureObject.DEFAULT_SYMMETRIC_ALGORITHM.getBytes());

		return new SecureObject(encryptedSymetricEncryptionAlgorithm, encryptedSecretKey, sealedObject);
	}

	public final Entry<PublicKey, Serializable> decrypt(final PrivateKey receiverPrivateKey)
			throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException,
			BadPaddingException, SignatureException, ClassNotFoundException, IOException {

		// Asymetric decryption of the symetric encryption algorithm
		final Cipher cipher1 = Cipher.getInstance(receiverPrivateKey.getAlgorithm());
		cipher1.init(Cipher.DECRYPT_MODE, receiverPrivateKey);
		final String symetricEncryptionAlgorithm = new String(
				cipher1.doFinal(this.encryptedSymetricEncryptionAlgorithm), StandardCharsets.UTF_8);

		// Asymetric decryption of the SecretKey
		cipher1.init(Cipher.DECRYPT_MODE, receiverPrivateKey);
		final SecretKey secretKey = SerializationUtils.deserialize(cipher1.doFinal(this.encryptedSecretKey));

		// Symetric decryption of the SignedObject
		final Cipher cipher2 = Cipher.getInstance(symetricEncryptionAlgorithm);
		cipher2.init(Cipher.DECRYPT_MODE, secretKey);
		final SignedObject signedObject = (SignedObject) this.sealedObject.getObject(cipher2);

		// Get the Message => sender PublicKey + UnsignedObject
		final Message message = (Message) signedObject.getObject();
		final PublicKey senderPublicKey = message.getSenderPublicKey();

		// Asymetric signature verification
		final Signature signature = Signature.getInstance(message.getAsymetricSignatureAlgorithm());
		final boolean verified = signedObject.verify(senderPublicKey, signature);
		if (!verified) {
			throw new SignatureException("The signature isn't verified !");
		}

		// Get the UnsignedObject
		return new SimpleEntry<PublicKey, Serializable>(senderPublicKey, message.getSerializableObject());
	}
}
