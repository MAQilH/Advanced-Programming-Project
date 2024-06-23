package ir.sharif.view;

import ir.sharif.utils.ConstantsLoader;
import ir.sharif.view.gui.terminal.TerminalGUI;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GUIApplication extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception {
		ViewLoader.setStage(primaryStage);
		primaryStage.setTitle(ConstantsLoader.getInstance().getProperty("app.title"));
		primaryStage.initStyle(StageStyle.UNDECORATED);
		ViewLoader.newScene("start");
	}

	public static void main(String[] args) {
		launch();
	}
}
