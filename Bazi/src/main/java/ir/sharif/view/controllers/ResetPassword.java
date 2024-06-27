package ir.sharif.view.controllers;

import ir.sharif.controller.ResetPasswordController;
import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.view.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ResetPassword {
	@FXML
	private Label errorLabel;
	@FXML
	private Label questionLabel;
	@FXML
	private PasswordField newPassword;
	@FXML
	private PasswordField newPasswordConfirm;
	@FXML
	private TextField answerTextField;

	@FXML
	public void initialize() {
		questionLabel.setText(ResetPasswordController.getInstance().getQuestion());
	}

	public void resetPassword(MouseEvent mouseEvent) {
		ResetPasswordController resetPasswordController = ResetPasswordController.getInstance();
		String answer = answerTextField.getText();
		String password = newPassword.getText();
		String passwordConfirm = newPasswordConfirm.getText();

		CommandResult result = resetPasswordController.resetPassword(answer, password, passwordConfirm);
		if (result.statusCode() == ResultCode.ACCEPT) {
			errorLabel.setText("Password changed successfully");
		} else {
			errorLabel.setText(result.message());
		}
	}

	public void backButtonPress(MouseEvent mouseEvent) {
		ViewLoader.newScene("login");
	}
}
