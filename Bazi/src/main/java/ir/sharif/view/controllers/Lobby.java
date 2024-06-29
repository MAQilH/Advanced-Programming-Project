package ir.sharif.view.controllers;

import ir.sharif.client.TCPClient;
import ir.sharif.model.User;
import ir.sharif.model.server.GameRecord;
import ir.sharif.service.UserService;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class Lobby {
	@FXML
	ListView<String> friends;
	@FXML
	Label errorLabel;
	@FXML
	Label usernameLabel;
	@FXML
	HBox hbox;
	private String lastGameToken = null;

	@FXML
	public void initialize() {
		ArrayList<String> friendsArray = new TCPClient().getFriends(UserService.getInstance().getCurrentUser().getUsername());
		friends.setCellFactory(lv -> {
			ListCell<String> cell = new ListCell<>();
			cell.textProperty().bind(cell.itemProperty());
			cell.setOnMouseClicked(event -> {
				if (!cell.isEmpty() && event.getClickCount() == 2) {
					handleFriendClick(cell.getItem());
				}
			});

			return cell;
		});

		friends.getItems().addAll(friendsArray);

		Thread gameLobbyThread = new Thread(() -> {
			while (true) {
				TCPClient client = new TCPClient();
				String token = client.getQueuedGame(UserService.getInstance().getCurrentUser().getUsername());
				System.err.println(token);
				if (token != null) {
					GameRecord record = client.getGameRecord(token);
					lastGameToken = token;
					Platform.runLater(() -> {
						usernameLabel.setText(record.getUser1().getUsername());
						hbox.setOpacity(1);
					});
				}

				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		gameLobbyThread.start();
	}

	private void handleFriendClick(String item) {
		TCPClient client = new TCPClient();
		String gameToken = client.gameRequest(UserService.getInstance().getCurrentUser(), item);
		if (gameToken == null) errorLabel.setText("Error in game request");
		else {
			errorLabel.setText("Game request sent to " + item);
			System.err.println(gameToken);
		}
	}

	public void random(MouseEvent mouseEvent) {

	}

	public void acceptGame(MouseEvent mouseEvent) {
		TCPClient client = new TCPClient();
		User user2 = client.gameAcceptRequest(lastGameToken, UserService.getInstance().getCurrentUser());
		errorLabel.setText("game accepted");
	}
}
