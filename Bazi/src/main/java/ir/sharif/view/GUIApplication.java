package ir.sharif.view;

import ir.sharif.controller.GameController;
import ir.sharif.controller.LoginController;
import ir.sharif.controller.PreGameController;
import ir.sharif.controller.RegisterController;
import ir.sharif.model.CommandResult;
import ir.sharif.model.GameHistory;
import ir.sharif.model.SecurityQuestion;
import ir.sharif.model.User;
import ir.sharif.model.game.CardTypes;
import ir.sharif.service.BackgroundMusicService;
import ir.sharif.service.GameHistoryService;
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

import java.util.ArrayList;

public class GUIApplication extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		ViewLoader.setStage(primaryStage);
		Font.loadFont(getClass().getResource("/fonts/KingsCross.ttf").toExternalForm(), 10);
		Font.loadFont(getClass().getResource("/fonts/Ancient.ttf").toExternalForm(), 10);

		primaryStage.setTitle(ConstantsLoader.getInstance().getProperty("app.title"));
		primaryStage.initStyle(StageStyle.UNDECORATED);
		ViewLoader.newScene("start");
		Pane pane = (Pane) (ViewLoader.getStage().getScene().getRoot());
		pane.getChildren().add(new CardGraphics(CardTypes.KAYRAN.getInstance(), 0.5));
		//new TerminalGUI(null);
		// BackgroundMusicService.getInstance().playMusic();
		CommandResult commandResult2 = new RegisterController().register("sohsoh", "Soheil@84", "Soheil@84", new SecurityQuestion("fuck", "fuck"), "sohsoh", "sohsoh84@gmail.com");
		System.out.println(commandResult2.message());
		new RegisterController().register("guest", "Soheil@84", "Soheil@84", new SecurityQuestion("fuck", "fuck"), "guest", "aqil@gmail.com");
		new RegisterController().register("aqil", "Soheil@84", "Soheil@84", new SecurityQuestion("fuck", "fuck"), "guest", "aqil@gmail.com");
		CommandResult commandResult = new LoginController().login("sohsoh", "Soheil@84", true);
		System.out.println(commandResult.message());
		PreGameController preGameController = new PreGameController();
		System.err.println(preGameController.createGame("guest").statusCode());
		System.err.println(preGameController.loadDeck("test").statusCode());
		System.err.println(preGameController.changeTurn().statusCode());
		System.err.println(preGameController.loadDeck("test").statusCode());
		System.err.println(preGameController.startGame().statusCode());

		GameService.getInstance().createController();
		ViewLoader.newScene("game");

		testGameHistory();
	}

	void testGameHistory(){
		GameHistory gameHistory = new GameHistory(
				new User("aqil"),
				new User("sohsoh"),
				new User("aqil"),
				null
		);

		System.out.println(GameHistoryService.getInstance().addGameHistory(
				gameHistory
		).getAdditionalInfo());

		System.out.println(GameHistoryService.getInstance().getNumberOfWins("sohsoh"));

		ArrayList<GameHistory> gameHistories = GameHistoryService.getInstance().getUserHistory("sohsoh");
		System.out.println(gameHistories);

		System.out.println(GameHistoryService.getInstance().getUserRank("aqil"));
	}

	public static void main(String[] args) {
		launch();
	}
}
