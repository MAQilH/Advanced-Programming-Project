package ir.sharif.messages.react;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;
import ir.sharif.model.Pair;
import ir.sharif.model.React;

public class ReactMessage extends ClientMessage {
	private final React react;
	public ReactMessage(String sender, String message, Pair<Double, Double> position) {
		this.react = new React(sender, message, position);
		this.type = ClientMessageType.REACT_MESSAGE;
	}

	public React getReact() {
		return react;
	}
}
