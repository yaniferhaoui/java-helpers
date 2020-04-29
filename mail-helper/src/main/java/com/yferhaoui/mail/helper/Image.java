package com.yferhaoui.mail.helper;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

public final class Image {

	private final String name;
	private final byte[] img;
	private final String mimeType;

	public Image(final String name, final byte[] img) throws IOException {
		this.name = name;
		this.img = img;
		
		final InputStream is = new BufferedInputStream(new ByteArrayInputStream(this.img));
		this.mimeType = URLConnection.guessContentTypeFromStream(is);
	}

	public Image(final String name, final File file) throws IOException {
		this.name = name;
		this.img = new byte[(int) file.length()];
		final FileInputStream fis = new FileInputStream(file);
		fis.read(this.img);
		fis.close();
		final InputStream is = new BufferedInputStream(new ByteArrayInputStream(this.img));
		this.mimeType = URLConnection.guessContentTypeFromStream(is);

	}

	// Getters
	public final String getName() {
		return this.name;
	}

	public final byte[] getImg() {
		return this.img;
	}

	public final String getMimeType() {
		return this.mimeType;
	}
}
