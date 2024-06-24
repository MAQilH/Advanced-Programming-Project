package ir.sharif.view.controllers;

import ir.sharif.controller.ProfileController;
import ir.sharif.model.CommandResult;
import ir.sharif.view.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class Profile {
	@FXML
	TextField usernameTextField;
	@FXML
	TextField emailTextField;
	@FXML
	TextField nicknameTextField;
	@FXML
	TextField currentPasswordTextField;
	@FXML
	TextField newPasswordTextField;
	@FXML
	Label errorLabel;

	private final ProfileController controller = new ProfileController();

	public void changeUsername(MouseEvent mouseEvent) {
		CommandResult result = controller.changeUsername(usernameTextField.getText());
		errorLabel.setText(result.message());
	}

	public void changePassword(MouseEvent mouseEvent) {
		CommandResult result = controller.changePassword(currentPasswordTextField.getText(), newPasswordTextField.getText());
		errorLabel.setText(result.message());
	}

	public void changeNickname(MouseEvent mouseEvent) {
		CommandResult result = controller.changeNickname(nicknameTextField.getText());
		errorLabel.setText(result.message());
	}

	public void changeEmail(MouseEvent mouseEvent) {
		CommandResult result = controller.changeEmail(emailTextField.getText());
		errorLabel.setText(result.message());
	}

	public void back(MouseEvent mouseEvent) {
		ViewLoader.newScene("main");
	}

	public void showInfo(MouseEvent mouseEvent) {
		ViewLoader.newScene("profile-info");
	}

	public void gameHistory(MouseEvent mouseEvent) {
		ViewLoader.newScene("game-history");
	}
}
