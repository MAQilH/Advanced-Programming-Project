package ir.sharif.view.controllers;

import com.almasb.fxgl.core.View;
import ir.sharif.model.User;
import ir.sharif.service.UserService;
import ir.sharif.view.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Login {
	@FXML
	TextField usernameTextField;
	@FXML
	TextField passwordTextField;

	public void loginButtonPress(MouseEvent mouseEvent) {
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();

		System.err.println("Login button pressed" + " " + username + " " + password);
		// TODO: remove this
		UserService.getInstance().setCurrentUser(new User("guest", "guest", "guest", "sohsoh84@gmail.com", null));
		ViewLoader.newScene("main");
	}

	public void backButtonPress(MouseEvent mouseEvent) {
		ViewLoader.newScene("start");
	}
}
