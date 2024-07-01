package ir.sharif.view;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ir.sharif.client.TCPClient;
import ir.sharif.model.Message;
import ir.sharif.service.UserService;
import ir.sharif.utils.ConstantsLoader;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.function.Function;

public class ChatGUI {
    private Stage stage;
    private VBox messageBox;
    private ScrollPane scrollPane;
    private TCPClient tcpClient = new TCPClient();

    public ChatGUI() {
        stage = new Stage();
        stage.setTitle("Game Chat");

        messageBox = new VBox();
        scrollPane = new ScrollPane(messageBox);
        scrollPane.setMinHeight(300);

        Scene scene = getTerminalScene(scrollPane);

        stage.setScene(scene);
        stage.show();
        stage.sizeToScene();

        Thread updateChatThread = new Thread(() -> {
            while (true) {
				final int previousSize = messageBox.getChildren().stream()
					.filter(node -> node instanceof TextArea).toArray().length;
				final int previousReactSize = messageBox.getChildren().stream()
					.filter(node -> node instanceof ImageView).toArray().length;

                ArrayList<Message> messages = tcpClient.getMessages();
				final int reactSize = messages.stream().filter(Message::getReact).toArray().length;

                if (messages != null) {
                    Platform.runLater(() -> {
                        if (messages.size() == previousSize && reactSize == previousReactSize)
                            return;
                        messageBox.getChildren().clear();

						int index = 0;
                        for (Message message : messages) {
                            TextArea messageArea = new TextArea(message.getSenderUsername() + "(on "
	                            + message.getDate()
	                            + "):\n " + message.getMessage());
                            messageArea.setEditable(false);
                            messageArea.setWrapText(true);
                            messageArea.setOnMouseEntered(event -> messageArea.setStyle("-fx-opacity: 0.7;"));
                            messageArea.setOnMouseExited(event -> messageArea.setStyle("-fx-opacity: 1.0;"));
	                        int finalIndex = index;
	                        messageArea.setOnMouseClicked(event -> {
								if (event.getClickCount() == 2) {
									TCPClient client = new TCPClient();
									client.reactToMessage(finalIndex);
								}
							});

							index++;
                            messageArea.setPrefRowCount(4);
                            messageBox.getChildren().add(messageArea);

							if (message.getReact()) {
								ImageView likeImageView = new ImageView();
								likeImageView.setImage(new Image(getClass().getResourceAsStream("/icons/like.png")));
								likeImageView.setFitHeight(20);
								likeImageView.setFitWidth(20);
								messageBox.getChildren().add(likeImageView);
							}
                        }

					});
                }

				Platform.runLater(() -> {
					scrollPane.setVvalue(1.0);
				});

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        updateChatThread.start();
    }

    @NotNull
    private Scene getTerminalScene(ScrollPane scrollPane) {
        TextField inputField = new TextField();
        inputField.setPromptText("Write your input here: ");
        inputField.setStyle("-fx-control-inner-background:#1d1d1d; -fx-font-family: Monospaced; -fx-highlight-fill: dodgerblue; -fx-highlight-text-fill: white; -fx-text-fill: lime; -fx-prompt-text-fill: #aaaaaa;");
        inputField.setOnKeyPressed(event -> {
            if (event.getCode() != KeyCode.ENTER) return;
            String input = inputField.getText();
            tcpClient.sendChatMessage(UserService.getInstance().getCurrentUser().getUsername(), input);
            inputField.clear();
        });

        VBox layout = new VBox(scrollPane, inputField);
        layout.setSpacing(0);
        layout.setPadding(new Insets(0));
        VBox.setMargin(scrollPane, new Insets(0));
        VBox.setMargin(inputField, new Insets(0));

        Scene scene = new Scene(layout);
        scene.setFill(Color.BLACK);
        return scene;
    }
}