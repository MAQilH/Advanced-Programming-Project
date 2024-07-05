package ir.sharif.view.controllers;

import ir.sharif.service.GameHistoryService;
import ir.sharif.service.UserService;
import ir.sharif.view.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class GameHistory {
	@FXML
	private ListView histories;
	@FXML
	public void initialize() {
		ArrayList<ir.sharif.model.GameHistory> userHistories = GameHistoryService.getInstance()
			.getUserHistory(UserService.getInstance().getCurrentUser().getUsername());

		histories.setStyle("-fx-background-color: transparent;");
		for (ir.sharif.model.GameHistory history : userHistories) {
			String data = "Date: " + history.getDate() + "\n" +
				"winner: " + history.getWinner().getUsername() + "\n" +
				"user1: " + history.getUser1().getUsername() + "\n" +
				"user2: " + history.getUser2().getUsername() + "\n" +
				"result: " + history.getRoundResult().getFirst() + " \\ " + history.getRoundResult().getSecond() + "\n";

			histories.getItems().add(data);
		}
	}

	public void back(MouseEvent mouseEvent) {
		ViewLoader.newScene("profile");
	}
}
