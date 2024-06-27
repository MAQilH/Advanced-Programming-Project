package ir.sharif.messages;

import java.util.Date;

public class ChatSendMessage extends ClientMessage {
	private String senderUsername;
	private Date date;
	private String message;

	public ChatSendMessage(String message, String senderUsername) {
		this.message = message;
		this.senderUsername = senderUsername;
		this.date = new Date();
		date.setTime(System.currentTimeMillis());
	}

	public String getSenderUsername() {
		return senderUsername;
	}

	public Date getDate() {
		return date;
	}

	public String getMessage() {
		return message;
	}
}
