package ir.sharif.messages.friends;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;

public class PendingFriendRequests extends ClientMessage {
	private String username;
	public PendingFriendRequests(String username) {
		this.username = username;
		this.type = ClientMessageType.PENDING_FRIEND_REQUESTS;
	}

	public String getUsername() {
		return username;
	}
}
