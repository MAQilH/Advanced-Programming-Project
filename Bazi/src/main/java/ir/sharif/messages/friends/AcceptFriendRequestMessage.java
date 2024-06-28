package ir.sharif.messages.friends;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;

public class AcceptFriendRequestMessage extends ClientMessage {
	private final String fromUsername;
	private final String targetUsername;

	public AcceptFriendRequestMessage(String fromUsername, String targetUsername) {
		this.fromUsername = fromUsername;
		this.targetUsername = targetUsername;
		this.type = ClientMessageType.ACCEPT_FRIEND_REQUEST_MESSAGE;
	}

	public String getFromUsername() {
		return fromUsername;
	}

	public String getTargetUsername() {
		return targetUsername;
	}
}
