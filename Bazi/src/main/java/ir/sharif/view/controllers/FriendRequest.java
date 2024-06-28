package ir.sharif.view.controllers;

import ir.sharif.client.TCPClient;
import ir.sharif.service.UserService;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
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
	public void initialize() {
		friends.getItems().add("Friend 1");
		friends.getItems().add("Friend 2");
		friends.getItems().add("Friend 3");
		friends.setStyle("-fx-background-color: transparent;");

		pendingFriends.getItems().add("Pending Friend 1");
		pendingFriends.getItems().add("Pending Friend 2");
		pendingFriends.getItems().add("Pending Friend 3");
	}

	public void update() {
		ArrayList<String> friends = client.getFriends(userService.getCurrentUser().getUsername());
	}

	public void sendFriendRequest(MouseEvent mouseEvent) {

	}

	public void backButtonPress(MouseEvent mouseEvent) {
	}

	public void refresh(MouseEvent mouseEvent) {
	}
}
