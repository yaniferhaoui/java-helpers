package com.yferhaoui.proxy_helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.net.ssl.HttpsURLConnection;

import com.yferhaoui.basic.helper.TimeHelper;

public final class ProxyAccount implements Comparable<ProxyAccount> {

	private final String proxyAccountID;
	private String username;
	private char[] password;
	private String company;

	public ProxyAccount(final String username, final char[] password, final String company) {

		this.proxyAccountID = TimeHelper.getValidHexaKey();
		this.username = username;
		this.password = password;
		this.company = company;
	}

	public ProxyAccount(final String username, final String password, final String company) {

		this(username, password.toCharArray(), company);
	}

	public ProxyAccount(final ResultSet rs) throws SQLException {
		this.proxyAccountID = rs.getString("proxyAccountID");
		this.username = rs.getString("proxyUsername");
		this.password = rs.getString("proxyPassword").toCharArray();
		this.company = rs.getString("proxyCompany");
	}

	// Configuration of the proxy
	public synchronized final void setUpProxy(final Proxy proxy) {
		proxy.setUpProxy();
		this.setUpProxyAccount();
	}

	public synchronized final void setUpProxyAccount() {

		Authenticator.setDefault(new Authenticator() {
			@Override
			public final PasswordAuthentication getPasswordAuthentication() {

				final String username = ProxyAccount.this.username;
				final char[] password = ProxyAccount.this.password;

				return new PasswordAuthentication(username, password);
			}
		});

		System.setProperty("http.proxyUser", this.username);
		System.setProperty("http.proxyPassword", String.valueOf(this.password));
		System.setProperty("https.proxyUser", this.username);
		System.setProperty("https.proxyPassword", String.valueOf(this.password));
		System.setProperty("java.net.socks.username", this.username);
		System.setProperty("java.net.socks.password", String.valueOf(this.password));
		System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");
		System.setProperty("jdk.http.auth.proxying.disabledSchemes", ""); //Do not print this Line ???
	}

	// Setter
	public final void setUsername(final String username) {
		this.username = username;
	}

	public final void setPassword(final String password) {
		this.password = password.toCharArray();
	}

	public final void setPassword(final char[] password) {
		this.password = password;
	}

	public final void setCompany(final String company) {
		this.company = company;
	}

	// Getter
	public final String getProxyAccountID() {
		return this.proxyAccountID;
	}

	public final String getUsername() {
		return this.username;
	}

	public final char[] getPassword() {
		return this.password;
	}

	public final String getPasswordToString() {
		return String.valueOf(this.password);
	}

	public final String getCompany() {
		return this.company;
	}

	@Override
	public final String toString() {
		return this.proxyAccountID + " => " + this.username + " => " + this.company;
	}

	public final int compareTo(final ProxyAccount arg0) {
		return this.proxyAccountID.compareTo(arg0.proxyAccountID);
	}

	public final static void main(final String[] args) throws IOException {

		final String username = "mehdi.ferhaoui@gmail.com";
		final char[] password = "62786278Mehdi".toCharArray();
		final String domain = "fr523.nordvpn.com";
		final int port = 80;

		System.setProperty("http.proxyHost", domain);
		System.setProperty("http.proxyPort", String.valueOf(port));
		System.setProperty("https.proxyHost", domain);
		System.setProperty("https.proxyPort", String.valueOf(port));
		System.setProperty("socksProxyHost", domain);
		System.setProperty("socksProxyPort ", String.valueOf(port));

		Authenticator.setDefault(new Authenticator() {
			@Override
			public final PasswordAuthentication getPasswordAuthentication() {

				return new PasswordAuthentication(username, password);
			}
		});

		System.setProperty("http.proxyUser", username);
		System.setProperty("http.proxyPassword", String.valueOf(password));
		System.setProperty("https.proxyUser", username);
		System.setProperty("https.proxyPassword", String.valueOf(password));
		System.setProperty("java.net.socks.username", username);
		System.setProperty("java.net.socks.password", String.valueOf(password));
		System.setProperty("jdk.http.auth.tunneling.disabledSchemes", "");

		final URL url = new URL("https://api.ipify.org");
		final HttpsURLConnection conn = (HttpsURLConnection) url.openConnection();
		conn.setConnectTimeout(10000);
		conn.setReadTimeout(10000);
		conn.connect();
		final BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		System.out.println(in.readLine());
	}
}
