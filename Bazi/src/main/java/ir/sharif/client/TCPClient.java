package ir.sharif.client;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import ir.sharif.enums.ResultCode;
import ir.sharif.messages.*;
import ir.sharif.model.CommandResult;
import ir.sharif.model.GameHistory;
import ir.sharif.model.User;
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

	public ServerMessage login(String username, String password){
		LoginMessage loginMessage = new LoginMessage(username, password);
		return sendMessage(loginMessage);
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

}