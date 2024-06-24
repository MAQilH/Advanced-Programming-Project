package ir.sharif.view.controllers;

import ir.sharif.controller.MainMenuController;
import ir.sharif.service.UserService;
import ir.sharif.view.ViewLoader;
import javafx.scene.input.MouseEvent;

import javax.swing.text.View;

public class Main {
	public void newGamePress(MouseEvent mouseEvent) {
		ViewLoader.newScene("pregame");
	}

	public void profileMenuPress(MouseEvent mouseEvent) {
		ViewLoader.newScene("profile");

	}

	public void logoutPress(MouseEvent mouseEvent) {
		UserService.getInstance().setCurrentUser(null);
		ViewLoader.newScene("start");
	}
}
