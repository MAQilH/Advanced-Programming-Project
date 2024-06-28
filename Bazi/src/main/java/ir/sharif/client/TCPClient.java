package ir.sharif.client;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import ir.sharif.enums.ResultCode;
import ir.sharif.messages.*;
import ir.sharif.messages.chat.ChatAllMessage;
import ir.sharif.messages.chat.ChatSendMessage;
import ir.sharif.messages.friends.AcceptFriendRequestMessage;
import ir.sharif.messages.friends.FriendRequestCreateMessage;
import ir.sharif.messages.friends.PendingFriendRequests;
import ir.sharif.messages.react.AllReactsMessage;
import ir.sharif.messages.react.ReactMessage;
import ir.sharif.model.*;
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
			e.printStackTrace();
			return false;
		}
	}

    private ServerMessage sendMessage(ClientMessage clientMessage) {
        try {
            establishConnection();
            sendBuffer.writeUTF(gsonAgent.toJson(clientMessage));
            lastServerMessage = receiveResponse();
            endConnection();
            return lastServerMessage;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private ServerMessage receiveResponse() {
        try {
            return gsonAgent.fromJson(recieveBuffer.readUTF(), ServerMessage.class);
        } catch (IOException e) {
            e.printStackTrace();
            return new ServerMessage(ResultCode.FAILED, "Unable to receive response");
        }
    }

	public ServerMessage getLastServerMessage() {
		return lastServerMessage;
	}

    public ServerMessage sendChatMessage(String message, String senderUsername) {
        ChatSendMessage chatSendMessage = new ChatSendMessage(message, senderUsername);
        return sendMessage(chatSendMessage);
    }

    public ServerMessage register(User user){
        RegisterMessage registerMessage = new RegisterMessage(user);
        return sendMessage(registerMessage);
    }

    public ServerMessage addGameHistory(GameHistory gameHistory) {
        AddGameHistoryMessage addGameHistoryMessage = new AddGameHistoryMessage(gameHistory);
        return sendMessage(addGameHistoryMessage);
    }

    public ArrayList<GameHistory> getGameHistories() {
        GetGameHistoriesMessage getGameHistoriesMessage = new GetGameHistoriesMessage();
        sendMessage(getGameHistoriesMessage);
        if(lastServerMessage.getStatusCode() == ResultCode.ACCEPT) {
            Type type = new TypeToken<ArrayList<GameHistory>>(){}.getType();
            return gsonAgent.fromJson(lastServerMessage.getAdditionalInfo(), type);
        }
        return new ArrayList<>();
    }

    public ArrayList<Message> getMessages() {
        ChatAllMessage chatAllMessage = new ChatAllMessage();
        try {
            sendMessage(chatAllMessage);
            ArrayList<Message> result = null;
            if (lastServerMessage.wasSuccessfull()) {
                Type token = new TypeToken<ArrayList<Message>>() {}.getType();
                result =  gsonAgent.fromJson(lastServerMessage.getAdditionalInfo(), token);
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }

    public CommandResult acceptFriendRequest(String fromUsername) {
        if (UserService.getInstance().getUserByUsername(fromUsername) == null) {
            return new CommandResult(ResultCode.FAILED, "User not found!");
        }

        sendMessage(new AcceptFriendRequestMessage(fromUsername, UserService.getInstance().getCurrentUser().getUsername()));
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

        sendMessage(new ir.sharif.messages.friends.GetFriendsMessage(username));
        ArrayList<String> result = null;
        if (lastServerMessage.wasSuccessfull()) {
            Type token = new TypeToken<ArrayList<String>>() {}.getType();
            result =  gsonAgent.fromJson(lastServerMessage.getAdditionalInfo(), token);
        }

        return result;
    }

	public ArrayList<String> getPendingFriendRequests(String username) {
		PendingFriendRequests pendingFriendRequests = new PendingFriendRequests(username);
		sendMessage(pendingFriendRequests);

		ArrayList<String> result = null;
		if (lastServerMessage.wasSuccessfull()) {
			Type token = new TypeToken<ArrayList<String>>() {}.getType();
			result =  gsonAgent.fromJson(lastServerMessage.getAdditionalInfo(), token);
		}

		return result;
	}

    public CommandResult sendFriendRequest(String username) {
        if (UserService.getInstance().getUserByUsername(username) == null) {
            return new CommandResult(ResultCode.FAILED, "User not found!");
        }

        if (UserService.getInstance().getUserByUsername(username).getUsername().equals(UserService.getInstance().getCurrentUser().getUsername())) {
            return new CommandResult(ResultCode.FAILED, "You can't send friend request to yourself!");
        }

        if (getFriends(UserService.getInstance().getCurrentUser().getUsername()).contains(username)) {
            return new CommandResult(ResultCode.FAILED, "You are already friends with this user!");
        }

        sendMessage(new FriendRequestCreateMessage(UserService.getInstance().getCurrentUser().getUsername(), username));
        if (lastServerMessage.wasSuccessfull()) {
            return new CommandResult(ResultCode.ACCEPT, "Friend request sent successfully!");
        } else {
            return new CommandResult(ResultCode.FAILED, lastServerMessage.getAdditionalInfo());
        }
    }

	public CommandResult sendReaction(String sender, String message) {
		sendMessage(new ReactMessage(sender, message));
		if (lastServerMessage.wasSuccessfull()) {
			return new CommandResult(ResultCode.ACCEPT, "react sent successfully");
		} else {
			return new CommandResult(ResultCode.FAILED, "react failed");
		}
	}

	public ArrayList<React> getAllReacts(int bufferSize) {
		sendMessage(new AllReactsMessage(bufferSize));
		if (lastServerMessage.wasSuccessfull()) {
			Type type = new TypeToken<ArrayList<React>>(){}.getType();
			return gsonAgent.fromJson(lastServerMessage.getAdditionalInfo(), type);
		}

		return null;
 	}
}