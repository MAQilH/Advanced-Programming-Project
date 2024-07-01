package ir.sharif.view.controllers;

import ir.sharif.model.server.TwoFactorAuth;
import ir.sharif.service.UserService;
import ir.sharif.view.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;


public class Verification {
	@FXML
	private Button verifyButton;
	@FXML
	private TextField verificationCode;
	@FXML
	private Label errorLabel;

	private String code = null;
	public void verifyLater(MouseEvent mouseEvent) {
		ViewLoader.newScene("main");
	}

	public void sendVerificationEmail(MouseEvent mouseEvent) {
		verificationCode.setOpacity(1);

		if (code == null) {
			try {
				code = TwoFactorAuth.getInstance().sendAuthCode(UserService.getInstance().getCurrentUser().getEmail());
				verifyButton.setText("Verify");
			} catch (Exception e) {
				errorLabel.setText("Failed to send verification email");
			}
		} else {
			if (verificationCode.getText().equals(code)) {
				ViewLoader.newScene("main");
			} else {
				errorLabel.setText("Invalid code");
			}
		}
	}
}
