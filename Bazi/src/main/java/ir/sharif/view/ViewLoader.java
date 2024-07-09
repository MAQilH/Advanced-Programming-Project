package ir.sharif.view;

import com.almasb.fxgl.core.View;
import ir.sharif.utils.ConstantsLoader;
import ir.sharif.view.gui.terminal.TerminalGUI;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class ViewLoader {
	static String viewName = "start";
	static Stage stage;
	private static Media media = new Media(ViewLoader.class.getResource("/background.mp4").toExternalForm());
	private static MediaPlayer mediaPlayer = new MediaPlayer(media);
	{
		mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		mediaPlayer.setAutoPlay(true);
		mediaPlayer.play();
	}

	public static void setStage(Stage stage) {
		ViewLoader.stage = stage;
	}

	public static Stage getStage() {
		return stage;
	}

	public static Scene newScene;
	private static boolean isBegin = true;


	public static void newScene(String menuName) {
		viewName = menuName;
		FXMLLoader fxmlLoader = new FXMLLoader(ViewLoader.class.getResource("/FXML/" + menuName + "-view.fxml"));
		Scene scene = null;
		try {
			scene = new Scene(fxmlLoader.load());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		if (!menuName.equals("game"))
			scene.getStylesheets().add(ViewLoader.class.getResource("/CSS/menu.css").toExternalForm());
		else
			scene.getStylesheets().add(ViewLoader.class.getResource("/CSS/game.css").toExternalForm());

		System.err.println(scene.getCursor());
		System.out.println(scene.getStylesheets());
		Pane pane = (Pane) scene.getRoot();
		pane.setCursor(new ImageCursor(new Image(ViewLoader.class.getResourceAsStream("/icons/cursor.png"))));
		pane.setOnMouseEntered(event -> pane.setCursor(new ImageCursor(new Image(ViewLoader.class.getResourceAsStream("/icons/cursor.png")))));

		double width = -1;
		double height = -1;
		if (!isBegin) {
			FadeTransition ft = new FadeTransition(Duration.millis(1000), stage.getScene().getRoot());
			ft.setFromValue(1.0);
			ft.setToValue(0.0);

			newScene = scene;
			ft.setOnFinished(event -> {
				stage.setScene(newScene);
				// centerStage(); // TODO: uncomment
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
		}
	}

	private static void setGlobalShortcuts(Scene newScene) {
		Pane root = (Pane) newScene.getRoot();
		// if ctrl t pressed, open terminal
		newScene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
			if (event.getCode() == KeyCode.T && event.isShiftDown()) {
				new TerminalGUI(null);
			} if (event.getCode() == KeyCode.C && event.isShiftDown()) {
				new ChatGUI();
			} if (event.getCode() == KeyCode.R && event.isShiftDown()) {
				new ReactionGUI();
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

	public static String getViewName() {
		return viewName;
	}

	public static void setMenuName(String name) {
		viewName = name;
	}
}
