package com.yferhaoui.basic.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;

public final class StreamHelper {

	/*
	 * Console Colors
	 */
	// Reset
	public static final String RESET = "\033[0m"; // Text Reset

	// Regular Colors
	public static final String BLACK = "\033[0;30m"; // BLACK
	public static final String RED = "\033[0;31m"; // RED
	public static final String GREEN = "\033[0;32m"; // GREEN
	public static final String YELLOW = "\033[0;33m"; // YELLOW
	public static final String BLUE = "\033[0;34m"; // BLUE
	public static final String PURPLE = "\033[0;35m"; // PURPLE
	public static final String CYAN = "\033[0;36m"; // CYAN
	public static final String WHITE = "\033[0;37m"; // WHITE

	// Bold
	public static final String BLACK_BOLD = "\033[1;30m"; // BLACK
	public static final String RED_BOLD = "\033[1;31m"; // RED
	public static final String GREEN_BOLD = "\033[1;32m"; // GREEN
	public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
	public static final String BLUE_BOLD = "\033[1;34m"; // BLUE
	public static final String PURPLE_BOLD = "\033[1;35m"; // PURPLE
	public static final String CYAN_BOLD = "\033[1;36m"; // CYAN
	public static final String WHITE_BOLD = "\033[1;37m"; // WHITE

	// Underline
	public static final String BLACK_UNDERLINED = "\033[4;30m"; // BLACK
	public static final String RED_UNDERLINED = "\033[4;31m"; // RED
	public static final String GREEN_UNDERLINED = "\033[4;32m"; // GREEN
	public static final String YELLOW_UNDERLINED = "\033[4;33m"; // YELLOW
	public static final String BLUE_UNDERLINED = "\033[4;34m"; // BLUE
	public static final String PURPLE_UNDERLINED = "\033[4;35m"; // PURPLE
	public static final String CYAN_UNDERLINED = "\033[4;36m"; // CYAN
	public static final String WHITE_UNDERLINED = "\033[4;37m"; // WHITE

	// Background
	public static final String BLACK_BACKGROUND = "\033[40m"; // BLACK
	public static final String RED_BACKGROUND = "\033[41m"; // RED
	public static final String GREEN_BACKGROUND = "\033[42m"; // GREEN
	public static final String YELLOW_BACKGROUND = "\033[43m"; // YELLOW
	public static final String BLUE_BACKGROUND = "\033[44m"; // BLUE
	public static final String PURPLE_BACKGROUND = "\033[45m"; // PURPLE
	public static final String CYAN_BACKGROUND = "\033[46m"; // CYAN
	public static final String WHITE_BACKGROUND = "\033[47m"; // WHITE

	// High Intensity
	public static final String BLACK_BRIGHT = "\033[0;90m"; // BLACK
	public static final String RED_BRIGHT = "\033[0;91m"; // RED
	public static final String GREEN_BRIGHT = "\033[0;92m"; // GREEN
	public static final String YELLOW_BRIGHT = "\033[0;93m"; // YELLOW
	public static final String BLUE_BRIGHT = "\033[0;94m"; // BLUE
	public static final String PURPLE_BRIGHT = "\033[0;95m"; // PURPLE
	public static final String CYAN_BRIGHT = "\033[0;96m"; // CYAN
	public static final String WHITE_BRIGHT = "\033[0;97m"; // WHITE

	// Bold High Intensity
	public static final String BLACK_BOLD_BRIGHT = "\033[1;90m"; // BLACK
	public static final String RED_BOLD_BRIGHT = "\033[1;91m"; // RED
	public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
	public static final String YELLOW_BOLD_BRIGHT = "\033[1;93m";// YELLOW
	public static final String BLUE_BOLD_BRIGHT = "\033[1;94m"; // BLUE
	public static final String PURPLE_BOLD_BRIGHT = "\033[1;95m";// PURPLE
	public static final String CYAN_BOLD_BRIGHT = "\033[1;96m"; // CYAN
	public static final String WHITE_BOLD_BRIGHT = "\033[1;97m"; // WHITE

