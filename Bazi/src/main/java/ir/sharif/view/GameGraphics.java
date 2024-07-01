package ir.sharif.view;

import ir.sharif.client.TCPClient;
import ir.sharif.controller.GameController;
import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.model.GameState;
import ir.sharif.model.React;
import ir.sharif.model.User;
import ir.sharif.model.game.Card;
import ir.sharif.model.game.Faction;
import ir.sharif.model.game.LeaderType;
import ir.sharif.service.GameService;
import ir.sharif.service.UserService;
import ir.sharif.view.game.CardGraphics;
import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
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
	private ImageView userDeckImages[][] = new ImageView[2][2];
	private Label deadLabel[] = new Label[2];
	private Label deckLabel[] = new Label[2];

	private CardGraphics selectedGradGraphics = null;


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
		System.err.println(pane.getChildren());
		this.pane = pane;
		System.err.println("kir2: " + GameService.getInstance().getController());
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
			if (checkActionOnline()) return;
			controller.passTurn();
		});

		leaderGraphics[0] = (ImageView) getChildrenById("leader0");
		leaderGraphics[1] = (ImageView) getChildrenById("leader1");
		healths[0] = (HBox) getChildrenById("health1");
		healths[1] = (HBox) getChildrenById("health2");

		pane.requestFocus();

		for (int i = 0; i < 2; i++) {
			System.err.println(controller.getUserUserTable(i).getLeader().getName());
			LeaderType leaderType = LeaderType.getLeaderType(controller.getUserUserTable(i).getLeader().getName());
			System.err.println(leaderType.toString());
			leaderGraphics[i].setImage(new Image(getClass().getResourceAsStream("/images/leader/" + leaderType.toString() + ".jpg")));
			deadLabel[i] = (Label) getChildrenById("dead" + String.valueOf(i + 1));
			deckLabel[i] = (Label) getChildrenById("deck" + String.valueOf(i + 1));
			int finalI = i;
			leaderGraphics[i].setOnMouseClicked(event -> {
				if (controller.getMatchTable().getTurn() != finalI) {
					showErrorToast("It's not your turn");
					return;
				}

				if (checkActionOnline())
					return;

				CommandResult result = controller.commanderPowerPlay();
				if (result.statusCode() == ResultCode.ACCEPT) {
					showToast("Commander power played");
				} else {
					showErrorToast(result.message());
				}

				loadModel();
			});
		}


		for (int i = 0; i < 2; i++) {
			Label nickname = (Label) getChildrenById("user" + String.valueOf(i + 1) + "nick");
			nickname.setText(controller.getMatchTable().getUser(i).getNickname());
		}

		if (controller.getGameState() == GameState.OFFLINE_OBSERVER)
			controller.offlineRunner();

		try {
			for (int i = 0; i < 2; i++) {
				Faction faction = controller.getUserUserTable(i).getFaction();
				for (int j = 0; j < 2; j++) {
					userDeckImages[i][j] = (ImageView) getChildrenById("user" + String.valueOf(i + 1) + "im" + String.valueOf(j + 1));
					userDeckImages[i][j].setImage(new Image(getClass().getResourceAsStream("/images/factions/" + getFactionImageName(faction))));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getFactionImageName(Faction faction) {
		if (faction == Faction.MONSTERS) return "monsters.jpg";
		if (faction == Faction.NILFGAARDIAN_EMPIRE) return "nilfgaard.jpg";
		if (faction == Faction.NORTHEN_REALMS) return "realms.jpg";
		if (faction == Faction.SCOIATAEL) return "scoiatael.jpg";
		else return "skellige.jpg";
	}

	public void startGame() {
		for (int i = 0; i < 6; i++) {
			powerLabels[i].setTextFill(Color.WHITE);
		}

		for (int i = 0; i < 2; i++) {
			userPowerLabels[i].setTextFill(Color.WHITE);
		}

		preTurnLoading();
		startReactShowerThread();
	}

	public void startReactShowerThread() {
		Thread thread = new Thread(() -> {
			try {
				TCPClient client = new TCPClient();
				int buffer = client.getAllReacts(0).size();
				while (true) {
					try {
						Thread.sleep(1000);
						ArrayList<React> reacts = client.getAllReacts(buffer);
						for (React react : reacts) {
							Platform.runLater(() -> {
								if (react.getMessage().contains(".png")) {
									ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/images/reacts/" + react.getMessage())));
									imageView.setFitWidth(100);
									imageView.setFitHeight(100);

									pane.getChildren().add(imageView);
									imageView.setY(pane.getHeight() - 100);
									imageView.setX(pane.getWidth() / 2 - 50);
									// move the react from bottom to top and fade it in 6 seconds
									TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(6), imageView);
									translateTransition.setByY(-1000);
									translateTransition.play();

									FadeTransition fadeTransition = new FadeTransition(Duration.seconds(6), imageView);
									fadeTransition.setFromValue(1.0);
									fadeTransition.setToValue(0.0);
									fadeTransition.play();
									showToast(react.getSender() + " reacted with ");
								} else {
									showToast(react.getSender() + " reacted: " + react.getMessage());
								}
							});
						}

						buffer += reacts.size();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();;
			}
		});

		thread.start();
	}

	public void showCurrentUserHand() {
		hand.getChildren().clear();
		ArrayList<Card> handArray = controller.getCurrentUserTable().getHand();
		if (controller.isOnline() && controller.getGameState() != GameState.ONLINE_OBSERVER
			&& controller.getGameState() != GameState.OFFLINE_OBSERVER) {
			handArray = controller.getUserUserTable(controller.getOnlineCurrentUser()).getHand();
		}

		for (Card card : handArray)
			addCardToHBox(card, hand);
	}

	public boolean checkActionOnline() {
		if (controller.isOnline() && controller.getOnlineCurrentUser() != controller.getMatchTable().getTurn()) {
			showErrorToast("It's not your turn");
			return true;
		}

		if (controller.getGameState() == GameState.ONLINE_OBSERVER || controller.getGameState() == GameState.OFFLINE_OBSERVER) {
			showErrorToast("You are observer!");
			return true;
		}

		return false;
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
		Platform.runLater(() -> {
			showToast((winner == -1 ? "draw" : "Player " + (winner + 1) + " won the game"));
			PauseTransition pause = new PauseTransition(Duration.seconds(5));
			pause.setOnFinished(event -> ViewLoader.newScene("main"));
			pause.play();
		});
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
		if (controller.getGameState() == GameState.OFFLINE_OBSERVER) return;
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
		Platform.runLater(() -> {
			showHealths();
			showCurrentUserHand();
			updateCardsInRows();
			updatePowerLabels();
			updateCardCounts();
		});
	}

	private void updateCardCounts() {
		for (int i = 0; i < 2; i++) {
			deckLabel[i].setText(String.valueOf(controller.getUserUserTable(i).getDeck().size()));
			deadLabel[i].setText(String.valueOf(controller.getUserUserTable(i).getDiscardPile().size()));
		}
	}

	private void updateCardsInRows() {
		for (int i = 0; i < 13; i++) {
			ArrayList<Card> cardsInRow = controller.getMatchTable().getCardsByPosition(i);
			ArrayList<Card> cardsInRowGraphics = new ArrayList<>();
			for (Node node : rows[i].getChildren())
				cardsInRowGraphics.add(((CardGraphics) node).getCard());

			if(cardsInRow != null && !cardsInRow.isEmpty()) {
				for (Card card : cardsInRow) {
					if (!cardsInRowGraphics.contains(card)) {
						addCardToHBox(card, rows[i]);
					}
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
		Platform.runLater(() -> {
			loadModel();
			if (controller.isVetoeTurn()) {
				showToast("Player " + (controller.getMatchTable().getTurn() + 1) + "'s turn for veto");
			} else {
				showToast("Player " + (controller.getMatchTable().getTurn() + 1) + "'s turn");
			}
		});
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
						if (checkActionOnline())
							return;

						int rowNumber = Integer.parseInt(row.getId().substring(3));
						CommandResult result = controller.placeCard(card, rowNumber);
						if (result.statusCode() == ResultCode.ACCEPT) {
							removeCardFromHBox(card, hand);
							updatePowerLabels();
							updateCardsInRows();
							showCurrentUserHand();
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
					if (checkActionOnline()) return;
					ArrayList<Card> hand = controller.getCurrentUserTable().getHand();
					CommandResult result = controller.vetoCard(hand.indexOf(cardGraphics.getCard()));
					if (result.statusCode() == ResultCode.ACCEPT) {
						loadModel();
						System.err.println(controller.getOpponentUserTable().getHand());
					} else {
						showErrorToast(result.message());
					}
				} else {
					if (selectedGradGraphics != null) selectedGradGraphics.stopAnimation();
					selectedGradGraphics = cardGraphics;
					selectedGradGraphics.playAnimation();
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