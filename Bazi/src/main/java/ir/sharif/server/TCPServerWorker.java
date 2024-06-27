package ir.sharif.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ir.sharif.messages.ChatAllMessage;
import ir.sharif.messages.ChatSendMessage;
import ir.sharif.messages.ClientMessage;
import ir.sharif.messages.ServerMessage;
import ir.sharif.model.Message;
import ir.sharif.utils.ConstantsLoader;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class TCPServerWorker extends Thread {
	private static ServerSocket server;
	private static Gson gsonAgent;

	private static final String INTERNAL_ERROR = "internal server error";
	private static final String INVALID_USERNAME = "no user exists with such username";
	private static final String USERNAME_TAKEN = "this username is taken";
	private static final String INVALID_TOKEN = "this token belongs to no user";
	private static final String WRONG_PASSWORD = "wrong password";
	private static final String BUSY_USER = "user is already logged in";

	private static int WORKERS;

	private static ArrayList<Socket> connections;

	private DataOutputStream sendBuffer;
	private DataInputStream recieveBuffer;

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

	private ClientMessage extractClientMessage(String clientStr) {
		System.err.println(clientStr);
		try {
			ClientMessage clientMessage = gsonAgent.fromJson(clientStr, ClientMessage.class);
			switch (clientMessage.getType()) {
				case CHAT_SEND_MESSAGE:
					return gsonAgent.fromJson(clientStr, ChatSendMessage.class);
				case CHAT_ALL_MESSAGES:
					System.err.println("kheilie fuck");
					return gsonAgent.fromJson(clientStr, ChatAllMessage.class);
				default:
					return null;
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}


	private boolean sendMessage(boolean success, String problem) {
		ServerMessage failureMessage = new ServerMessage(success, problem);
		String failureString = gsonAgent.toJson(failureMessage);
		try {
			sendBuffer.writeUTF(failureString);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private boolean sendFailure(String problem) {
		return sendMessage(false, problem);
	}

	private boolean sendSuccess(String info) {
		return sendMessage(true, info);
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

			if (msg instanceof ChatSendMessage) {
				ChatSendMessage chatSendMessage = (ChatSendMessage) msg;
				Message message = new Message(chatSendMessage.getSenderUsername(), chatSendMessage.getMessage());
				ChatService.getInstance().addMessage(message);
				sendSuccess("Message received");
			} else if (msg instanceof ChatAllMessage) {
				sendSuccess(gsonAgent.toJson(ChatService.getInstance().getMessages()));
			} else {
				sendFailure("Invalid message type");
			}

			sendBuffer.close();
			recieveBuffer.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
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