	// High Intensity backgrounds
	public static final String BLACK_BACKGROUND_BRIGHT = "\033[0;100m";// BLACK
	public static final String RED_BACKGROUND_BRIGHT = "\033[0;101m";// RED
	public static final String GREEN_BACKGROUND_BRIGHT = "\033[0;102m";// GREEN
	public static final String YELLOW_BACKGROUND_BRIGHT = "\033[0;103m";// YELLOW
	public static final String BLUE_BACKGROUND_BRIGHT = "\033[0;104m";// BLUE
	public static final String PURPLE_BACKGROUND_BRIGHT = "\033[0;105m"; // PURPLE
	public static final String CYAN_BACKGROUND_BRIGHT = "\033[0;106m"; // CYAN
	public static final String WHITE_BACKGROUND_BRIGHT = "\033[0;107m"; // WHITE

	/* Output Methods */
	public static String truncate(Object obj, int maxLength) {
		return String.valueOf(obj).substring(0, Math.min(String.valueOf(obj).length(), maxLength));
	}

	public static String fillLeftSpace(Object obj, int maxLength) {
		
		if (maxLength - String.valueOf(obj).length() > 0) {
			
			String blank = new String(new char[maxLength - String.valueOf(obj).length()]).replace("\0", " ");
			StringBuilder sb = new StringBuilder(blank)//
					.append(obj);
			return sb.toString();
		}
		return String.valueOf(obj);
	}

	public static String fillRightSpace(Object obj, int maxLength) {
		
		if (maxLength - String.valueOf(obj).length() > 0) {
			
			String blank = new String(new char[maxLength - String.valueOf(obj).length()]).replace("\0", " ");
			StringBuilder sb = new StringBuilder(String.valueOf(obj))//
					.append(blank);
			return sb.toString();
		}
		return String.valueOf(obj);
	}
	
	public static String fillLeftSpaceTruncate(Object obj, int maxLength) {
		
		if (maxLength - String.valueOf(obj).length() > 0) {
			
			String blank = new String(new char[maxLength - String.valueOf(obj).length()]).replace("\0", " ");
			StringBuilder sb = new StringBuilder(blank)//
					.append(obj);
			return sb.toString();
		}
		return truncate(obj, maxLength);
	}

	public static String fillRightSpaceTruncate(Object obj, int maxLength) {
		
		if (maxLength - String.valueOf(obj).length() > 0) {
			
			String blank = new String(new char[maxLength - String.valueOf(obj).length()]).replace("\0", " ");
			StringBuilder sb = new StringBuilder(String.valueOf(obj))//
					.append(blank);
			return sb.toString();
		}
		return truncate(obj, maxLength);
	}

	private static StringBuilder getWithTime(int ind) {
		StringBuilder sb = new StringBuilder(TimeHelper.getInstantString(Instant.now()))//
				.append(StringUtils.repeat(" ", 3 * (ind + 1)));
		return sb;
	}

	public static void printlnWithTime(String text, int ind) {
		System.out.println(StreamHelper.getWithTime(ind).append(text));
	}
	
	public static void printlnErrWithTime(String text, int ind) {
		System.out.println(StreamHelper.getWithTime(ind)//
				.append(StreamHelper.RED)//
				.append(text)//
				.append(StreamHelper.RESET));
	}

	public static void println(String text, int ind) {
		final StringBuilder sb = new StringBuilder(StringUtils.repeat(" ", 3 * ind))//
				.append(text);
		System.out.println(sb);
	}

	/* Input Methods */
	public static String readLine(String text, int ind) {
		String msg = new StringBuilder(StringUtils.repeat(" ", 3 * ind))//
				.append(text)//
				.append(": ")//
				.toString();
		return System.console().readLine(msg);
	}

	public static String readLineWithTime(String text, int ind) {
		String msg = StreamHelper.getWithTime(ind)//
				.append(text)//
				.append(": ")//
				.toString();
		return System.console().readLine(msg);
	}

	public static String readNotEmptyLine(String text, int ind) {
		String res = "";
		while (res.isBlank()) {
			res = StreamHelper.readLine(text, ind);
		}
		return res;
	}

	public static String readNotEmptyLineWithTime(String text, int ind) {
		String res = "";
		while (res.isBlank()) {
			res = StreamHelper.readLineWithTime(text, ind);
		}
		return res;
	}

	public static String readPassword(String text, int ind) {
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
