package ir.sharif.view.controllers;

import com.almasb.fxgl.core.View;
import ir.sharif.view.ViewLoader;
import javafx.scene.input.MouseEvent;

public class Start {
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
