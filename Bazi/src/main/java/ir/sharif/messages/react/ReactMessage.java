package ir.sharif.messages.react;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;
import ir.sharif.model.React;

public class ReactMessage extends ClientMessage {
	private final React react;
	public ReactMessage(String sender, String message) {
		this.react = new React(sender, message);
		this.type = ClientMessageType.REACT_MESSAGE;
	}

	public React getReact() {
		return react;
	}
}
