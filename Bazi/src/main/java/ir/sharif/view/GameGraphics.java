package ir.sharif.view;

import eu.hansolo.tilesfx.Command;
import ir.sharif.controller.GameController;
import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.model.game.Card;
import ir.sharif.model.game.CardTypes;
import ir.sharif.model.game.Leader;
import ir.sharif.model.game.LeaderType;
import ir.sharif.service.GameService;
import ir.sharif.utils.Random;
import ir.sharif.view.game.CardGraphics;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class GameGraphics {
	private Pane pane;
	private HBox rows[] = new HBox[13];
	private HBox hand;
	private static GameGraphics instance;
	private GameController controller;
	private Button passButton;
	private HBox healths[] = new HBox[2];
	private Label powerLabels[] = new Label[6];
	private Label userPowerLabels[] = new Label[2];
	private ImageView leaderGraphics[] = new ImageView[2];
	private ArrayList<HBox> validRows;

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

		for (int i = 0; i < 13; i++)
			rows[i] = (HBox) getChildrenById("row" + i);

		userPowerLabels[0] = (Label) getChildrenById("userPower0");
		userPowerLabels[1] = (Label) getChildrenById("userPower1");
		for (int i = 0; i < 6; i++)
			powerLabels[i] = (Label) getChildrenById("power" + i);

		hand = (HBox) getChildrenById("hand");
		passButton = (Button) getChildrenById("passButton");

		passButton.setOnMouseClicked(event -> {
			controller.passTurn();
		});

		leaderGraphics[0] = (ImageView) getChildrenById("leader0");
		leaderGraphics[1] = (ImageView) getChildrenById("leader1");
		healths[0] = (HBox) getChildrenById("health1");
		healths[1] = (HBox) getChildrenById("health2");

		pane.requestFocus();
		pane.setOnKeyPressed(e -> {
			if (e.getCode().toString().equals("SPACE")) {
				addCardToHBox(CardTypes.KAMBI.getInstance(), rows[0]);
			} else if (e.getCode().toString().equals("ENTER")) {
				removeNodeWithAnimation(rows[0], rows[0].getChildren().get(Random.getRandomInt(rows[0].getChildren().size())));
			} else if (e.getCode().toString().equals("H")) {
				for (int i = 0; i < 6; i++) {
					powerLabels[i].setTextFill(Color.WHITE);
				}

				for (int i = 0; i < 2; i++) {
					userPowerLabels[i].setTextFill(Color.WHITE);
				}
				preTurnLoading();
			}
		});

		for (int i = 0; i < 2; i++) {
			LeaderType leaderType = LeaderType.getLeaderType(controller.getUserUserTable(i).getLeader().getName());
			leaderGraphics[i].setImage(new Image(getClass().getResourceAsStream("/images/leader/" + leaderType.toString() + ".jpg")));
			leaderGraphics[i].setOnMouseClicked(event -> {
				CommandResult result = controller.commanderPowerPlay();
				if (result.statusCode() == ResultCode.ACCEPT) {
					showToast("Commander power played");
				} else {
					showErrorToast(result.message());
				}

				loadModel();
			});
		}
	}

	public void showCurrentUserHand() {
		hand.getChildren().clear();
		for (Card card : controller.getCurrentUserTable().getHand())
			addCardToHBox(card, hand);
	}

	public void updatePowerLabels() {
		for (int i = 0; i < 6; i++)
			powerLabels[i].setText(String.valueOf(controller.calculateRowPower(i)));

		for (int i = 0; i < 2; i++)
			userPowerLabels[i].setText(String.valueOf(controller.calculateTotalPower(i)));

		for (int i = 0; i < 6; i++) {
			for (Node graphics : rows[i].getChildren()) {
				CardGraphics cardGraphics = (CardGraphics) graphics;
				cardGraphics.updatePower();
			}
		}
	}

	public void showWinner(int winner) {
		showToast((winner == -1 ? "draw" : "Player " + (winner + 1) + " won the game"));
		PauseTransition pause = new PauseTransition(Duration.seconds(3));
		pause.setOnFinished(event -> ViewLoader.newScene("main"));
		pause.play();
	}

	private ImageView loadIcon(String iconName, double size) {
		try {
			InputStream iconStream = getClass().getResourceAsStream("/icons/" + iconName + ".png");
			Image iconImage = new Image(iconStream);
			javafx.scene.image.ImageView iconView = new javafx.scene.image.ImageView(iconImage);
			iconView.setFitHeight(size);
			iconView.setFitWidth(size);
			return iconView;
		} catch (Exception e) {
			System.err.println("Error loading icon: " + iconName);
			e.printStackTrace();
			return null;
		}
	}

	public void showHealths() {
		for (int i = 0; i < 2; i++) {
			int healthCount = controller.getUserUserTable(i).getLife();
			healths[i].getChildren().clear();
			for (int j = 0; j < healthCount; j++) {
				healths[i].getChildren().add(loadIcon("health", 40));
			}
		}
	}

	public void showToastWithClass(String text, boolean isError) {
		Label toastLabel = new Label(text);
		toastLabel.getStyleClass().add(isError ? "error-toast" : "title-label");

		toastLabel.layoutXProperty().bind(pane.widthProperty().subtract(toastLabel.widthProperty()).divide(2));
		toastLabel.layoutYProperty().bind(pane.heightProperty().subtract(toastLabel.heightProperty()).divide(2));
		toastLabel.setOpacity(0.0);
		pane.getChildren().add(toastLabel);

		FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), toastLabel);
		fadeIn.setFromValue(0.0);
		fadeIn.setToValue(1.0);
		fadeIn.setOnFinished((e) -> {
			FadeTransition fadeOut = new FadeTransition(Duration.seconds(2), toastLabel);
			fadeOut.setFromValue(1.0);
			fadeOut.setToValue(0.0);
			fadeOut.setOnFinished((e2) -> pane.getChildren().remove(toastLabel));
			fadeOut.play();
		});

		fadeIn.play();
	}
	public void showToast(String text) {
		showToastWithClass(text, false);
	}

	public void showErrorToast(String text) {
		showToastWithClass(text, true);
	}

	public void loadModel() {
		showHealths();
		showCurrentUserHand();
		updateCardsInRows();
		updatePowerLabels();
	}

	private void updateCardsInRows() {
		for (int i = 0; i < 13; i++) {
			ArrayList<Card> cardsInRow = controller.getMatchTable().getCardsByPosition(i);
			ArrayList<Card> cardsInRowGraphics = new ArrayList<>();
			for (Node node : rows[i].getChildren()) {
				cardsInRowGraphics.add(((CardGraphics) node).getCard());
			}

			for (Card card : cardsInRow) {
				if (!cardsInRowGraphics.contains(card)) {
					addCardToHBox(card, rows[i]);
				}
			}

			for (Card card : cardsInRowGraphics) {
				if (!cardsInRow.contains(card)) {
					removeCardFromHBox(card, rows[i]);
				}
			}
		}
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

	public void preTurnLoading() {
		loadModel();
		if (controller.isVetoeTurn()) {
			showToast("Player " + (controller.getMatchTable().getTurn() + 1) + "'s turn for veto");
		} else {
			showToast("Player " + (controller.getMatchTable().getTurn() + 1) + "'s turn");
		}
	}

	public void setDragAndDropFunctionality(CardGraphics cardGraphics, HBox hbox) {
		if (hbox == hand) {
			cardGraphics.setOnDragDetected(event -> {
				Card card = cardGraphics.getCard();
				if (validRows != null) {
					for (HBox row : validRows) {
						row.setBackground(Background.EMPTY);
					}
					validRows.clear();
				}

				WritableImage snapshot = cardGraphics.snapshot(new SnapshotParameters(), null);
				ImageView dragView = new ImageView(snapshot);
				dragView.setOpacity(0.5);

				Dragboard db = cardGraphics.startDragAndDrop(TransferMode.MOVE);
				ClipboardContent content = new ClipboardContent();
				content.putString(cardGraphics.getCard().toString());
				db.setContent(content);
				db.setDragView(dragView.getImage());

				validRows = getValidRows(card);

				for (HBox row : validRows) {
					row.setBackground(Background.fill(Color.rgb(1, 1, 0, 0.3)));
					row.setOnDragOver(e -> {
						e.acceptTransferModes(TransferMode.MOVE);
						row.setBackground(Background.fill(Color.rgb(1, 1, 0, 0.5)));
					});

					row.setOnDragExited(e -> row.setBackground(Background.fill(Color.rgb(1, 1, 0, 0.3))));

					row.setOnDragDropped(e -> {
						int rowNumber = Integer.parseInt(row.getId().substring(3));
						CommandResult result = controller.placeCard(card, rowNumber);
						if (result.statusCode() == ResultCode.ACCEPT) {
							addCardToHBox(card, row);
							removeCardFromHBox(card, hand);
							updatePowerLabels();
						} else {
							showErrorToast(result.message());
						}
					});
				}
			});

			cardGraphics.setOnDragDone(event -> {
				Card card = cardGraphics.getCard();
				for (HBox row : validRows) {
					row.setBackground(Background.EMPTY);
				}
				validRows.clear();
				updatePowerLabels();

				// remove on Drag over and drag dropped from all rows
				for (int i = 0; i < 13; i++) {
					HBox row = rows[i];
					row.setOnDragExited(null);
					row.setOnDragOver(null);
					row.setOnDragDropped(null);
				}
			});
		}
	}

	private ArrayList<HBox> getValidRows(Card card) {
		ArrayList<Integer> validPositions = card.validPositions();
		ArrayList<HBox> validRows = new ArrayList<>();
		for (int pos : validPositions)
			validRows.add(rows[pos]);

		for (int e : validPositions) {
			System.out.print(e + " ");
		}

		System.out.println();
		return validRows;
	}

	public void setOnMouseClickFunctionality(CardGraphics cardGraphics, HBox hbox) {
		cardGraphics.setOnMouseClicked(event -> {
				if (hbox == hand && controller.isVetoeTurn()) {
					ArrayList<Card> hand = controller.getCurrentUserTable().getHand();
					CommandResult result = controller.vetoCard(hand.indexOf(cardGraphics.getCard()));
					if (result.statusCode() == ResultCode.ACCEPT) {
						loadModel();
						System.err.println(controller.getOpponentUserTable().getHand());
					} else {
						showErrorToast(result.message());
					}
				}
			}
		);
	}

	public void setOnMouseHoverFunctionality(CardGraphics cardGraphics, HBox hBox) {
		cardGraphics.setOnMouseEntered(event -> {
			cardGraphics.setScaleX(1.2);
			cardGraphics.setScaleY(1.2);
		});

		cardGraphics.setOnMouseExited(event -> {
			cardGraphics.setScaleX(1);
			cardGraphics.setScaleY(1);
		});
	}

	public void addCardToHBox(Card card, HBox hbox) {
		CardGraphics cardGraphics = new CardGraphics(card, hbox.getHeight());
		setDragAndDropFunctionality(cardGraphics, hbox);
		setOnMouseClickFunctionality(cardGraphics, hbox);
		setOnMouseHoverFunctionality(cardGraphics, hbox);

		hbox.getChildren().add(cardGraphics);
		updatePowerLabels();
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
