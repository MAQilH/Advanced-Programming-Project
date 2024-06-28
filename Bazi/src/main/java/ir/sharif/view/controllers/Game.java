package ir.sharif.view.controllers;

import ir.sharif.view.GameGraphics;
import ir.sharif.view.ViewLoader;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;

public class Game {
	@FXML
	public void initialize() {
		Platform.runLater(() -> {
			PauseTransition transition = new PauseTransition();
			transition.setDuration(javafx.util.Duration.seconds(2));
			transition.setOnFinished(event -> {
				GameGraphics.getInstance().initialize((Pane) ViewLoader.getStage().getScene().getRoot());
				GameGraphics.getInstance().startGame();
			});

			transition.play();
		});
	}
}
