package ir.sharif.messages.friends;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;

public class FriendRequestCreateMessage extends ClientMessage {
	private final String targetUsername;
	private final String fromUsername;

	public FriendRequestCreateMessage(String fromUsername, String targetUsername) {
		this.targetUsername = targetUsername;
		this.fromUsername = fromUsername;
		this.type = ClientMessageType.FRIEND_REQUEST_CREATE_MESSAGE;
	}

	public String getTargetUsername() {
		return targetUsername;
	}

	public String getFromUsername() {
		return fromUsername;
	}
}
