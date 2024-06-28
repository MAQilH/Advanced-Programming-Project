package ir.sharif.messages.friends;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;

public class GetFriendsMessage extends ClientMessage {
	private final String username;

	public GetFriendsMessage(String username) {
		this.type = ClientMessageType.GET_FRIENDS_MESSAGE;
		this.username = username;
	}

	public String getUsername() {
		return username;
	}


}
