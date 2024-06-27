package ir.sharif.view;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import ir.sharif.model.CommandResult;
import ir.sharif.model.Message;
import ir.sharif.service.UserService;
import ir.sharif.utils.ConstantsLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Function;

public class ChatGUI {
	private Stage stage;
	private TextArea outputArea;
	public static InputStream inputStream;
	private OutputStream outputStream;
	private Socket socket;
	private final Gson gson = new Gson();

	public ChatGUI() {
		stage = new Stage();
		stage.setTitle("Game Chat");

		outputArea = new TextArea();
		outputArea.setMinHeight(300);
		outputArea.setEditable(false); // The output area should not be editable
		outputArea.setStyle("-fx-control-inner-background:#1d1d1d; -fx-font-family: Monospaced; -fx-highlight-fill: dodgerblue; -fx-highlight-text-fill: white; -fx-text-fill: lime;"); // Mac terminal style

		Scene scene = getTerminalScene(outputArea);

		stage.setScene(scene);
		stage.show();
		stage.sizeToScene();

		try {
			socket = new Socket("localhost", Integer.parseInt(ConstantsLoader.getInstance().getProperty("chat.port")));
			inputStream = socket.getInputStream();
			outputStream = socket.getOutputStream();
		} catch (Exception e) {
			e.printStackTrace();
		}

		Thread thread = new Thread(() -> {
			while (true) {
				try {
					outputStream.write("get".getBytes());
					outputStream.flush();
					String allMessages = new String(ChatGUI.inputStream.readAllBytes());
					ArrayList<Message> messages = gson.fromJson(allMessages, ArrayList.class);
					StringBuilder text = new StringBuilder();
					for (Message message : messages) {
						text.append(message.getSenderUsername()).append(": ").append(message.getMessage()).append("\n");
					}

					outputArea.setText(text.toString());
				} catch (IOException e) {
					throw new RuntimeException(e);
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}

		});
	}

	@NotNull
	private Scene getTerminalScene(TextArea outputArea) {
		TextField inputField = new TextField();
		inputField.setPromptText("Write your input here: "); // Append $ to input part
		inputField.setStyle("-fx-control-inner-background:#1d1d1d; -fx-font-family: Monospaced; -fx-highlight-fill: dodgerblue; -fx-highlight-text-fill: white; -fx-text-fill: lime; -fx-prompt-text-fill: #aaaaaa;"); // Mac terminal style
		inputField.setOnKeyPressed(event -> {
			if (event.getCode() != KeyCode.ENTER) return;
			String input = inputField.getText();
			Message message = new Message(UserService.getInstance().getCurrentUser().getUsername(), input);
			String json = gson.toJson(message);
			try {
				outputStream.write(json.getBytes());
				outputStream.flush();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		});

		VBox layout = new VBox(outputArea, inputField); // Remove padding to eliminate white space
		layout.setSpacing(0); // Remove spacing between TextArea and TextField
		layout.setPadding(new Insets(0)); // Remove padding
		VBox.setMargin(outputArea, new Insets(0)); // Remove margins
		VBox.setMargin(inputField, new Insets(0)); // Remove margins

		Scene scene = new Scene(layout);
		scene.setFill(Color.BLACK);
		return scene;
	}
}
