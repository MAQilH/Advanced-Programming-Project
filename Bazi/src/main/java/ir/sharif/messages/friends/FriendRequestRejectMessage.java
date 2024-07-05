package ir.sharif.messages.friends;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;

public class FriendRequestRejectMessage extends ClientMessage {
	private final String fromUsername, targetUsername;

	public FriendRequestRejectMessage(String fromUsername, String targetUsername) {
		this.type = ClientMessageType.FRIEND_REQUEST_REJECT_MESSAGE;
		this.fromUsername = fromUsername;
		this.targetUsername = targetUsername;
	}

	public String getFromUsername() {
		return fromUsername;
	}

	public String getTargetUsername() {
		return targetUsername;
	}
}
