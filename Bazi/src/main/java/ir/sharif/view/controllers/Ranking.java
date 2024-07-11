package ir.sharif.view.controllers;


import ir.sharif.client.TCPClient;
import ir.sharif.model.GameHistory;
import ir.sharif.model.GameState;
import ir.sharif.model.User;
import ir.sharif.model.game.MatchTable;
import ir.sharif.service.GameHistoryService;
import ir.sharif.service.GameService;
import ir.sharif.service.UserService;
import ir.sharif.view.ViewLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import javax.swing.text.View;
import java.util.ArrayList;
import java.util.HashMap;

public class Ranking {
	@FXML
	ListView<String> ranking;
	@FXML
	Label errorLabel;

	HashMap<String, String> usernameMap = new HashMap<>();

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
			while (ViewLoader.getViewName().equals("ranking")) {
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
			ranks.put(user, GameHistoryService.getInstance().getUserRank(user.getUsername()));
			isOnline.put(user, client.getUserStatus(user.getUsername()));
		}

		allUsers.sort((User user1, User user2) -> {
			return ranks.get(user1) - ranks.get(user2);
		});

		Platform.runLater(() -> {
			usernameMap.clear();
			ranking.getItems().clear();
			for (User user : allUsers) {
				String s = ranks.get(user) + ". " + user.getUsername() + "(" + GameHistoryService.getInstance().getHighestScore(user.getUsername()) + ")" + " -" +
					(isOnline.get(user) ? "Online" : "Offline") + "-";
				usernameMap.put(s, user.getUsername());
				ranking.getItems().add(s);
			}
		});
	}

	private void handleFriendClick(String item) {
		GameHistoryService service = GameHistoryService.getInstance();
		ArrayList<GameHistory> histories = service.getUserHistory(usernameMap.get(item));
		GameHistory lastOnlineGame = null;
		for (GameHistory history : histories) {
			if (history.getGameToken() != null)
				lastOnlineGame = history;
		}

		if (lastOnlineGame == null) {
			errorLabel.setText("No online game found for the user");
			return;
		}

		GameService.getInstance().setMatchTable(new MatchTable(lastOnlineGame.getUser1(), lastOnlineGame.getUser2(),
			lastOnlineGame.getGameToken(), null));
		GameService.getInstance().createController(GameState.OFFLINE_OBSERVER);
		ViewLoader.newScene("game");
	}

	public void back(MouseEvent mouseEvent) {
		ViewLoader.newScene("main");
	}
}
