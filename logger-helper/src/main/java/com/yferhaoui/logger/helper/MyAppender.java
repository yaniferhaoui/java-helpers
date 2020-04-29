package com.yferhaoui.logger.helper;

import org.apache.logging.log4j.Level;

public final class MyAppender {

	public final String loggerCanonicalName; //
	public final String path;
	public final boolean append;
	public final int min;
	public final int max;
	public final Level level;

	private MyAppender(final String loggerCanonicalName, final String path, final boolean append, final int min, final int max,
			final Level level) {
		this.loggerCanonicalName = loggerCanonicalName;
		this.path = path;
		this.append = append;
		this.min = min;
		this.max = max;
		this.level = level;
	}

	public final static class MyAppenderBuilder {

		private final String loggerCanonicalName;
		private final String path;

		private boolean append = false;
		private int min = 0;
		private int max = 10;
		private Level level = Level.INFO;

		public MyAppenderBuilder(final String loggerCanonicalName, final String path) {
			this.loggerCanonicalName = loggerCanonicalName;
			this.path = path;
		}

		public MyAppenderBuilder(final Class<?> theClass, final String path) {
			this.loggerCanonicalName = theClass.getCanonicalName();
			this.path = path;
		}

		public final MyAppenderBuilder append(final boolean append) {
			this.append = append;
			return this;
		}

		public final MyAppenderBuilder min(final int min) {
			this.min = min;
			return this;
		}

		public final MyAppenderBuilder max(final int max) {
			this.max = max;
			return this;
		}

		public final MyAppenderBuilder level(final Level level) {
			this.level = level;
			return this;
		}

		public final MyAppender build() {
			return new MyAppender(this.loggerCanonicalName, this.path, this.append, this.min, this.max, this.level);
		}
	}
}
