package ir.sharif.view.controllers;

import ir.sharif.client.TCPClient;
import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.service.UserService;
import ir.sharif.view.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class TournamentLobby {
	@FXML
	Label errorLabel;
	@FXML
	TextField trId;

	@FXML
	private void create() {
		TCPClient client = new TCPClient();
		String token = client.createTournament(UserService.getInstance().getCurrentUser());
		if (token == null)
			errorLabel.setText("tournament failed");
		else {
			errorLabel.setText("Tournament created with id: " + token);
			System.err.println("token: " + token);
			Clipboard clipboard = Clipboard.getSystemClipboard();
			ClipboardContent content = new ClipboardContent();
			content.putString(token);
			clipboard.setContent(content);
		}
	}

	@FXML
	private void join() {
		String token = trId.getText();
		TCPClient client = new TCPClient();
		CommandResult result = client.joinToTournament(UserService.getInstance().getCurrentUser(), token);
		if (result.statusCode() == ResultCode.ACCEPT) {
			UserService.getInstance().setTournamentToken(token);
			ViewLoader.setMenuName("tr-main");
			ViewLoader.newScene("tr-main");
		} else {
			errorLabel.setText(result.message());
		}
	}
}
