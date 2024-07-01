package ir.sharif.messages.chat;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;

public class ReactToMessage extends ClientMessage {
	private int messageId;
	public ReactToMessage(int messageId) {
		this.messageId = messageId;
		this.type = ClientMessageType.REACT_TO_MESSAGE;
	}

	public int getMessageId() {
		return messageId;
	}
}
