package ir.sharif.server;

import com.almasb.fxgl.net.Server;
import com.google.gson.Gson;
import ir.sharif.model.Message;
import ir.sharif.utils.ConstantsLoader;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ChatServer {
	private static final int PORT = Integer.parseInt(ConstantsLoader.getInstance().getProperty("chat.port"));
	ArrayList<Message> messages = new ArrayList<>();
	Gson gson = new Gson();
	public void startServer() {
		try (ServerSocket serverSocket = new ServerSocket(PORT)) {
			System.out.println("Server started on port " + PORT);
			while (true) {
				Socket clientSocket = serverSocket.accept();
				new Thread(() -> handleClient(clientSocket)).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void handleClient(Socket clientSocket) {
		try (InputStream inputStream = clientSocket.getInputStream()) {
			OutputStream outputStream = clientSocket.getOutputStream();
			String message = new String(inputStream.readAllBytes());
			System.err.println("fuck: " + message);
			if (message.equals("get")) {
				outputStream.write(gson.toJson(messages).getBytes());
				outputStream.flush();
			} else {
				try {
					Message newMessage = gson.fromJson(message, Message.class);
					messages.add(newMessage);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
