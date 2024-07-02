package ir.sharif.model;

public class React {
	private final String message;
	private final String sender;
	private final Pair<Double, Double> position;

	public React(String sender, String message, Pair<Double, Double> position) {
		this.sender = sender;
		this.message = message;
		this.position = position;
	}

	public String getMessage() {
		return message;
	}

	public String getSender() {
		return sender;
	}

	public Pair<Double, Double> getPosition() {
		return position;
	}
}
