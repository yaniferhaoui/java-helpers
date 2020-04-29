package com.yferhaoui.basic.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

public final class StreamHelper {

	/* Output Methods */
	public final static String truncate(final Object obj, final int maxLength) {
		return String.valueOf(obj).substring(0, Math.min(String.valueOf(obj).length(), maxLength));
	}

	public final static String fillLeftSpace(final Object obj, final int maxLength) {
		if (maxLength - String.valueOf(obj).length() > 0) {
			final String blank = new String(new char[maxLength - String.valueOf(obj).length()]).replace("\0", " ");
			final StringBuilder sb = new StringBuilder(blank)//
					.append(obj);
			return sb.toString();
		}
		return String.valueOf(obj);
	}

	public final static String fillRightSpace(final Object obj, final int maxLength) {
		if (maxLength - String.valueOf(obj).length() > 0) {
			final String blank = new String(new char[maxLength - String.valueOf(obj).length()]).replace("\0", " ");
			final StringBuilder sb = new StringBuilder(String.valueOf(obj))//
					.append(blank);
			return sb.toString();
		}
		return String.valueOf(obj);
	}

	private final static StringBuilder getWithTime(final int ind) {
		final StringBuilder sb = new StringBuilder(TimeHelper.getInstantString(Instant.now()))//
				.append(StringUtils.repeat(" ", 3 * (ind + 1)));
		return sb;
	}

	public final static void printlnWithTime(final String text, final int ind) {
		System.out.println(StreamHelper.getWithTime(ind).append(text));
	}

	public final static void println(final String text, final int ind) {
		final StringBuilder sb = new StringBuilder(StringUtils.repeat(" ", 3 * ind))//
				.append(text);
		System.out.println(sb);
	}

	/* Input Methods */
	public final static String readLine(final String text, final int ind) {
		final String msg = new StringBuilder(StringUtils.repeat(" ", 3 * ind))//
				.append(text)//
				.append(": ")//
				.toString();
		return System.console().readLine(msg);
	}

	public final static String readLineWithTime(final String text, final int ind) {
		final String msg = StreamHelper.getWithTime(ind)//
				.append(text)//
				.append(": ")//
				.toString();
		return System.console().readLine(msg);
	}

	public final static String readNotEmptyLine(final String text, final int ind) {
		String res = "";
		while (res.isBlank()) {
			res = StreamHelper.readLine(text, ind);
		}
		return res;
	}

	public final static String readNotEmptyLineWithTime(final String text, final int ind) {
		String res = "";
		while (res.isBlank()) {
			res = StreamHelper.readLineWithTime(text, ind);
		}
		return res;
	}

	public final static String readPassword(final String text, final int ind) {
		final String msg = new StringBuilder(StringUtils.repeat(" ", 3 * ind))//
				.append(text)//
				.append(": ")//
				.toString();
		return new String(System.console().readPassword(msg));
	}

	public final static String readPasswordWithTime(final String message, final int ind) {
		final String msg = StreamHelper.getWithTime(ind)//
				.append(message)//
				.append(": ")//
				.toString();
		return new String(System.console().readPassword(msg));
	}

	public final static String readNotEmptyPassword(final String text, final int ind) {
		String res = "";
		while (res.isBlank()) {
			res = StreamHelper.readPassword(text, ind);
		}
		return res;
	}

	public final static String readNotEmptyPasswordWithTime(final String text, final int ind) {
		String res = "";
		while (res.isBlank()) {
			res = StreamHelper.readPasswordWithTime(text, ind);
		}
		return res;
	}

	// Ask boolean
	public final static boolean askBoolean(final String text) {
		while (true) {
			try {
				final StringBuilder sb = new StringBuilder(text)//
						.append(" Y/n : ");
				final String input = System.console().readLine(sb.toString());
				if (input.equalsIgnoreCase("Y") || input.equalsIgnoreCase("YES")) {
					return true;
				} else if (input.equalsIgnoreCase("N") || input.equalsIgnoreCase("NO")) {
					return false;
				}
			} catch (final NullPointerException e) {
				System.err.println(" Yes ('y', 'Y', 'yes', 'YES') - No ('n', 'N', 'no', 'NO') ");
			}
		}
	}

	public final static boolean askBoolean(final String text, final int ind) {
		return StreamHelper.askBoolean(StringUtils.repeat(" ", 3 * ind) + text);
	}

	public final static boolean askBooleanWithTime(final String text, final int ind) {
		return StreamHelper.askBoolean(StreamHelper.getWithTime(ind).append(text).toString());
	}

	// Ask Number
	public final static int askNumber(final String text, final Object[] objects, final int ind) {
		StringBuilder sb;
		while (true) {
			try {
				sb = new StringBuilder("\n")//
						.append(StringUtils.repeat(" ", 3 * ind))//
						.append(text)//
						.append(": ");
				System.out.println(sb);

				for (int i = 0; i < objects.length; i++) {
					sb = new StringBuilder(StringUtils.repeat(" ", 3 * ind))//
							.append("- ")//
							.append(objects[i])//
							.append(" | ")//
							.append(i);
					System.out.println(sb);
				}

				sb = new StringBuilder(StringUtils.repeat(" ", 3 * ind))//
						.append("Enter the number of your choice: ");
				final int number = Integer.valueOf(System.console().readLine(sb.toString()));
				if (0 <= number && number < objects.length) {
					return number;
				}
			} catch (final NumberFormatException e) {
				sb = new StringBuilder(" Choose a number between 0 and ")//
						.append(objects.length)//
						.append(" ! ");
				System.err.println(sb);
			}
		}
	}

	public final static int askNumber(final String text, final int min, final int max) {
		StringBuilder sb;
		while (true) {
			try {
				sb = new StringBuilder(text)//
						.append(" | Enter a number between ")//
						.append(min)//
						.append(" and ")//
						.append(max)//
						.append(": ");
				final int localVar = Integer.valueOf(System.console().readLine(sb.toString()));
				if (min <= localVar && localVar <= max) {
					return localVar;
				}
			} catch (final NumberFormatException e) {
				sb = new StringBuilder(" Choose a number between ")//
						.append(min)//
						.append(" and ")//
						.append(max)//
						.append(" ! ");
				System.err.println(sb);
			}
		}
	}

	public final static int askNumber(final String text, final int min, final int max, final int ind) {
		return StreamHelper.askNumber(StringUtils.repeat(" ", 3 * ind) + text, min, max);
	}

	public final static int askNumberWithTime(String text, final int min, final int max, final int ind) {
		return StreamHelper.askNumber(StreamHelper.getWithTime(ind).append(text).toString(), min, max);
	}

	public final static int askNumberWithTime(final String text, final Object[] objects, final int ind) {
		return StreamHelper.askNumber(StreamHelper.getWithTime(ind).append(text).toString(), objects, ind);
	}

	// Ask Date
	public final static Instant askInstant(final String text, final SimpleDateFormat formatter) {
		while (true) {

			try {

				final String format = formatter.toPattern();
				final String res = StreamHelper.readNotEmptyLineWithTime(text + " (" + format + ")", 1);
				final SimpleDateFormat df = new SimpleDateFormat(format);
				df.setTimeZone(TimeZone.getTimeZone("UTC"));
				return df.parse(res).toInstant();

			} catch (final ParseException e) {
				// Do nothing
			}
		}
	}
}
