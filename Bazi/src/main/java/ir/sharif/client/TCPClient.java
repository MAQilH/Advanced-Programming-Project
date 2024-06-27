package ir.sharif.client;

import com.google.gson.*;
import ir.sharif.messages.ChatSendMessage;
import ir.sharif.messages.ServerMessage;
import ir.sharif.model.CommandResult;
import ir.sharif.utils.ConstantsLoader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


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

	public TCPClient(String serverIP, int serverPort) {
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

	public boolean sendMessage(String message, String senderUsername) {
		ChatSendMessage chatSendMessage = new ChatSendMessage(message, senderUsername);
		establishConnection();
		sendMessage(gsonAgent.toJson(chatSendMessage));
		lastServerMessage = gsonAgent.fromJson(recieveResponse(), ServerMessage.class);
		endConnection();
		return lastServerMessage.wasSuccessfull();
	}
}