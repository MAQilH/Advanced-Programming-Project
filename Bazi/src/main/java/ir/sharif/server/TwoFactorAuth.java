package ir.sharif.server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import ir.sharif.utils.Random;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class TwoFactorAuth {
	private static TwoFactorAuth instance = null;

	private TwoFactorAuth() {}

	private ArrayList<String> verifiedTokens = new ArrayList<>();


	public static TwoFactorAuth getInstance() {
		if (instance == null) instance = new TwoFactorAuth();
		return instance;
	}

	public String sendAuthCode(String emailAddress) {
		String authCode = Random.randomAuthCode();
		String body = "Your authentication code is: " + authCode + "\n" + "or use link: " + "127.0.0.1:8480/verify?token=" + authCode;
		sendEmail(body, emailAddress);
		return authCode;
	}


	public void sendEmail(String messageText, String emailAddress) {
		Properties userPass = new Properties();
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
			message.setText(messageText);
			Transport.send(message);
			System.err.println("done!");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	public static void runVerifier() throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(8480), 0);
		server.createContext("/verify", new VerifyHandler());
		server.setExecutor(null);
		server.start();
		System.out.println("Server started on port 8080");
	}

	static class VerifyHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			System.err.println("ruuuuu: " + exchange.getRequestURI().getQuery());
			if ("GET".equals(exchange.getRequestMethod())) {
				Map<String, String> queryParams = parseQueryParams(exchange.getRequestURI().getQuery());
				String token = queryParams.get("token");
				System.out.println("verify :" + token);
				String response;
				if (verifyToken(token)) {
					response = "Email verified successfully!";
				} else {
					response = "Invalid or expired token.";
				}

				exchange.sendResponseHeaders(200, response.length());
				OutputStream os = exchange.getResponseBody();
				os.write(response.getBytes());
				os.close();
			}
		}

		private Map<String, String> parseQueryParams(String query) {
			Map<String, String> queryParams = new HashMap<>();
			String[] pairs = query.split("&");
			for (String pair : pairs) {
				int idx = pair.indexOf("=");
				queryParams.put(pair.substring(0, idx), pair.substring(idx + 1));
			}
			return queryParams;
		}

		private boolean verifyToken(String token) {
			getInstance().verifiedTokens.add(token);
			return true;
		}
	}

	public boolean isVerified(String token) {
		for (String tok : verifiedTokens)
			if (tok.equals(token))
				return true;

		return false;
	}
}
