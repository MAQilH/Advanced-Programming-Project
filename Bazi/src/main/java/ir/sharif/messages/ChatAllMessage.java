package ir.sharif.messages;

import ir.sharif.model.Message;

import java.util.ArrayList;

public class ChatAllMessage extends ClientMessage {
	public ChatAllMessage() {
		this.type = ClientMessageType.CHAT_ALL_MESSAGES;
	}

}
