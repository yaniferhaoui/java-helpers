package com.yferhaoui.logger.helper;

import java.util.Calendar;
import java.util.GregorianCalendar;

import com.yferhaoui.basic.helper.TimeHelper;

public final class RollingFileManager extends Thread {

	public final static void launch() throws IllegalAccessException {
		if (RollingFileManager.rollingFileManager == null) {
			RollingFileManager.rollingFileManager = new RollingFileManager();
			RollingFileManager.rollingFileManager.start();
		} else {
			throw new IllegalAccessException(RollingFileManager.class.getSimpleName() + " already launched !");
		}
	}

	private static RollingFileManager rollingFileManager = null;

	// --------------- //

	private long nextTimeToRoll;

	private RollingFileManager() {
		final Calendar cal = Calendar.getInstance();

		final int currentYear = cal.get(Calendar.YEAR);
		final int currentMonth = cal.get(Calendar.MONTH);
		final int currentDay = cal.get(Calendar.DAY_OF_MONTH);

		final GregorianCalendar now = new GregorianCalendar(currentYear, currentMonth, currentDay, 2, 0, 0);

		this.nextTimeToRoll = now.getTime().getTime();
	}

	@Override
	public final void run() {
		while (true) {
			if (this.nextTimeToRoll - System.currentTimeMillis() < 0) {
				LoggerHelper.rollOver();
				this.incrementDay();
			} else {
				TimeHelper.sleepFromTimestampUninterruptibly(this.nextTimeToRoll);
			}
		}
	}

	private final void incrementDay() {
		this.nextTimeToRoll += TimeHelper.MS_PER_DAY;
		LoggerHelper.logger.info("Next time to rollOver: " + TimeHelper.getDateString(this.nextTimeToRoll));
	}
}