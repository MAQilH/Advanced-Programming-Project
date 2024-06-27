package ir.sharif.server;

import com.google.gson.Gson;
import ir.sharif.model.Message;
import ir.sharif.utils.ConstantsLoader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {
	private static final int MESSAGE_PORT = Integer.parseInt(ConstantsLoader.getInstance().getProperty("message.port"));
	private static final int GET_PORT = Integer.parseInt(ConstantsLoader.getInstance().getProperty("get.port"));
	private ExecutorService executorService = Executors.newFixedThreadPool(10);
	private Gson gson = new Gson();
	private ArrayList<Message> messages = new ArrayList<>();


	public void startMessageServer() {
		try (ServerSocket serverSocket = new ServerSocket(MESSAGE_PORT)) {
			System.out.println("Message Server started on port " + MESSAGE_PORT);
			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.err.println("accepted client on message server");
				executorService.submit(() -> handleMessageClient(clientSocket));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startGetServer() {
		try (ServerSocket serverSocket = new ServerSocket(GET_PORT)) {
			System.out.println("Get Server started on port " + GET_PORT);
			while (true) {
				Socket clientSocket = serverSocket.accept();
				executorService.submit(() -> handleGetClient(clientSocket));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void handleMessageClient(Socket clientSocket) {
		System.err.println("wtf is happening");

		try {
			InputStream inputStream = clientSocket.getInputStream();
			while (true) {
				if (inputStream.available() > 0) {
					String receivedMessage = new String(inputStream.readAllBytes());
					Message newMessage = gson.fromJson(receivedMessage, Message.class);
					System.err.println(newMessage);
					messages.add(newMessage);
				} else {
					// Add a small delay to prevent busy waiting
					Thread.sleep(100);
				}
			}
		} catch (IOException e) {
			System.out.println("Error handling message client: " + e.getMessage());
			e.printStackTrace();
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}

	private void handleGetClient(Socket clientSocket) {
		try {
			OutputStream outputStream = clientSocket.getOutputStream();
			String allMessages = gson.toJson(messages);
			outputStream.write(allMessages.getBytes());
			outputStream.flush();
		} catch (IOException e) {
			System.out.println("Error handling get client: " + e.getMessage());
			e.printStackTrace();
		}
	}
}