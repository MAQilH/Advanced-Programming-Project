package ir.sharif.messages.chat;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;

public class ChatAllMessage extends ClientMessage {
	public ChatAllMessage() {
		this.type = ClientMessageType.CHAT_ALL_MESSAGES;
	}

}
