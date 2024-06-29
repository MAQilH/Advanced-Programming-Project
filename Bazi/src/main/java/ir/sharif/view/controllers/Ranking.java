package ir.sharif.view.controllers;


import ir.sharif.client.TCPClient;
import ir.sharif.model.User;
import ir.sharif.service.UserService;
import ir.sharif.view.ViewLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class Ranking {
	@FXML
	ListView<String> ranking;

	@FXML
	public void initialize() {
		ranking.setStyle("-fx-background-color: rgba(1, 1, 1, 0.5)");
		ranking.setCellFactory(lv -> {
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
		Thread thread = new Thread(() -> {
			while (true) {
				update();
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		});

		thread.start();
	}

	public void update() {
		ArrayList<User> allUsers = UserService.getInstance().getUsers();
		HashMap<User, Integer> ranks = new HashMap<>();
		HashMap<User, Boolean> isOnline = new HashMap<>();
		for (User user : allUsers) {
			TCPClient client = new TCPClient();
			// TODO: add ranks
			ranks.put(user, 1);
			isOnline.put(user, client.getUserStatus(user.getUsername()));
		}

		allUsers.sort((User user1, User user2) -> {
			return ranks.get(user1) - ranks.get(user2);
		});

		Platform.runLater(() -> {
			ranking.getItems().clear();
			for (User user : allUsers) {
				ranking.getItems().add(ranks.get(user) + ". " + user.getUsername() + " -" +
					(isOnline.get(user) ? "Online" : "Offline") + "-");
			}
		});
	}

	private void handleFriendClick(String item) {
	}

	public void back(MouseEvent mouseEvent) {
		ViewLoader.newScene("main");
	}
}
