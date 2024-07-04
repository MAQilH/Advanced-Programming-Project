package ir.sharif.view.controllers;

import ir.sharif.client.TCPClient;
import ir.sharif.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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
		}
	}

	@FXML
	private void join() {
		errorLabel.setText("join: " + trId.getText());
	}
}
