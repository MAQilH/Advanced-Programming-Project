package ir.sharif.view.controllers;


import ir.sharif.client.TCPClient;
import ir.sharif.model.CommandResult;
import ir.sharif.service.UserService;
import ir.sharif.view.ViewLoader;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class FriendRequest {
	@FXML
	private ListView<String> friends;
	@FXML
	private ListView<String> pendingFriends;
	@FXML
	private TextField usernameTextField;
	private UserService userService = UserService.getInstance();

	private TCPClient client = new TCPClient();
	@FXML
	private Label errorLabel;

	@FXML
	public void initialize() {
		friends.getItems().add("Friend 1");
		friends.getItems().add("Friend 2");
		friends.getItems().add("Friend 3");
		friends.setStyle("-fx-background-color: transparent;");

		pendingFriends.setCellFactory(lv -> {
			ListCell<String> cell = new ListCell<>();
			cell.textProperty().bind(cell.itemProperty());
			cell.setOnMouseClicked(event -> {
				if (!cell.isEmpty() && event.getClickCount() == 2) {
					handleFriendClick(cell.getItem());
				}
			});

			return cell;
		});

		update();
	}

	private void handleFriendClick(String item) {
		System.err.println(item);
		client.acceptFriendRequest(item);
		update();
	}

	public void update() {
		ArrayList<String> friendArray = client.getFriends(userService.getCurrentUser().getUsername());
		if (friends != null) {
			friends.getItems().clear();
			for (String friendName : friendArray) {
				friends.getItems().add(friendName);
			}
		}

		ArrayList<String> pendingFriendsArray = client.getPendingFriendRequests(userService.getCurrentUser().getUsername());
		if (pendingFriendsArray != null) {
			pendingFriends.getItems().clear();
			for (String friendName : pendingFriendsArray) {
				pendingFriends.getItems().add(friendName);
			}
		}
	}

	public void sendFriendRequest(MouseEvent mouseEvent) {
		CommandResult result = client.sendFriendRequest(usernameTextField.getText());
		errorLabel.setText(result.message());
		update();
	}

	public void backButtonPress(MouseEvent mouseEvent) {
		ViewLoader.newScene("main");
	}

	public void refresh(MouseEvent mouseEvent) {
		update();
	}
}
