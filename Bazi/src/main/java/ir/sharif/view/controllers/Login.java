package ir.sharif.view.controllers;

import ir.sharif.controller.LoginController;
import ir.sharif.controller.ResetPasswordController;
import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.model.User;
import ir.sharif.service.UserService;
import ir.sharif.view.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class Login {
	@FXML
	TextField usernameTextField;
	@FXML
	TextField passwordTextField;
	@FXML
	Label errorLabel;

	private LoginController loginController = new LoginController();

	public void loginButtonPress(MouseEvent mouseEvent) {
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();

		CommandResult result = loginController.login(username, password, true);
		if (result.statusCode() == ResultCode.ACCEPT) {
			ViewLoader.newScene("main");
		} else {
			errorLabel.setText(result.message());
		}
	}

	public void backButtonPress(MouseEvent mouseEvent) {
		ViewLoader.newScene("start");
	}

	public void resetPassword(MouseEvent mouseEvent) {
		CommandResult result = ResetPasswordController.getInstance().setUser(usernameTextField.getText());
		if (result.statusCode() == ResultCode.FAILED) {
			errorLabel.setText(result.message());
			return;
		}

		ViewLoader.newScene("reset-password");
	}
}
