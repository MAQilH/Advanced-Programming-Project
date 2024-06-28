package ir.sharif.model;

public class React {
	private final String message;
	private final String sender;

	public React(String sender, String message) {
		this.sender = sender;
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public String getSender() {
		return sender;
	}
}
