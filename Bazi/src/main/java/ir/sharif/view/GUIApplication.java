package ir.sharif.view;

import ir.sharif.controller.GameController;
import ir.sharif.controller.LoginController;
import ir.sharif.controller.PreGameController;
import ir.sharif.controller.RegisterController;
import ir.sharif.model.SecurityQuestion;
import ir.sharif.model.game.CardTypes;
import ir.sharif.service.BackgroundMusicService;
import ir.sharif.service.GameService;
import ir.sharif.utils.ConstantsLoader;
import ir.sharif.view.controllers.Game;
import ir.sharif.view.game.CardGraphics;
import ir.sharif.view.gui.terminal.TerminalGUI;
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GUIApplication extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		ViewLoader.setStage(primaryStage);
		Font.loadFont(getClass().getResource("/fonts/KingsCross.ttf").toExternalForm(), 10);
		Font.loadFont(getClass().getResource("/fonts/Ancient.ttf").toExternalForm(), 10);

		primaryStage.setTitle(ConstantsLoader.getInstance().getProperty("app.title"));
		primaryStage.initStyle(StageStyle.UNDECORATED);
		ViewLoader.newScene("start");
		//Pane pane = (Pane) (ViewLoader.getStage().getScene().getRoot());
		//pane.getChildren().add(new CardGraphics(CardTypes.KAYRAN.getInstance(), 0.5));
		BackgroundMusicService.getInstance().playMusic();
		new RegisterController().register("sohsoh", "Soheil@84", "Soheil@84", new SecurityQuestion("fuck", "fuck"), "sohsoh", "sohsoh84@gmail.com");
		new RegisterController().register("guest", "Soheil@84", "Soheil@84", new SecurityQuestion("fuck", "fuck"), "guest", "aqil@gmail.com");
		new RegisterController().register("aqil", "Soheil@84", "Soheil@84", new SecurityQuestion("fuck", "fuck"), "aqil", "aqil2@gmail.com");
		new LoginController().login("sohsoh", "Soheil@84", true);
		//ViewLoader.newScene("pregame");
				PreGameController preGameController = new PreGameController();
		System.err.println(preGameController.createGame("guest").statusCode());
		System.err.println(preGameController.loadDeck("test").statusCode());
		System.err.println(preGameController.changeTurn().statusCode());
		System.err.println(preGameController.loadDeck("test").statusCode());
		System.err.println(preGameController.changeTurn().statusCode());
		System.err.println(preGameController.startGame().statusCode());

		GameService.getInstance().createController();
		ViewLoader.newScene("game");

		//ViewLoader.newScene("friend-request");
	}

	public static void main(String[] args) {
		launch();
	}
}
