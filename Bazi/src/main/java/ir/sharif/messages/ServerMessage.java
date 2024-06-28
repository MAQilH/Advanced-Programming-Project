package ir.sharif.messages;

public class ServerMessage {
	private final boolean success;
	private final String additionalInfo;

	public ServerMessage (boolean success, String info) {
		this.success = success;
		this.additionalInfo = info;
	}

	public boolean wasSuccessfull () {
		return this.success;
	}

	public String getAdditionalInfo () {
		return this.additionalInfo;
	}
}