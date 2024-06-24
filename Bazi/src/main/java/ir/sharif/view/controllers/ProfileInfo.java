package ir.sharif.view.controllers;

import ir.sharif.controller.ProfileController;
import ir.sharif.view.ViewLoader;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import org.json.JSONObject;

import javax.swing.text.View;

public class ProfileInfo {
	@FXML
	private Label usernameLabel;
	@FXML
	private Label nicknameLabel;
	@FXML
	private Label maxScoreLabel;
	@FXML
	private Label rankLabelLabel;
	@FXML
	private Label numberOfGamesLabel;
	@FXML
	private Label numberOfWinsLabel;
	@FXML
	private Label numberOfLossesLabel;
	@FXML
	private Label numberOfDrawsLabel;

	public void showInfo(JSONObject jsonObject) {
		usernameLabel.setText("Username: " + jsonObject.getString("username"));
		nicknameLabel.setText("Nickname: " + jsonObject.getString("nickname"));
		maxScoreLabel.setText("Max Score: " + String.valueOf(jsonObject.getInt("maxScore")));
		rankLabelLabel.setText("Rank: " + String.valueOf(jsonObject.getInt("rank")));
		numberOfGamesLabel.setText("Number Of Games: " + String.valueOf(jsonObject.getInt("numberOfGames")));
		numberOfWinsLabel.setText("Number Of Wins: " + String.valueOf(jsonObject.getInt("numberOfWins")));
		numberOfLossesLabel.setText("Number Of Loses: " + String.valueOf(jsonObject.getInt("numberOfLoose")));
		numberOfDrawsLabel.setText("Number Of Draws: " + String.valueOf(jsonObject.getInt("numberOfDraws")));
	}

	@FXML
	public void initialize() {
		JSONObject jsonObject = new JSONObject(new ProfileController().showInfo().message());
		showInfo(jsonObject);
	}

	public void back(MouseEvent mouseEvent) {
		ViewLoader.newScene("profile");
	}
}