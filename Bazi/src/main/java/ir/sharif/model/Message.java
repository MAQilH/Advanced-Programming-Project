package ir.sharif.model;

import java.util.Date;

public class Message {
	private String senderUsername;
	private String message;
	private Date date;

	public Message(String senderUsername, String message) {
		this.senderUsername = senderUsername;
		this.message = message;
		this.date = new Date();
		date.setTime(System.currentTimeMillis());
	}

	public String getSenderUsername() {
		return senderUsername;
	}

	public String getMessage() {
		return message;
	}
}
