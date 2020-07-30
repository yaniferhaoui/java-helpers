package com.yferhaoui.basic.helper;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public final class TimeHelper {

	public final static ZoneId ZONE_ID = ZoneId.of("Europe/Paris");

	public final static long MS_PER_YEAR = 31556952000L;
	public final static long MS_PER_MONTH = 2629746000L;
	public final static long MS_PER_WEEK = 604800000;
	public final static long MS_PER_DAY = 86400000;
	public final static long MS_PER_HOUR = 3600000;
	public final static long MS_PER_MINUTE = 60000;

	private final static Lock LOCK = new ReentrantLock(true);

	public final static long getValidKey() {
		LOCK.lock();
		try {
			return System.currentTimeMillis();
		} finally {
			sleepUninterruptibly(2);
			LOCK.unlock();
		}
	}

	public final static String getValidHexaKey() {
		return Long.toHexString(getValidKey());
	}

	public final static String getDateString(final Long theDate) {
		if (theDate != null) {
			return Instant.ofEpochMilli(theDate).atZone(ZONE_ID).toLocalDateTime()
					.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}
		return null;
	}
	
	public final static String getJustReadableDate(final Long theDate) {
		if (theDate != null) {
			return Instant.ofEpochMilli(theDate).atZone(ZONE_ID).toLocalDateTime()
					.format(DateTimeFormatter.ofPattern("dd MMM yyyy"));
		}
		return null;
	}
	
	public final static String getReadableDate(final Long theDate) {
		if (theDate != null) {
			return Instant.ofEpochMilli(theDate).atZone(ZONE_ID).toLocalDateTime()
					.format(DateTimeFormatter.ofPattern("dd MMM yyyy HH:mm:ss"));
		}
		return null;
	}
	
	public final static String getCalendarString(final Calendar theDate) {
		if (theDate != null) {
			return TimeHelper.getDateString(theDate.getTimeInMillis());
		}
		return null;
	}

	public final static String getInstantString(final Instant theDate) {
		if (theDate != null) {
			return theDate.atZone(ZONE_ID).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
		}
		return null;
	}

	public final static String getJustDateString(final Long theDate) {
		if (theDate != null) {
			return Instant.ofEpochMilli(theDate).atZone(ZONE_ID).toLocalDateTime()
					.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		return null;
	}
	
	public final static String getJustCalendarString(final Calendar theDate) {
		if (theDate != null) {
			return TimeHelper.getJustDateString(theDate.getTimeInMillis());
		}
		return null;
	}

	public final static String getJustInstantString(final Instant theDate) {
		if (theDate != null) {
			return theDate.atZone(ZONE_ID).toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
		}
		return null;
	}

	public final static String getTime(final Long theDate) {
		if (theDate != null) {
			return Instant.ofEpochMilli(theDate).atZone(ZONE_ID).toLocalDateTime()
					.format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		}
		return null;
	}

	public final static String getTime(final Instant theDate) {
		if (theDate != null) {
			return theDate.atZone(ZONE_ID).toLocalDateTime().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
		}
		return null;
	}

	public final static void sleep(final long time) throws InterruptedException {
		if (time > 0) {
			Thread.sleep(time);
		}
	}

	public final static void sleepUninterruptibly(long time) {
		while (time > 0) {
			final long now = System.currentTimeMillis();
			try {
				Thread.sleep(time);
			} catch (final InterruptedException e) {
				// Do Nothing
			} finally {
				time -= System.currentTimeMillis() - now;
			}
		}
	}

	public final static void sleepFromTimestamp(final long dateToSleep) throws InterruptedException {
		sleep(dateToSleep - System.currentTimeMillis());
	}

	public final static void sleepFromTimestamp(final Instant dateToSleep) throws InterruptedException {
		sleep(dateToSleep.toEpochMilli() - System.currentTimeMillis());
	}

	public final static void sleepFromTimestampUninterruptibly(final long dateToSleep) {
		sleepUninterruptibly(dateToSleep - System.currentTimeMillis());
	}

	public final static void sleepFromTimestampUninterruptibly(final Instant dateToSleep) {
		sleepUninterruptibly(dateToSleep.toEpochMilli() - System.currentTimeMillis());
	}

	public final static void sleepFromTimestamp(final long dateToSleep, final int indentationLevel)
			throws InterruptedException {

		StreamHelper.printlnWithTime("Sleep until " + getDateString(dateToSleep), indentationLevel);
		sleep(dateToSleep - System.currentTimeMillis());
	}

	public final static void sleepFromTimestamp(final Instant dateToSleep, final int indentationLevel)
			throws InterruptedException {

		StreamHelper.printlnWithTime("Sleep until " + getInstantString(dateToSleep), indentationLevel);
		sleep(dateToSleep.toEpochMilli() - System.currentTimeMillis());
	}

	public final static void sleepFromTimestampUninterruptibly(final long dateToSleep, final int indentationLevel) {

		StreamHelper.printlnWithTime("Sleep uninterrupltibly until " + getDateString(dateToSleep), indentationLevel);
		sleepUninterruptibly(dateToSleep - System.currentTimeMillis());
	}

	public final static void sleepFromTimestampUninterruptibly(final Instant dateToSleep, final int indentationLevel) {

		StreamHelper.printlnWithTime("Sleep uninterrupltibly until " + getInstantString(dateToSleep), indentationLevel);
		sleepUninterruptibly(dateToSleep.toEpochMilli() - System.currentTimeMillis());
	}

}
