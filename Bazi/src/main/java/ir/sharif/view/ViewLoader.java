package ir.sharif.view;

import com.almasb.fxgl.core.View;
import ir.sharif.utils.ConstantsLoader;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class ViewLoader {
	static Stage stage;

	public static void setStage(Stage stage) {
		ViewLoader.stage = stage;
	}

	public static Stage getStage() {
		return stage;
	}

	public static void newScene(String menuName) {
		FXMLLoader fxmlLoader = new FXMLLoader(ViewLoader.class.getResource("/FXML/" + menuName + "-view.fxml"));
		Scene scene = null;
		try {
			scene = new Scene(fxmlLoader.load());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		scene.getStylesheets().add(ViewLoader.class.getResource("/CSS/menu.css").toExternalForm());
		System.out.println(scene.getStylesheets());
		Pane pane = (Pane) scene.getRoot();

		stage.setScene(scene);
		pane.setBackground(new Background(createBackgroundImage("background.jpg")));
		stage.show();
		centerStage();
	}

	public static void centerStage() {
		stage.setX(Screen.getPrimary().getBounds().getWidth() / 2 - stage.getWidth() / 2);
		stage.setY(Screen.getPrimary().getBounds().getHeight() / 2 - stage.getHeight() / 2);
	}

	public static BackgroundImage createBackgroundImage(String name) {
		Image image = new Image(ViewLoader.class.getResource("/images/" + name).toExternalForm(),
			stage.getScene().getWidth(), stage.getScene().getHeight(),
			false, false);

		BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
			BackgroundRepeat.NO_REPEAT,
			BackgroundPosition.DEFAULT,
			BackgroundSize.DEFAULT);

		return backgroundImage;
	}
}
