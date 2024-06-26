package ir.sharif.view;

import com.almasb.fxgl.core.View;
import ir.sharif.utils.ConstantsLoader;
import ir.sharif.view.gui.terminal.TerminalGUI;
import javafx.animation.FadeTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;

public class ViewLoader {
	static Stage stage;

	public static void setStage(Stage stage) {
		ViewLoader.stage = stage;
	}

	public static Stage getStage() {
		return stage;
	}

	public static Scene newScene;
	private static boolean isBegin = true;

	public static void newScene(String menuName) {
		FXMLLoader fxmlLoader = new FXMLLoader(ViewLoader.class.getResource("/FXML/" + menuName + "-view.fxml"));
		Scene scene = null;
		try {
			scene = new Scene(fxmlLoader.load());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		if (!menuName.equals("game"))
			scene.getStylesheets().add(ViewLoader.class.getResource("/CSS/menu.css").toExternalForm());
		else
			scene.getStylesheets().add(ViewLoader.class.getResource("/CSS/game.css").toExternalForm());

		System.out.println(scene.getStylesheets());
		Pane pane = (Pane) scene.getRoot();

		double width = -1;
		double height = -1;
		if (!isBegin) {
			FadeTransition ft = new FadeTransition(Duration.millis(1000), stage.getScene().getRoot());
			ft.setFromValue(1.0);
			ft.setToValue(0.0);

			newScene = scene;
			ft.setOnFinished(event -> {
				stage.setScene(newScene);
				centerStage();
				setGlobalShortcuts(newScene);
				FadeTransition fadeIn = new FadeTransition(Duration.millis(1000), newScene.getRoot());
				fadeIn.setFromValue(0.0);
				fadeIn.setToValue(1.0);
				fadeIn.play();
			});

			width = ((Pane)newScene.getRoot()).getPrefWidth();
			height = ((Pane)newScene.getRoot()).getPrefHeight();
			ft.play();
		} else {
			isBegin = false;
			stage.setScene(scene);
		}

		if (width < 0) {
			width = ((Pane)scene.getRoot()).getPrefWidth();
			height = ((Pane)scene.getRoot()).getPrefHeight();
		}

		stage.show();
		if (!menuName.equals("game"))
			pane.setBackground(new Background(createBackgroundImage("background.jpg", width, height)));
		else {
			pane.setBackground(new Background(createBackgroundImage("board.jpg", width, height)));
			GameGraphics.getInstance().initialize(pane);
		}
	}

	private static void setGlobalShortcuts(Scene newScene) {
		Pane root = (Pane) newScene.getRoot();
		root.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			KeyEvent keyEvent = (KeyEvent) event;
			if (event.getCode() == KeyCode.T) {
				new TerminalGUI(null);
			} else if (event.getCode() == KeyCode.C) {
				ViewLoader.newScene("chat");
			}
		});

	}

	public static void centerStage() {
		stage.setX(Screen.getPrimary().getBounds().getWidth() / 2 - stage.getWidth() / 2);
		stage.setY(Screen.getPrimary().getBounds().getHeight() / 2 - stage.getHeight() / 2);
	}

	public static BackgroundImage createBackgroundImage(String name, double width, double height) {
		Image image = new Image(ViewLoader.class.getResource("/images/" + name).toExternalForm(),
			width, height,
			false, false);

		BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
			BackgroundRepeat.NO_REPEAT,
			BackgroundPosition.DEFAULT,
			BackgroundSize.DEFAULT);

		return backgroundImage;
	}
}
