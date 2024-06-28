package ir.sharif.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ir.sharif.enums.ResultCode;
import ir.sharif.messages.*;
import ir.sharif.model.GameHistory;
import ir.sharif.model.User;
import ir.sharif.service.GameHistoryService;
import ir.sharif.utils.ConstantsLoader;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class TCPServerWorker extends Thread {
	private static ServerSocket server;
	private static Gson gsonAgent;


	private static int WORKERS;

	private static ArrayList<Socket> connections;

	private DataOutputStream sendBuffer;
	private DataInputStream recieveBuffer;

	private AuthHandler authHandler = new AuthHandler();
	private GameHistoryHandler gameHistoryHandler = new GameHistoryHandler();

	private static boolean setupServer(int portNumber, int workerNum) {
		try {
			server = new ServerSocket(portNumber);
			connections = new ArrayList <Socket>();
			WORKERS = workerNum;
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
	public TCPServerWorker() {
		GsonBuilder builder = new GsonBuilder();
		gsonAgent = builder.create();
	}

	public void listen() throws IOException {
		Socket socket;
		while (true) {
			socket = server.accept();
			synchronized (connections) {
				connections.add(socket);
				connections.notify();
			}
		}
	}

	@Override
	public void run() {
		Socket socket;
		while (true) {
			socket = null;
			synchronized (connections) {
				while (connections.isEmpty()) {
					try {
						connections.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				socket = connections.get(0);
				connections.remove(0);
			}
			if (socket != null) {
				handleConnection(socket);
			}
		}
	}

	private String generateNewToken() {
		Random random = new Random();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 256; i++)
			sb.append((char) (random.nextInt(128)));
		return sb.toString();
	}

	private boolean sendMessage(ResultCode resultCode, String problem) {
		return sendMessage(new ServerMessage(resultCode, problem));
	}

	private boolean sendMessage(ServerMessage serverMessage){
		String failureString = gsonAgent.toJson(serverMessage);
		try {
			sendBuffer.writeUTF(failureString);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean sendFailure(String problem) {
		return sendMessage(ResultCode.FAILED, problem);
	}

	private boolean sendSuccess(String info) {
		return sendMessage(ResultCode.ACCEPT, info);
	}


	private void handleConnection(Socket socket) {
		String clientRequest;

		try {
			recieveBuffer = new DataInputStream(
				new BufferedInputStream(socket.getInputStream())
			);
			sendBuffer = new DataOutputStream(
				new BufferedOutputStream(socket.getOutputStream())
			);

			clientRequest = recieveBuffer.readUTF();
			ClientMessage msg = extractClientMessage(clientRequest);
			splitHandler(msg);

			sendBuffer.close();
			recieveBuffer.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private ClientMessage extractClientMessage(String clientStr) {
		try {
			ClientMessage clientMessage = gsonAgent.fromJson(clientStr, ClientMessage.class);
			switch (clientMessage.getType()) {
				case CHAT_SEND_MESSAGE:
					return gsonAgent.fromJson(clientStr, ChatSendMessage.class);
				case LOGIN_MESSAGE:
					return gsonAgent.fromJson(clientStr, LoginMessage.class);
				case REGISTER_MESSAGE:
					return gsonAgent.fromJson(clientStr, RegisterMessage.class);
				case ADD_GAME_HISTORY_MESSAGE:
					return gsonAgent.fromJson(clientStr, AddGameHistoryMessage.class);
				case GET_GAME_HISTORIES_MESSAGE:
					return gsonAgent.fromJson(clientStr, GetGameHistoriesMessage.class);
				default:
					return null;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	void splitHandler(ClientMessage msg){
		if (msg instanceof ChatSendMessage) {
			ChatSendMessage chatSendMessage = (ChatSendMessage) msg;
			System.out.println("Message from " + chatSendMessage.getSenderUsername() + ": " + chatSendMessage.getMessage());
			sendSuccess("Message received");
		} else if(msg instanceof LoginMessage){
			LoginMessage loginMessage = (LoginMessage) msg;
			sendMessage(authHandler.login(loginMessage));
		} else if(msg instanceof RegisterMessage) {
			RegisterMessage registerMessage = (RegisterMessage) msg;
			sendMessage(authHandler.register(registerMessage));
		} else if(msg instanceof AddGameHistoryMessage) {
			AddGameHistoryMessage addGameHistoryMessage = (AddGameHistoryMessage) msg;
			sendMessage(gameHistoryHandler.addGameHistory(addGameHistoryMessage));
		} else if(msg instanceof GetGameHistoriesMessage){
			GetGameHistoriesMessage getGameHistoriesMessage = (GetGameHistoriesMessage) msg;
			sendMessage(gameHistoryHandler.getGameHistories(getGameHistoriesMessage));
		}
		else {
			sendFailure("Invalid message type");
		}
	}

	public static void main(String[] args) {
		try {
			TCPServerWorker.setupServer(Integer.parseInt(ConstantsLoader.getInstance().getProperty("server.port")), 10);
			for (int i = 0; i < WORKERS; i++) {
				new TCPServerWorker().start();
			}
			new TCPServerWorker().listen();
		} catch (Exception e) {
			System.out.println("Server encountered a problem!");
			e.printStackTrace();
		}
	}
}
