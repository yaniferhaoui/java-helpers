package com.yferhaoui.security_helper;

import java.io.Serializable;
import java.security.PublicKey;

public final class Message implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8497883692190173723L;
	public final static String DEFAULT_ASYMETRIC_SIGNATURE_ALGORITHM = "SHA256withRSA";

	private final PublicKey senderPublicKey;
	private final Serializable serializableObject;
	private final String asymetricSignatureAlgorithm;

	public Message(final PublicKey senderPublicKey, final Serializable serializableObject) {
		this.senderPublicKey = senderPublicKey;
		this.serializableObject = serializableObject;
		this.asymetricSignatureAlgorithm = Message.DEFAULT_ASYMETRIC_SIGNATURE_ALGORITHM;
	}

	public Message(final PublicKey senderPublicKey, //
			final Serializable serializableObject, //
			final String asymetricSignatureAlgorithm) {

		this.senderPublicKey = senderPublicKey;
		this.serializableObject = serializableObject;
		this.asymetricSignatureAlgorithm = asymetricSignatureAlgorithm;
	}

	// Getters
	public final PublicKey getSenderPublicKey() {
		return this.senderPublicKey;
	}

	public final Serializable getSerializableObject() {
		return this.serializableObject;
	}

	public final String getAsymetricSignatureAlgorithm() {
		return this.asymetricSignatureAlgorithm;
	}
}
