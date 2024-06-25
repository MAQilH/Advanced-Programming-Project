package ir.sharif.view;

import ir.sharif.model.game.Card;
import ir.sharif.model.game.CardTypes;
import ir.sharif.view.game.CardGraphics;
import javafx.animation.FadeTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class GameGraphics {
	private Pane pane;
	private HBox rows[] = new HBox[6];
	private HBox deck;
	private static GameGraphics instance;

	private GameGraphics() {}

	public static GameGraphics getInstance() {
		if (instance == null) {
			instance = new GameGraphics();
		}
		return instance;
	}

	private Node getChildrenById(String id) {
		for (Node node : pane.getChildren()) {
			if (node.getId() != null && node.getId().equals(id)) {
				return node;
			}
		}
		return null;
	}

	public void initialize(Pane pane) {
		this.pane = pane;
		for (int i = 0; i < 6; i++) {
			rows[i] = (HBox) getChildrenById("row" + i);
		}

		deck = (HBox) getChildrenById("deck");
		pane.requestFocus();
		pane.setOnKeyPressed(e -> {
			if (e.getCode().toString().equals("SPACE")) {
				addCardToHBox(CardTypes.KAMBI.getInstance(), rows[0]);
			}
		});
	}

	public void addNodeWithAnimation(HBox hbox, Node node) {
		node.setOpacity(0.0);
		hbox.getChildren().add(node);

		FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), node);
		fadeTransition.setFromValue(0.0);
		fadeTransition.setToValue(1.0);

		for (int i = 0; i < hbox.getChildren().size() - 1; i++) {
			Node nextNode = hbox.getChildren().get(i);
			TranslateTransition translateTransition = new TranslateTransition(Duration.millis(500), nextNode);
			translateTransition.setByX(-node.getBoundsInParent().getWidth());

			translateTransition.play();
		}

		fadeTransition.play();
	}

	public void addCardToHBox(Card card, HBox hbox) {
		CardGraphics cardGraphics = new CardGraphics(card, hbox.getHeight());
		addNodeWithAnimation(hbox, cardGraphics);
		System.err.println(hbox);;
	}
}
