package ir.sharif.view.controllers;

import javafx.fxml.FXML;
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

	@FXML
	public void initialize() {
		ta.setImage(new Image(getClass().getResourceAsStream("/images/reacts/ta.png")));
		kian.setImage(new Image(getClass().getResourceAsStream("/images/reacts/kian.png")));
		soheil.setImage(new Image(getClass().getResourceAsStream("/images/reacts/soheil.png")));
		aqil.setImage(new Image(getClass().getResourceAsStream("/images/reacts/aqil.png")));
	}
}
