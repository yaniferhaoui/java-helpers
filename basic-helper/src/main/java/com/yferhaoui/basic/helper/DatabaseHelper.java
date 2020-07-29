package com.yferhaoui.basic.helper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;

import org.apache.ibatis.jdbc.ScriptRunner;

public final class DatabaseHelper {

	// Execute sql script
	public final static void executeScript(final String scriptName, //
			final Connection connection) throws UnsupportedEncodingException {

		final PrintStream tmp = new PrintStream(new OutputStream() {
			@Override
			public final void write(final int b) throws IOException {
				// do nothing
			}
		});
		final InputStream inputStream = DatabaseHelper.class.getClassLoader().getResourceAsStream(scriptName);
		final Reader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		final ScriptRunner scriptRunner = new ScriptRunner(connection);
		scriptRunner.setLogWriter(new PrintWriter(tmp));
		scriptRunner.runScript(reader);
	}
}
