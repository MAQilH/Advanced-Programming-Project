package ir.sharif.view;

import ir.sharif.controller.GameController;
import ir.sharif.model.game.Card;
import ir.sharif.model.game.CardTypes;
import ir.sharif.service.GameService;
import ir.sharif.utils.Random;
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
	private HBox hand;
	private static GameGraphics instance;
	private GameController controller;

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
		controller = GameService.getInstance().getController();
		for (int i = 0; i < 6; i++) {
			rows[i] = (HBox) getChildrenById("row" + i);
		}

		hand = (HBox) getChildrenById("hand");
		pane.requestFocus();
		pane.setOnKeyPressed(e -> {
			if (e.getCode().toString().equals("SPACE")) {
				addCardToHBox(CardTypes.KAMBI.getInstance(), rows[0]);
			} else if (e.getCode().toString().equals("ENTER")) {
				removeNodeWithAnimation(rows[0], rows[0].getChildren().get(Random.getRandomInt(rows[0].getChildren().size())));
			}
		});

		showCurrentUserHand();
	}

	public void showCurrentUserHand() {
		hand.getChildren().clear();
		for (Card card : controller.getCurrentUserTable().getHand())
			addCardToHBox(card, hand);
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
			translateTransition.setByX(-(node.getBoundsInParent().getWidth() + hbox.getSpacing() / 2));
			translateTransition.play();
		}

		fadeTransition.play();
	}

	public void removeNodeWithAnimation(HBox hbox, Node node) {
		double nodeWidth = 0 ;

		FadeTransition fadeTransition = new FadeTransition(Duration.millis(500), node);
		fadeTransition.setFromValue(1.0);
		fadeTransition.setToValue(0.0);

		TranslateTransition[] transitions = new TranslateTransition[hbox.getChildren().size()];
		for (int i = 0; i < hbox.getChildren().size(); i++) {
			if (hbox.getChildren().get(i) != node) {
				transitions[i] = new TranslateTransition(Duration.millis(500), hbox.getChildren().get(i));
				transitions[i].setByX(i < hbox.getChildren().indexOf(node) ? nodeWidth : -nodeWidth);
			}
		}

		for (TranslateTransition transition : transitions) {
			if (transition != null) {
				transition.play();
			}
		}

		fadeTransition.setOnFinished(event -> hbox.getChildren().remove(node));
		fadeTransition.play();
	}

	public void addCardToHBox(Card card, HBox hbox) {
		CardGraphics cardGraphics = new CardGraphics(card, hbox.getHeight());
		hbox.getChildren().add(cardGraphics);
	}

	public void removeCardFromHBox(Card card, HBox hbox) {
		for (Node node : hbox.getChildren()) {
			if (((CardGraphics) node).getCard().equals(card)) {
				removeNodeWithAnimation(hbox, node);
				break;
			}
		}
	}
}
