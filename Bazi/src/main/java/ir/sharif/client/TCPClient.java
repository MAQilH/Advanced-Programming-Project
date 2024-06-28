package ir.sharif.client;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import ir.sharif.enums.ResultCode;
import ir.sharif.messages.Chat.ChatAllMessage;
import ir.sharif.messages.Chat.ChatSendMessage;
import ir.sharif.messages.Friends.AcceptFriendRequestMessage;
import ir.sharif.messages.Friends.FriendRequestCreateMessage;
import ir.sharif.messages.ServerMessage;
import ir.sharif.model.CommandResult;
import ir.sharif.model.Message;
import ir.sharif.model.User;
import ir.sharif.service.UserService;
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

	public CommandResult acceptFriendRequest(String fromUsername) {
		if (UserService.getInstance().getUserByUsername(fromUsername) == null) {
			return new CommandResult(ResultCode.FAILED, "User not found!");
		}

		establishConnection();
		sendMessage(gsonAgent.toJson(new AcceptFriendRequestMessage(fromUsername, UserService.getInstance().getCurrentUser().getUsername())));
		lastServerMessage = gsonAgent.fromJson(recieveResponse(), ServerMessage.class);
		endConnection();
		if (lastServerMessage.wasSuccessfull()) {
			return new CommandResult(ResultCode.ACCEPT, "Friend request accepted successfully!");
		} else {
			return new CommandResult(ResultCode.FAILED, lastServerMessage.getAdditionalInfo());
		}
	}

	public ArrayList<String> getFriends(String username) {
		if (UserService.getInstance().getUserByUsername(username) == null) {
			return null;
		}

		establishConnection();
		sendMessage(gsonAgent.toJson(new ir.sharif.messages.Friends.GetFriendsMessage(username)));
		lastServerMessage = gsonAgent.fromJson(recieveResponse(), ServerMessage.class);
		ArrayList<String> result = null;
		if (lastServerMessage.wasSuccessfull()) {
			Type token = new TypeToken<ArrayList<String>>() {}.getType();
			result =  gsonAgent.fromJson(lastServerMessage.getAdditionalInfo(), token);
		}

		endConnection();
		return result;
	}

	public CommandResult sendFriendRequest() {
		if (UserService.getInstance().getUserByUsername(username) == null) {
			return new CommandResult(ResultCode.FAILED, "User not found!");
		}

		if (UserService.getInstance().getUserByUsername(username).getUsername().equals(UserService.getInstance().getCurrentUser().getUsername())) {
			return new CommandResult(ResultCode.FAILED, "You can't send friend request to yourself!");
		}

		if (getFriends(UserService.getInstance().getCurrentUser().getUsername()).contains(username)) {
			return new CommandResult(ResultCode.FAILED, "You are already friends with this user!");
		}

		establishConnection();
		sendMessage(gsonAgent.toJson(new FriendRequestCreateMessage(UserService.getInstance().getCurrentUser().getUsername(), username)));
		lastServerMessage = gsonAgent.fromJson(recieveResponse(), ServerMessage.class);
		endConnection();
		if (lastServerMessage.wasSuccessfull()) {
			return new CommandResult(ResultCode.ACCEPT, "Friend request sent successfully!");
		} else {
			return new CommandResult(ResultCode.FAILED, lastServerMessage.getAdditionalInfo());
		}
	}
}