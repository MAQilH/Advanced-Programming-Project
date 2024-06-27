package ir.sharif.server;

import ir.sharif.model.Message;

import java.util.ArrayList;

public class ChatService {
	private static ChatService instance = null;
	private ArrayList<Message> messages = new ArrayList<>();

	private ChatService() {
	}

	public static ChatService getInstance() {
		if (instance == null) {
			instance = new ChatService();
		}
		return instance;
	}

	public ArrayList<Message> getMessages() {
		return messages;
	}

	public void addMessage(Message message) {
		messages.add(message);
	}
}
