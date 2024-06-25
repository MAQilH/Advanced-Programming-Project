package ir.sharif.view;

import ir.sharif.model.game.CardTypes;
import ir.sharif.view.game.CardGraphics;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

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
			rows[i].setBackground(Background.fill(Color.BLACK));
		}

		deck = (HBox) getChildrenById("deck");
	}
}
