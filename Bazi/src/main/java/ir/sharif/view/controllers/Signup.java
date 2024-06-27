package ir.sharif.view.controllers;

import ir.sharif.controller.RegisterController;
import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.model.SecurityQuestion;
import ir.sharif.utils.Random;
import ir.sharif.view.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
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
	TextField passwordConfirmTextField;
	@FXML
	TextField emailTextField;
	@FXML
	TextField nicknameTextField;
	@FXML
	Label errorLabel;
	@FXML
	ComboBox securityQuestionComboBox;
	@FXML
	TextField securityAnswerTextField;

	private RegisterController controller = new RegisterController();

	@FXML
	void initialize() {
			for (String questionString : SecurityQuestion.questions)
				securityQuestionComboBox.getItems().add(questionString);
	}

	public void signupButtonPress(MouseEvent mouseEvent) {
		String username = usernameTextField.getText();
		String password = passwordTextField.getText();
		String nickname = nicknameTextField.getText();
		String password2 = passwordConfirmTextField.getText();
		String email = emailTextField.getText();

		SecurityQuestion question = new SecurityQuestion((String) securityQuestionComboBox.getValue(),
			securityAnswerTextField.getText());
		CommandResult commandResult = controller.register(username, password, password2, question, nickname, email);
		if (commandResult.statusCode() == ResultCode.ACCEPT) {
			ViewLoader.newScene("start");
		} else {
			errorLabel.setText(commandResult.message());
		}
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
