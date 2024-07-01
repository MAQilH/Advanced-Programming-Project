package ir.sharif.view.controllers;

import ir.sharif.client.TCPClient;
import ir.sharif.model.Pair;
import ir.sharif.service.UserService;
import ir.sharif.view.GameGraphics;
import ir.sharif.view.game.CardGraphics;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameReaction {
	@FXML
	TextField textField;

	@FXML
	ImageView kian;
	@FXML
	ImageView aqil;
	@FXML
	ImageView soheil;
	@FXML
	ImageView ta;

	public Pair<Double, Double> getPositionPair() {
		Pair<Double, Double> pos = null;
		if (GameGraphics.getInstance().getSelectedCardGraphics() != null) {
			CardGraphics graphics = GameGraphics.getInstance().getSelectedCardGraphics();
			Point2D pointInScene = graphics.localToScene(graphics.getBoundsInLocal().getCenterX(), graphics.getBoundsInLocal().getCenterY());
			pos = new Pair<>(pointInScene.getX() - graphics.getScene().getX(), pointInScene.getY() - graphics.getScene().getY());
		}

		if (pos == null)
			System.err.println("tf: pos is null");
		else
			System.err.println("tf: " + pos.getFirst() + " " + pos.getSecond());

		return pos;
	}

	@FXML
	public void initialize() {
		ta.setImage(new Image(getClass().getResourceAsStream("/images/reacts/ta.png")));
		kian.setImage(new Image(getClass().getResourceAsStream("/images/reacts/kian.png")));
		soheil.setImage(new Image(getClass().getResourceAsStream("/images/reacts/soheil.png")));
		aqil.setImage(new Image(getClass().getResourceAsStream("/images/reacts/aqil.png")));

		ta.setOnMouseClicked(event -> {
			TCPClient client = new TCPClient();
			client.sendReaction(UserService.getInstance().getCurrentUser().getNickname(), "ta.png", getPositionPair());
		});

		kian.setOnMouseClicked(event -> {
			TCPClient client = new TCPClient();
			client.sendReaction(UserService.getInstance().getCurrentUser().getNickname(), "kian.png", getPositionPair());
		});

		soheil.setOnMouseClicked(event -> {
			TCPClient client = new TCPClient();
			client.sendReaction(UserService.getInstance().getCurrentUser().getNickname(), "soheil.png", getPositionPair());
		});

		aqil.setOnMouseClicked(event -> {
			TCPClient client = new TCPClient();
			client.sendReaction(UserService.getInstance().getCurrentUser().getNickname(), "aqil.png", getPositionPair());
		});


		textField.setOnKeyPressed(event -> {
			if (event.getCode().toString().equals("ENTER")) {
				TCPClient client = new TCPClient();
				client.sendReaction(UserService.getInstance().getCurrentUser().getNickname(), textField.getText(), getPositionPair());
			}
		});
	}
}
