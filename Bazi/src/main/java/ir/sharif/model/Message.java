package ir.sharif.model;

public class Message {
	private String senderUsername;
	private String message;

	public Message(String senderUsername, String message) {
		this.senderUsername = senderUsername;
		this.message = message;
	}

	public String getSenderUsername() {
		return senderUsername;
	}

	public String getMessage() {
		return message;
	}
}
