package com.yferhaoui.basic.helper;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public final class NetworkHelper {
	
	public final static boolean netIsAvailable() {
		return NetworkHelper.netIsAvailable(true);
	}

	private final static boolean netIsAvailable(final boolean retry) {
		try {
			// CloudFlare
			final URLConnection conn = new URL("http://1.1.1.1").openConnection();
			conn.connect();
			conn.getInputStream().close();
			return true;

		} catch (final MalformedURLException e) {
			throw new RuntimeException(e);

		} catch (final IOException e) {

			// Sleep 100 ms
			TimeHelper.sleepUninterruptibly(100);

			if (retry) {
				return NetworkHelper.netIsAvailable(false);
			}
		}
		return false;
	}
}
