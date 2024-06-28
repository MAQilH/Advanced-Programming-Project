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
                ArrayList<Message> messages = tcpClient.getMessages();
                System.err.println(messages);
                if (messages != null) {
                    Platform.runLater(() -> {
                        if (messages.size() == messageBox.getChildren().size())
                            return;
                        messageBox.getChildren().clear();
                        for (Message message : messages) {
                            TextArea messageArea = new TextArea(message.getSenderUsername() + ":  " +
                                    message.getMessage() + "\n");
                            messageArea.setEditable(false);
                            messageArea.setWrapText(true);
                            messageArea.setOnMouseEntered(event -> messageArea.setStyle("-fx-opacity: 0.7;"));
                            messageArea.setOnMouseExited(event -> messageArea.setStyle("-fx-opacity: 1.0;"));
                            messageArea.setPrefRowCount(4);
                            messageBox.getChildren().add(messageArea);
                        }
                    });
                }

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
            tcpClient.sendChatMessage(input, UserService.getInstance().getCurrentUser().getUsername());
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