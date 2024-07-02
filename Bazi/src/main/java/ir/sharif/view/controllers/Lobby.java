package ir.sharif.view.controllers;

import ir.sharif.client.TCPClient;
import ir.sharif.controller.GameController;
import ir.sharif.controller.PreGameController;
import ir.sharif.model.GameState;
import ir.sharif.model.User;
import ir.sharif.model.game.MatchTable;
import ir.sharif.model.server.GameRecord;
import ir.sharif.service.GameService;
import ir.sharif.service.UserService;
import ir.sharif.view.ViewLoader;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

import static java.lang.Thread.sleep;

public class Lobby {
	@FXML
	ListView<String> friends;
	@FXML
	Label errorLabel;
	@FXML
	Label usernameLabel;
	@FXML
	HBox hbox;
	@FXML
	Button acceptButton;
	@FXML
	CheckBox privateCheck;
	private String lastGameToken = null;
	private String lastGameCreated = null;

	@FXML
	public void initialize() {
		ArrayList<String> friendsArray = new TCPClient().getFriends(UserService.getInstance().getCurrentUser().getUsername());
		friends.setStyle("-fx-background-color: transparent;");
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
			System.err.println("fuck: " + ViewLoader.getViewName());
			while (ViewLoader.getViewName().equals("lobby")) {
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

				if (lastGameCreated != null) {
					User user2 = client.gameIsAccepted(lastGameCreated);
					if (user2 != null) {
						GameRecord record = client.getGameRecord(lastGameCreated);
						PreGameController controller = new PreGameController();
						controller.startOnlineGame(record);
						ViewLoader.setMenuName("game");
						Platform.runLater(() -> {
							ViewLoader.newScene("game");
						});

						return;
					}
				}

				try {
					sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		});

		gameLobbyThread.start();
	}

	private void handleFriendClick(String item) {
		TCPClient client = new TCPClient();
		// TODO: handle private and public game
		String gameToken = client.gameRequest(UserService.getInstance().getCurrentUser(), item, privateCheck.isSelected());
		if (gameToken == null) errorLabel.setText("Error in game request");
		else {
			errorLabel.setText("Game request sent to " + item);
			lastGameCreated = gameToken;
		}
	}

	public void random(MouseEvent mouseEvent) {
		TCPClient client = new TCPClient();
		String gameToken = client.randomGameRequest(UserService.getInstance().getCurrentUser());
		if (gameToken == null) errorLabel.setText("Error in game request");
		else {
			errorLabel.setText("Game request sent to random player");
			lastGameCreated = gameToken;

			Thread randomThread = new Thread(() -> {
				while (ViewLoader.getViewName().equals("lobby")) {
					System.err.println(gameToken);
					TCPClient client1 = new TCPClient();
					User user2 = client1.randomGameIsAccepted(UserService.getInstance().getCurrentUser().getUsername(), lastGameToken);
					if (user2 != null) {
						User user1 = UserService.getInstance().getCurrentUser();
						if (user1.getUsername().compareTo(user2.getUsername()) > 0) {
							User temp = user1;
							user1 = user2;
							user2 = temp;
						}

						GameService.getInstance().setMatchTable(new MatchTable(user1, user2, lastGameToken, null));
						GameService.getInstance().createController(GameState.ONLINE_PLAYER);
					}
				}

				try {
					sleep(1000);
				} catch (InterruptedException e) {
					throw new RuntimeException(e);
				}
			});

			randomThread.start();
		}
	}

	public void acceptGame(MouseEvent mouseEvent) {
		acceptButton.setDisable(true);
		TCPClient client = new TCPClient();
		User user2 = client.gameAcceptRequest(lastGameToken, UserService.getInstance().getCurrentUser());
		GameRecord record = client.getGameRecord(lastGameToken);
		PreGameController controller = new PreGameController();
		controller.startOnlineGame(record);
		lastGameToken = null;
		hbox.setOpacity(0);

		ViewLoader.newScene("game");
	}

	public void back(MouseEvent mouseEvent) {
		ViewLoader.newScene("main");
	}
}
