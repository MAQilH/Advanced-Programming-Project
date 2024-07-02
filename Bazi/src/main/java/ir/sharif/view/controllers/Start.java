package ir.sharif.view.controllers;

import com.almasb.fxgl.core.View;
import ir.sharif.controller.LoginController;
import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.view.ViewLoader;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.util.Timer;

public class Start {
	public static int count = 0;
	@FXML
	public void initialize() {
		CommandResult result = (new LoginController().isLogin());
		if (count == 0 && result.statusCode() == ResultCode.ACCEPT) {
			Thread thread = new Thread(() -> {
				Platform.runLater(() -> ViewLoader.newScene("main"));
			});

			thread.start();
			count++;
		}
	}

	public void loginButtonClickListener(MouseEvent mouseEvent) {
		ViewLoader.newScene("login");
	}

	public void signupButtonClickListener(MouseEvent mouseEvent) {
		ViewLoader.newScene("signup");
	}

	public void exitButtonClickListener(MouseEvent mouseEvent) {
		System.exit(0);
	}
}
