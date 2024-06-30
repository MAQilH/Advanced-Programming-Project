package ir.sharif.view.controllers;

import ir.sharif.client.TCPClient;
import ir.sharif.model.GameHistory;
import ir.sharif.model.GameState;
import ir.sharif.model.game.MatchTable;
import ir.sharif.model.server.GameRecord;
import ir.sharif.service.GameService;
import ir.sharif.view.ViewLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class Television {
	@FXML
	private ListView<String> liveGames;
	@FXML
	private ListView<String> pastGames;
	@FXML
	private Label errorLabel;

	HashMap<String, GameHistory> pastGamesMap = new HashMap<>();
	HashMap<String, GameRecord> liveGamesMap = new HashMap<>();

	@FXML
	public void initialize() {
		liveGames.setStyle("-fx-background-color: transparent;");
		pastGames.setStyle("-fx-background-color: transparent;");

		liveGames.setCellFactory(lv -> {
			ListCell<String> cell = new ListCell<>();
			cell.textProperty().bind(cell.itemProperty());
			cell.setOnMouseClicked(event -> {
				if (!cell.isEmpty() && event.getClickCount() == 1) {
					handleLiveGameClick(cell.getItem());
				}
			});

			return cell;
		});

		pastGames.setCellFactory(lv -> {
			ListCell<String> cell = new ListCell<>();
			cell.textProperty().bind(cell.itemProperty());
			cell.setOnMouseClicked(event -> {
				if (!cell.isEmpty() && event.getClickCount() == 2) {
					handlePastGamesClick(cell.getItem());
				}
			});

			return cell;
		});

		Thread thread = new Thread(() -> {
			while (true) {
				update();
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		});

		thread.start();
		update();
	}

	public String getLabelForGameHistory(GameHistory history) {
		String user1Label = history.getUser1().getUsername() + "(" + history.getRoundScores().getFirst() + ")";
		String user2Label = history.getUser2().getUsername() + "(" + history.getRoundScores().getLast() + ")";
		if (history.getGameToken() != null) user1Label = "<" + history.getGameToken().substring(0, 3) + "> " + user1Label;
		return user1Label + " vs " + user2Label;
	}

	public String getLabelForLiveGame(GameRecord record) {
		String user1Label = record.getUser1().getUsername();
		String user2Label = record.getUser2().getUsername();
		return record.getGameToken().substring(0, 3) + " " + user1Label + " vs " + user2Label;
	}

	public synchronized void update() {
		Platform.runLater(() -> {
			pastGamesMap.clear();
			pastGames.getItems().clear();
			liveGamesMap.clear();
			liveGames.getItems().clear();;
			TCPClient client = new TCPClient();
			ArrayList<GameHistory> histories = client.getGameHistories();
			for (GameHistory history : histories) {
				String label = getLabelForGameHistory(history);
				pastGames.getItems().add(label);
				pastGamesMap.put(label, history);
			}

			ArrayList<GameRecord> records = client.getLiveGames();
			for (GameRecord record : records) {
				String label = getLabelForLiveGame(record);
				liveGames.getItems().add(label);
				liveGamesMap.put(label, record);
			}
		});
	}

	private void handlePastGamesClick(String item) {
	}

	private synchronized void handleLiveGameClick(String item) {
		GameRecord record = liveGamesMap.get(item);
		GameService.getInstance().setMatchTable(new MatchTable(record.getUser1(), record.getUser2(),
			record.getGameToken()));
		GameService.getInstance().createController(GameState.ONLINE_OBSERVER);
		ViewLoader.newScene("game");
	}

	public void backButtonPress(MouseEvent mouseEvent) {
		ViewLoader.newScene("main");
	}
}
