package com.yferhaoui.mail.helper;

import java.text.MessageFormat;

import org.simplejavamail.MailException;
import org.simplejavamail.api.email.EmailPopulatingBuilder;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.internal.MailerRegularBuilderImpl;

import com.yferhaoui.basic.helper.TimeHelper;
import com.yferhaoui.logger.helper.LoggerHelper;
import com.yferhaoui.proxy_helper.Proxy;
import com.yferhaoui.proxy_helper.ProxyAccount;

public class EmailSender {

	public enum SMTPServer {

		GMAIL("smtp.gmail.com", 587, true, TransportStrategy.SMTP_TLS), //
		OUTLOOK("smtp-mail.outlook.com", 587, true, TransportStrategy.SMTP_TLS), //
		LAPOSTE("smtp.laposte.net", 587, true, TransportStrategy.SMTP_TLS), //
		GMX("mail.gmx.com", 587, true, TransportStrategy.SMTP_TLS), //
		MAILO("mail.mailo.com", 587, true, TransportStrategy.SMTP_TLS), //
		MAIL_COM("smtp.mail.com", 587, true, TransportStrategy.SMTP_TLS);

		private final String host;
		private final int port;
		private final boolean auth;
		private final TransportStrategy transportStrategy;

		private SMTPServer(final String host, final int port, final boolean auth,
				final TransportStrategy transportStrategy) {
			this.host = host;
			this.port = port;
			this.auth = auth;
			this.transportStrategy = transportStrategy;
		}

		public final boolean senderEmailShowedMustBeTrust() {
			return this.equals(OUTLOOK) || this.equals(LAPOSTE) || this.equals(GMX) || this.equals(MAIL_COM);
		}
	}

	// Sender
	private final String emailSender;
	private final String passwordSender;
	private final String nameSenderShowed;
	private final String emailSenderShowed;

	// Recipient
	private final String recipientEmail;

	// SMTP Server
	private final SMTPServer smtpServer;

	// Proxy
	private final Proxy proxy;
	private final ProxyAccount proxyAccount;

	public EmailSender(final String emailSender, //
			final String passwordSender, //
			final String nameSenderShowed, //
			final String emailSenderShowed, //
			final String recipientEmail, //
			final SMTPServer smtpServer, //
			final Proxy proxy, //
			final ProxyAccount proxyAccount) {

		this.emailSender = emailSender;
		this.passwordSender = passwordSender;
		this.nameSenderShowed = nameSenderShowed;
		this.emailSenderShowed = emailSenderShowed;
		this.recipientEmail = recipientEmail;
		this.smtpServer = smtpServer;
		this.proxy = proxy;
		this.proxyAccount = proxyAccount;
	}

	public EmailSender(final String emailSender, //
			final String passwordSender, //
			final String nameSenderShowed, //
			final String emailSenderShowed, //
			final String recipientEmail, //
			final SMTPServer smtpServer, final Proxy proxy) {
		this(emailSender, passwordSender, nameSenderShowed, emailSenderShowed, recipientEmail, smtpServer, proxy, null);
	}

	public EmailSender(final String emailSender, //
			final String passwordSender, //
			final String nameSenderShowed, //
			final String emailSenderShowed, //
			final String recipientEmail, //
			final SMTPServer smtpServer) {
		this(emailSender, passwordSender, nameSenderShowed, emailSenderShowed, recipientEmail, smtpServer, null, null);
	}

	public final void sendMail(final String subject, final String body, final Image... images) throws MailException {

		final String res = this.proxy == null ? "without proxy" : "with proxy " + this.proxy;
		final String text1 = "Sending E-mail from {0} to {1} with server {2} {3} ...";
		final String msg1 = MessageFormat.format(text1, this.emailSender, this.recipientEmail, this.smtpServer, res);
		LoggerHelper.logger.info(msg1);

		// Build the E-mail
		final EmailPopulatingBuilder emailBuilder = EmailBuilder.startingBlank()//
				.to(this.recipientEmail)//
				.withSubject(subject)//
				.withHTMLText(body);

		if (this.smtpServer.senderEmailShowedMustBeTrust()) { // Impossible to about Email Sender
			emailBuilder.from(this.nameSenderShowed, this.emailSender);

		} else {
			emailBuilder.from(this.nameSenderShowed, this.emailSenderShowed);
		}

		for (final Image image : images) {
			emailBuilder.withEmbeddedImage(image.getName(), image.getImg(), image.getMimeType().toString());
		}

		// Build the Mailer
		final MailerRegularBuilderImpl mailerBuilder = MailerBuilder//
				.withSMTPServer(this.smtpServer.host, this.smtpServer.port)//
				.withTransportStrategy(this.smtpServer.transportStrategy)//
				.withSessionTimeout((int) TimeHelper.MS_PER_MINUTE * 2);

		if (this.smtpServer.auth) {
			mailerBuilder.withSMTPServerUsername(this.emailSender);
			mailerBuilder.withSMTPServerPassword(this.passwordSender);
		}

		if (this.proxy != null) {

			if (proxyAccount != null) { // With Authentication
				mailerBuilder.withProxy(this.proxy.getDomain(), this.proxy.getPort(), //
						this.proxyAccount.getUsername(), String.valueOf(this.proxyAccount.getPassword()));

			} else { // Without Authentication
				mailerBuilder.withProxy(this.proxy.getDomain(), this.proxy.getPort());
			}
		}

		// Here send the email !
		mailerBuilder.buildMailer().sendMail(emailBuilder.buildEmail());

		final String text2 = "E-mail from {0} to {1} with server {2} {3} sent !";
		final String msg2 = MessageFormat.format(text2, this.emailSender, this.recipientEmail, this.smtpServer, res);
		LoggerHelper.logger.info(msg2);
	}

}