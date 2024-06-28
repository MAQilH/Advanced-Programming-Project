package ir.sharif.messages.Chat;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;
import ir.sharif.model.Message;

import java.util.ArrayList;

public class ChatAllMessage extends ClientMessage {
	public ChatAllMessage() {
		this.type = ClientMessageType.CHAT_ALL_MESSAGES;
	}

}
