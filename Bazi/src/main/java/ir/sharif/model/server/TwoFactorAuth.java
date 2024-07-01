package ir.sharif.model.server;

import ir.sharif.utils.Random;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.util.Properties;

public class TwoFactorAuth {
	private static TwoFactorAuth instance = null;

	private TwoFactorAuth() {}

	public static TwoFactorAuth getInstance() {
		if (instance == null) instance = new TwoFactorAuth();
		return instance;
	}

	public String sendAuthCode(String emailAddress) {
		Properties userPass = new Properties();
		String authCode = Random.randomAuthCode();
		try {
			userPass.load(getClass().getResourceAsStream("/gmail.properties"));
			Properties prop = new Properties();
			prop.put("mail.smtp.host", "smtp.gmail.com");
			prop.put("mail.smtp.port", "587");
			prop.put("mail.smtp.auth", "true");
			prop.put("mail.smtp.starttls.enable", true);
			prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");



			Session session = Session.getInstance(prop,
				new jakarta.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(userPass.getProperty("gmail.username"),
							userPass.getProperty("gmail.apppassword"));
					}
				});

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(userPass.getProperty("gmail.username")));
			message.setRecipient(
				Message.RecipientType.TO,
				InternetAddress.parse(emailAddress)[0]
			);

			message.setSubject("Authentication Code for Sharif Gwent");
			message.setText("Hi, here is your authentication code for gwent game: " + authCode);
			Transport.send(message);
			System.err.println("done!");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}


		return authCode;
	}
}
