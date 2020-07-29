package com.yferhaoui.logger.helper;

import java.util.Map.Entry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.Appender;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.appender.RollingFileAppender;
import org.apache.logging.log4j.core.appender.rolling.CompositeTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.DefaultRolloverStrategy;
import org.apache.logging.log4j.core.appender.rolling.OnStartupTriggeringPolicy;
import org.apache.logging.log4j.core.appender.rolling.SizeBasedTriggeringPolicy;
import org.apache.logging.log4j.core.config.AppenderRef;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.layout.PatternMatch;
import org.apache.logging.log4j.core.layout.ScriptPatternSelector;
import org.apache.logging.log4j.core.script.AbstractScript;
import org.apache.logging.log4j.core.script.Script;

public final class LoggerHelper {

	public final static Logger logger = (Logger) LogManager.getLogger(LoggerHelper.class);

	private static RollingFileAppender[] appenderProg = null;

	public final static void initLoggerHelper(final MyAppender[] appenders) {

		if (LoggerHelper.appenderProg == null) {

			final LoggerContext ctx = (LoggerContext) LogManager.getContext(false);
			ctx.setConfiguration(new DefaultConfiguration());

			final Configuration config = ctx.getConfiguration();
			for (final Entry<String, Appender> app : config.getAppenders().entrySet()) {
				
				config.getLoggerConfig(LogManager.ROOT_LOGGER_NAME).removeAppender(app.getKey());
				for (final Entry<String, LoggerConfig> e : config.getLoggers().entrySet()) {
					e.getValue().removeAppender(app.getKey());
				}
			}

			final PatternMatch[] otherPattern = new PatternMatch[] { PatternMatch.newBuilder().setKey("withThrowable")
					.setPattern("%d{yyyy-MM-dd HH:mm:ss} "//
							+ "[%-12.-12t] %-5p %5.5throwable{short.lineNumber}:"//
							+ "%-13.-13replace{%replace{%throwable{short.fileName}}{\\s}{}}{.java}{}  %maxLen{%m}{180}%n")
					.build() };

			final AbstractScript script = Script.createScript("yaniScript", "bsh",
					"return logEvent.getThrown() != null ? \"withThrowable\" : null;");

			final ScriptPatternSelector sel = ScriptPatternSelector.newBuilder()//
					.setDefaultPattern(
							"%d{yyyy-MM-dd HH:mm:ss} [%-12.-12t] %-5p %5.5L:%-13.-13C{1}  %maxLen{%m}{180}%n")//
					.setProperties(otherPattern)//
					.setScript(script)//
					.setConfiguration(config)//
					.build();

			final PatternLayout layout = PatternLayout.newBuilder()//
					.withPatternSelector(sel)//
					.build();

			LoggerHelper.appenderProg = new RollingFileAppender[appenders.length];
			for (int i = 0; i < appenders.length; i++) {

				final MyAppender appender = appenders[i];
				LoggerHelper.appenderProg[i] = RollingFileAppender.newBuilder()//
						.setConfiguration(config)//
						.setName("Appender-" + appender.loggerCanonicalName)//
						.setLayout(layout)//
						.withFileName(appender.path)//
						.withFilePattern(appender.path + "%i")//
						.withAppend(appender.append)//
						.withImmediateFlush(true)//
						.withPolicy(CompositeTriggeringPolicy.createPolicy(//
								OnStartupTriggeringPolicy.createPolicy(1L), //
								SizeBasedTriggeringPolicy.createPolicy("20000000")))// 20 Mo
						.withStrategy(DefaultRolloverStrategy.newBuilder()//
								.withMax(String.valueOf(appender.max))//
								.withMin(String.valueOf(appender.min))//
								.withFileIndex("0")//
								.withConfig(config)//
								.build())//
						.build();
				LoggerHelper.appenderProg[i].initialize();
				LoggerHelper.appenderProg[i].start();

				final LoggerConfig loggerConfig = LoggerConfig.createLogger(true, //
						appender.level, //
						appender.loggerCanonicalName, //
						null, //
						new AppenderRef[] {}, //
						null, //
						config, //
						null);
				loggerConfig.addAppender(LoggerHelper.appenderProg[i], appender.level, null);

				config.addLogger(appender.loggerCanonicalName, loggerConfig);
			}

			ctx.updateLoggers();

		} else {
			System.err.println("LoggerHelper already initialized !");
		}
	}

	public final static void rollOver() {
		for (final RollingFileAppender rollFile : LoggerHelper.appenderProg) {
			rollFile.getManager().rollover();
		}
	}

}