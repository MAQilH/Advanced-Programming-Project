package ir.sharif.view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ReactionGUI {
	ReactionGUI() {
		Stage stage = new Stage();
		stage.setTitle("React to game");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/FXML/game-reaction-view.fxml"));
		try {
			Scene scene = new Scene(loader.load());
			scene.getStylesheets().add(getClass().getResource("/CSS/menu.css").toExternalForm());
			stage.setScene(scene);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		stage.show();
	}
}
