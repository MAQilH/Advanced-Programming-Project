package ir.sharif.view.controllers;

import ir.sharif.controller.MainMenuController;
import ir.sharif.controller.PreGameController;
import ir.sharif.service.UserService;
import ir.sharif.view.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import javax.swing.text.View;

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
		UserService.getInstance().setCurrentUser(null);
		ViewLoader.newScene("start");
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

		ViewLoader.newScene("lobby");
	}

	public void selectDeck(MouseEvent mouseEvent) {
		Pregame.isSingle = true;
		ViewLoader.newScene("pregame");
	}
}
