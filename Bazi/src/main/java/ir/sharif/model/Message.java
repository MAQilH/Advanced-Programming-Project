package ir.sharif.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Message {
	private String senderUsername;
	private String message;
	private String date;
	private boolean reacted = false;

	public Message(String senderUsername, String message) {
		this.senderUsername = senderUsername;
		this.message = message;
		this.date = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now());
	}

	public String getSenderUsername() {
		return senderUsername;
	}

	public String getMessage() {
		return message;
	}

	public void addReact() {
		reacted = true;
	}

	public boolean getReact() {
		return reacted;
	}

	public String getDate() {
		return date;
	}
}
