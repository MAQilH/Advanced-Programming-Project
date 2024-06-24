package ir.sharif.view;

import ir.sharif.model.game.CardTypes;
import ir.sharif.utils.ConstantsLoader;
import ir.sharif.view.game.CardGraphics;
import ir.sharif.view.gui.terminal.TerminalGUI;
import javafx.application.Application;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GUIApplication extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		ViewLoader.setStage(primaryStage);
		primaryStage.setTitle(ConstantsLoader.getInstance().getProperty("app.title"));
		primaryStage.initStyle(StageStyle.UNDECORATED);
		ViewLoader.newScene("start");
		Pane pane = (Pane) (ViewLoader.getStage().getScene().getRoot());
		//pane.getChildren().add(new CardGraphics(CardTypes.MENNO_COEHOORN_2.getInstance(), 1));
		// new TerminalGUI(null);
	}

	public static void main(String[] args) {
		launch();
	}
}
