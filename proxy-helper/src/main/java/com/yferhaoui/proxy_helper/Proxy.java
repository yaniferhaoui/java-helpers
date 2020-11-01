package com.yferhaoui.proxy_helper;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.yferhaoui.basic.helper.TimeHelper;

public final class Proxy implements Comparable<Proxy> {

	private final String proxyID;
	private String domain;
	private int port;
	private String company;

	public Proxy(final String domain, final int port, final String company) {
		this.proxyID = TimeHelper.getValidHexaKey();
		this.domain = domain;
		this.port = port;
		this.company = company;
	}

	public Proxy(final ResultSet rs) throws SQLException {
		this.proxyID = rs.getString("proxyID");
		this.domain = rs.getString("proxyDomain");
		this.port = rs.getInt("proxyPort");
		this.company = rs.getString("proxyCompany");
	}

	// Configuration of the proxy
	public synchronized final void setUpProxy() {
		
		System.setProperty("java.net.useSystemProxies", "true");
		System.setProperty("http.proxyHost", this.domain);
		System.setProperty("http.proxyPort", String.valueOf(this.port));
		System.setProperty("https.proxyHost", this.domain);
		System.setProperty("https.proxyPort", String.valueOf(this.port));
		System.setProperty("socksProxyHost", this.domain);
		System.setProperty("socksProxyPort ", String.valueOf(this.port));
	}

	public synchronized final static void clearProxy() {
		
		System.setProperty("java.net.useSystemProxies", "false");
		System.clearProperty("http.proxyHost");
		System.clearProperty("http.proxyPort");
		System.clearProperty("https.proxyHost");
		System.clearProperty("https.proxyPort");
		System.clearProperty("socksProxyHost");
		System.clearProperty("socksProxyPort");
	}

	// Setter
	public final void setDomain(final String domain) {
		this.domain = domain;
	}

	public final void setPort(final int port) {
		this.port = port;
	}

	public final void setCompany(final String company) {
		this.company = company;
	}

	// Getter
	public final String getProxyID() {
		return this.proxyID;
	}

	public final String getDomain() {
		return this.domain;
	}

	public final int getPort() {
		return this.port;
	}

	public final String getCompany() {
		return this.company;
	}

	@Override
	public final String toString() {
		return this.proxyID + " => " + this.domain + ":" + this.port + " => " + this.company;
	}

	public final int compareTo(final Proxy arg0) {
		return this.proxyID.compareTo(arg0.proxyID);
	}
}
