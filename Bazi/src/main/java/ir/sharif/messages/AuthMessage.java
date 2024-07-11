package ir.sharif.messages;

public class AuthMessage extends ClientMessage {
	private String token;
	public AuthMessage(String token) {
		this.token = token;
		this.type = ClientMessageType.AUTH_MESSAGE;
	}

	public String getToken() {
		return token;
	}
}
