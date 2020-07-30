package com.yferhaoui.mail.helper;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import java.util.Properties;

import org.simplejavamail.MailException;

import com.yferhaoui.basic.helper.TimeHelper;
import com.yferhaoui.basic.helper.ToolHelper;

public final class EmailExceptionSender extends EmailSender {

	public final static EmailExceptionSender getInstance(final SMTPServer smtpServer) {

		if (SENDER == null) {

			try {

				// Load Properties
				final Properties properties = new Properties();
				final ClassLoader loader = EmailExceptionSender.class.getClassLoader();
				properties.load(loader.getResourceAsStream("email.properties"));

				// Get Data
				final String senderEmail = properties.getProperty("senderEmail");
				final String senderPassword = properties.getProperty("senderPassword");
				final String senderFakeName = properties.getProperty("senderFakeName");
				final String senderFakeEmail = properties.getProperty("senderFakeEmail");
				final String recipientEmail = properties.getProperty("recipientEmail");

				SENDER = new EmailExceptionSender(senderEmail, //
						senderPassword, //
						senderFakeName, senderFakeEmail, recipientEmail, smtpServer);

			} catch (final IOException e) {
				throw new RuntimeException(e);
			}
		}
		return SENDER;
	}

	private static EmailExceptionSender SENDER;

	// -------------------------------------------------- //

	private final List<Entry<Long, Exception>> mails = new ArrayList<Entry<Long, Exception>>();

	private EmailExceptionSender(final String senderEmail, //
			final String senderPassword, //
			final String senderFakeName, //
			final String senderFakeEmail, //
			final String recipientEmail, //
			final SMTPServer smtpServer) {
		super(senderEmail, senderPassword, senderFakeName, senderFakeEmail, recipientEmail, smtpServer);
	}

	public final void sendError(final Exception e, final float nbHoursToWait, final boolean terminated)
			throws MailException {

		this.purge();
		if (this.getKey(e) == null) {

			final StackTraceElement stack = e.getStackTrace()[0];
			final String subject = e.getClass().getSimpleName();
			final StringBuilder body = new StringBuilder("<html><body><h3>Exception: ")//
					.append(e.getClass().getSimpleName())//
					.append("</h3><ul><li><strong>Thread: </strong>")//
					.append(Thread.currentThread().getName())//
					.append("</li><li><strong>Class: </strong>")//
					.append(stack.getClassName())//
					.append("</li><li><strong>Method: </strong>")//
					.append(stack.getMethodName())//
					.append("</li><li><strong>Line: </strong>")//
					.append(stack.getLineNumber())//
					.append("</li><li><strong>Message: </strong>")//
					.append(e.getMessage())//
					.append("</li><li><strong>Program Terminated: </strong>")//
					.append(terminated)//
					.append("</li></ul>")//
					.append("<h4>")//
					.append("Array of ")//
					.append(StackTraceElement.class.getSimpleName())//
					.append("</h4><ul>");

			for (final StackTraceElement s : e.getStackTrace()) {
				body.append("<li>");
				body.append(s);
				body.append("</li>");
			}

			body.append("</ul></body></html>");

			this.sendMail(subject, body.toString());

			final long time = System.currentTimeMillis() + (long) (TimeHelper.MS_PER_HOUR * nbHoursToWait);
			this.mails.add(new SimpleEntry<Long, Exception>(time, e));

		} else {
			EmailSender.LOGGER.debug("Email about this Exception already sent in the last {} hours !", nbHoursToWait);
		}

	}

	private final void purge() {
		for (final Entry<Long, Exception> e : new ArrayList<Entry<Long, Exception>>(this.mails)) {
			if (e.getKey() < System.currentTimeMillis()) {
				this.mails.remove(e);
			}
		}
	}

	private final Long getKey(final Exception e1) {
		for (final Entry<Long, Exception> entry : this.mails) {

			final Exception e2 = entry.getValue();
			if (ToolHelper.Equals(e1, e2)) {
				return entry.getKey();
			}
		}
		return null;
	}
}
