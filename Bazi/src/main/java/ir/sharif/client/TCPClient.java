package ir.sharif.client;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import ir.sharif.messages.ChatAllMessage;
import ir.sharif.messages.ChatSendMessage;
import ir.sharif.messages.ServerMessage;
import ir.sharif.model.CommandResult;
import ir.sharif.model.Message;
import ir.sharif.server.ChatService;
import ir.sharif.utils.ConstantsLoader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.ArrayList;


public class TCPClient {
	private Socket socket;
	private DataInputStream recieveBuffer;
	private DataOutputStream sendBuffer;
	private String serverIP;
	private int serverPort;

	private Gson gsonAgent;

	private String username, password;
	private String bio;
	private String token;

	private ServerMessage lastServerMessage;

	public TCPClient() {
		GsonBuilder builder = new GsonBuilder();
		this.gsonAgent = builder.create();
		this.serverIP = ConstantsLoader.getInstance().getProperty("server.ip");
		this.serverPort = Integer.parseInt(ConstantsLoader.getInstance().getProperty("server.port"));
	}

	private boolean establishConnection() {
		try {
			socket = new Socket(serverIP, serverPort);
			sendBuffer = new DataOutputStream(
				socket.getOutputStream()
			);
			recieveBuffer = new DataInputStream(
				socket.getInputStream()
			);
			return true;
		} catch (Exception e) {
			System.err.println("Unable to initialize socket!");
			e.printStackTrace();
			return false;
		}
	}

	private boolean endConnection() {
		if(socket == null) return true;
		try {
			socket.close();
			recieveBuffer.close();
			sendBuffer.close();
			return true;
		} catch (IOException e) {
			return false;
		}
	}

	private boolean sendMessage(String message) {
		try {
			sendBuffer.writeUTF(message);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private String recieveResponse() {
		try {
			return recieveBuffer.readUTF();
		} catch (IOException e) {
			return null;
		}
	}

	public ServerMessage getLastServerMessage() {
		return lastServerMessage;
	}

	public boolean sendChatMessage(String message, String senderUsername) {
		ChatSendMessage chatSendMessage = new ChatSendMessage(senderUsername, message);
		establishConnection();
		sendMessage(gsonAgent.toJson(chatSendMessage));
		lastServerMessage = gsonAgent.fromJson(recieveResponse(), ServerMessage.class);
		endConnection();
		return lastServerMessage.wasSuccessfull();
	}

	public ArrayList<Message> getMessages() {
		ChatAllMessage chatAllMessage = new ChatAllMessage();
		try {
			establishConnection();
			sendMessage(gsonAgent.toJson(chatAllMessage));
			lastServerMessage = gsonAgent.fromJson(recieveResponse(), ServerMessage.class);
			ArrayList<Message> result = null;
			if (lastServerMessage.wasSuccessfull()) {
				Type token = new TypeToken<ArrayList<Message>>() {}.getType();
				result =  gsonAgent.fromJson(lastServerMessage.getAdditionalInfo(), token);
			}

			endConnection();
			return result;
		} catch (Exception e) {
			return null;
		}
	}
}