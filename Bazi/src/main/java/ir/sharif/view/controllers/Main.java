package ir.sharif.view.controllers;

import ir.sharif.controller.MainMenuController;
import ir.sharif.service.UserService;
import ir.sharif.view.ViewLoader;
import javafx.scene.input.MouseEvent;

public class Main {
	public void newGamePress(MouseEvent mouseEvent) {

	}

	public void profileMenuPress(MouseEvent mouseEvent) {
		ViewLoader.newScene("profile");

	}

	public void logoutPress(MouseEvent mouseEvent) {
		UserService.getInstance().setCurrentUser(null);
		ViewLoader.newScene("start");
	}
}
