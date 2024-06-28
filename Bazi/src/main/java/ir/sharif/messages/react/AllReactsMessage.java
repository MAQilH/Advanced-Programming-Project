package ir.sharif.messages.react;

import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ClientMessageType;

import java.util.ArrayList;

public class AllReactsMessage extends ClientMessage {
	private int bufferSize = 0;
	public AllReactsMessage(int bufferSize) {
		this.bufferSize = bufferSize;
		this.type = ClientMessageType.ALL_REACTS_MESSAGE;
	}

	public int getBufferSize() {
		return bufferSize;
	}
}
