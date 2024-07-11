package ir.sharif.view.controllers;

import ir.sharif.client.TCPClient;
import ir.sharif.service.UserService;
import ir.sharif.view.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class Main {
	@FXML
	Label errorLabel;

	public void newGamePress(MouseEvent mouseEvent) {
		Pregame.isSingle = false;
		ViewLoader.newScene("pregame");
	}

	public void profileMenuPress(MouseEvent mouseEvent) {
		ViewLoader.newScene("profile");

	}

	public void logoutPress(MouseEvent mouseEvent) {
		String username = UserService.getInstance().getCurrentUser().getUsername();
		UserService.getInstance().setCurrentUser(null);
		ViewLoader.newScene("start");
		TCPClient client = new TCPClient();
		client.setUserStatus(username, false);
	}

	public void friends(MouseEvent mouseEvent) {
		ViewLoader.newScene("friend-request");
	}

	public void gameLobby(MouseEvent mouseEvent) {
		System.err.println(UserService.getInstance().getCurrentUser().getUsername());
		System.err.println(UserService.getInstance().getCurrentUser().getDeckInfo());
		if (UserService.getInstance().getCurrentUser().getDeckInfo() == null) {
			errorLabel.setText("set your deck before entering lobby");
			return;
		}

		ViewLoader.setMenuName("lobby");
		ViewLoader.newScene("lobby");
	}

	public void selectDeck(MouseEvent mouseEvent) {
		Pregame.isSingle = true;
		ViewLoader.newScene("pregame");
	}

	public void rankingPress(MouseEvent mouseEvent) {
		ViewLoader.newScene("ranking");
	}

	public void televisionPress(MouseEvent mouseEvent) {
		ViewLoader.newScene("television");
	}

	public void tournamentPress(MouseEvent mouseEvent) {
		ViewLoader.setMenuName("tr-lobby");
		ViewLoader.newScene("tr-lobby");
	}
}
