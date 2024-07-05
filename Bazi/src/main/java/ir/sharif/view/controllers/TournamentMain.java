package ir.sharif.view.controllers;

import ir.sharif.client.TCPClient;
import ir.sharif.model.GameState;
import ir.sharif.model.User;
import ir.sharif.model.game.MatchTable;
import ir.sharif.model.server.TournamentMatchOpponentResult;
import ir.sharif.model.server.TournamentState;
import ir.sharif.service.GameService;
import ir.sharif.service.UserService;
import ir.sharif.view.ViewLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import javafx.scene.control.*;

import static java.lang.Thread.sleep;

public class TournamentMain {
	@FXML
	private Label errorLabel;
	@FXML
	private Label stateLabel;
	@FXML
	private Button readyButton;
	@FXML
	public void initialize() {
		stateLabel.setText("Waiting for users to join");
	}

	public void ready(MouseEvent mouseEvent) {
		TCPClient client = new TCPClient();
		client.readyPlayer(UserService.getInstance().getCurrentUser().getUsername(), true,
			UserService.getInstance().getTournamentToken());

		Thread updateThread = new Thread(() -> {
			while (ViewLoader.getViewName().equals("tr-main")) {
				Platform.runLater(() -> {
					TCPClient client1 = new TCPClient();
					TournamentState state = client1.getTournamentState(UserService.getInstance().getTournamentToken());
					if (state == TournamentState.PENDDING) {
						stateLabel.setText("Waiting for users to join");
					} else if (state == TournamentState.FINISHED) {
						stateLabel.setText("Tournament finished, winner: " +
							client1.getTournament(UserService.getInstance().getTournamentToken())
								.getWinner().getUsername());
					} else {
						stateLabel.setText("Tournament started, Please don't leave this page");
					}

					TournamentMatchOpponentResult result = client1.getOpponent(UserService.getInstance().getCurrentUser().getUsername(),
						UserService.getInstance().getTournamentToken());

					if (result == null) return;
					User user2 = result.getOpponent();
					if (user2 != null) {
						User user1 = UserService.getInstance().getCurrentUser();
						if (user1.getUsername().compareTo(user2.getUsername()) > 0) {
							User temp = user1;
							user1 = user2;
							user2 = temp;
						}

						String gameToken = result.getGameToken();
						GameService.getInstance().setMatchTable(new MatchTable(user1, user2, gameToken,
							UserService.getInstance().getTournamentToken()));
						GameService.getInstance().createController(GameState.ONLINE_PLAYER);
						ViewLoader.setMenuName("game");
						ViewLoader.newScene("game");
					}
				});

				try {
					sleep(2000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			}
		});

		updateThread.start();
		
	}
}