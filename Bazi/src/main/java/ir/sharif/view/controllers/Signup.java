package ir.sharif.view.controllers;

import ir.sharif.utils.Random;
import ir.sharif.view.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;

import javafx.scene.input.MouseEvent;
import org.w3c.dom.Text;

public class Signup {
	@FXML
	TextField usernameTextField;
	@FXML
	TextField passwordTextField;
	@FXML
	TextField emailTextField;

	public void signupButtonPress(MouseEvent mouseEvent) {
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();
		String email = emailTextField.getText();

		System.err.println("Signup button pressed" + " " + username + " " + password + " " + email);
	}

	public void backButtonPress(MouseEvent mouseEvent) {
		ViewLoader.newScene("start");
	}

	public void randomPasswordPress(MouseEvent mouseEvent) {
		String password = Random.getRandomPassword();
		passwordTextField.setText(password);

		StringSelection stringSelection = new StringSelection(password);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
	}
}
