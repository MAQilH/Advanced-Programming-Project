package ir.sharif.view.controllers;

import eu.hansolo.tilesfx.Command;
import ir.sharif.controller.PreGameController;
import ir.sharif.enums.ResultCode;
import ir.sharif.model.CommandResult;
import ir.sharif.model.game.*;
import ir.sharif.view.ViewLoader;
import ir.sharif.view.game.CardGraphics;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;

public class Pregame {
	@FXML
	private ComboBox factionList;
	@FXML
	private ComboBox leaderList;
	@FXML
	private ScrollPane cardsPane;
	@FXML
	private Label errorLabel;
	@FXML
	private TextField deckName;
	@FXML
	private Button nextButton;

	private PreGameController pregameController = new PreGameController();
	private int turn = 0;

	@FXML
	public void initialize() {
		for (Faction faction : Faction.values())
			factionList.getItems().add(faction.toString());

		factionList.valueProperty().addListener((obs, oldVal, newVal) -> {
			updateLeaders();
		});

		factionList.valueProperty().addListener((obs, oldVal, newVal) -> {
			updateCardLists();
		});

		leaderList.setMinWidth(300);
		factionList.setMinWidth(300);

		pregameController.createGame("guest");

	}

	private void updateLeaders() {
		if (factionList.getValue() == null || factionList.getValue().toString().isEmpty())
			return;

		Faction faction = Faction.findFaction(factionList.getValue().toString());
		if (faction == null)
			return;

		for (LeaderType leaderType : LeaderType.values()) {
			if (leaderType.getFaction() == faction) {
				leaderList.getItems().add(leaderType.toString());
			}
		}
	}

	public void addButtonClickListener(Label countLabel) {
		int value = Integer.parseInt(countLabel.getText().split("/")[0]);
		int maxValue = Integer.parseInt(countLabel.getText().split("/")[1]);

		if (value == maxValue)
			return;
		countLabel.setText(value + 1 + "/" + maxValue);
	}

	public void removeButtonClickListener(Label countLabel) {
		int value = Integer.parseInt(countLabel.getText().split("/")[0]);
		if (value == 0)
			return;

		int maxValue = Integer.parseInt(countLabel.getText().split("/")[1]);
		countLabel.setText(value - 1 + "/" + maxValue);
	}

	private VBox createCardAdder(CardTypes type) {
		VBox vBox = new VBox();
		vBox.setSpacing(10);
		vBox.getChildren().add(new CardGraphics(type.getInstance(), 1));

		HBox hBox = new HBox();
		hBox.setAlignment(Pos.CENTER);
		hBox.setSpacing(10);
		Label countLabel = new Label("0/" + type.getInstance().getNoOfCards());

		ImageView addButton = new ImageView();
		addButton.setFitWidth(40);
		addButton.setFitHeight(40);
		addButton.setImage(new Image(getClass().getResource("/icons/add.png").toExternalForm()));
		addButton.setOnMouseClicked(e -> addButtonClickListener(countLabel));

		ImageView removeButton = new ImageView();
		removeButton.setFitWidth(40);
		removeButton.setFitHeight(40);
		removeButton.setImage(new Image(getClass().getResource("/icons/remove.png").toExternalForm()));
		removeButton.setOnMouseClicked(e -> removeButtonClickListener(countLabel));

		hBox.getChildren().addAll(removeButton, countLabel, addButton);
		vBox.getChildren().add(hBox);
		return vBox;
	}

	private void updateCardLists() {
		VBox allCards = new VBox();
		ArrayList<CardTypes> factionCards = new ArrayList<>();

		HBox lastRow = null;
		for (CardTypes cardTypes : CardTypes.values()) {
			if (lastRow == null || lastRow.getChildren().size() == 3) {
				lastRow = new HBox();
				lastRow.setSpacing(10);
				lastRow.setAlignment(Pos.CENTER);
				allCards.getChildren().add(lastRow);
			}

			if (cardTypes.getInstance().getFaction() == null || cardTypes.getInstance().getFaction() == Faction.findFaction(factionList.getValue().toString())) {
				lastRow.getChildren().add(createCardAdder(cardTypes));
			}
		}

		allCards.setPrefWidth(cardsPane.getWidth());
		allCards.setPrefHeight(allCards.getChildren().size()
			* allCards.getChildren().get(0).prefHeight(cardsPane.getWidth()));

		allCards.setSpacing(10);
		allCards.setAlignment(Pos.CENTER);
		cardsPane.setContent(allCards);
	}

	DeckInfo getDeckInfo() {
		if (factionList.getValue() == null || leaderList.getValue() == null)
			return null;
		DeckInfo deckInfo = new DeckInfo();
		deckInfo.setFaction(Faction.findFaction(factionList.getValue().toString()));
		deckInfo.setLeader(LeaderType.valueOf(leaderList.getValue().toString()));

		VBox vBox = (VBox) cardsPane.getContent();
		for (int i = 0; i < vBox.getChildren().size(); i++) {
			HBox hBox = (HBox) vBox.getChildren().get(i);
			for (Node node : hBox.getChildren()) {
				VBox cardVBox = (VBox) node;
				String cardName = ((CardGraphics) cardVBox.getChildren().get(0)).getCard().getName();
				HBox detailsHBox = (HBox) cardVBox.getChildren().get(1);
				Label countLabel = (Label) detailsHBox.getChildren().get(1);
				int count = Integer.parseInt(countLabel.getText().split("/")[0]);
				for (int j = 0; j < count; j++) {
					deckInfo.addCard(CardTypes.getCardType(cardName));
				}
			}
		}
		return deckInfo;
	}

	public void loadUsingName(MouseEvent mouseEvent) {
		CommandResult result = pregameController.loadDeck(deckName.getText());
		if (result.statusCode() == ResultCode.ACCEPT)
			updateDeckUsingController();
		errorLabel.setText(result.message());
	}

	public void updateDeckUsingController() {
		DeckInfo deckInfo = pregameController.getDeck();
		factionList.setValue(deckInfo.getFaction().toString());
		leaderList.setValue(deckInfo.getLeader().toString());
		updateCardLists();
		updateCardGraphicsWithDeckInfo(deckInfo);
	}

	public void saveUsingName(MouseEvent mouseEvent) {
		DeckInfo deckInfo = getDeckInfo();
		pregameController.setDeck(deckInfo);
		CommandResult result = pregameController.saveDeck(deckName.getText());
		errorLabel.setText(result.message());
	}

	public void loadUsingPath(MouseEvent mouseEvent) {
		Path path = null;
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Open Deck File");
		fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Deck Files",
			"*.deck"));

		File file = fileChooser.showOpenDialog(ViewLoader.getStage());
		if (file != null)
			path = file.toPath();

		CommandResult result = pregameController.loadDeck(path);
		if (result.statusCode() == ResultCode.ACCEPT) updateDeckUsingController();
		errorLabel.setText(result.message());
	}

	private void updateCardGraphicsWithDeckInfo(DeckInfo deckInfo) {
		for (CardTypes cardTypes : deckInfo.getStorage()) {
			VBox vBox = (VBox) cardsPane.getContent();
			for (int i = 0; i < vBox.getChildren().size(); i++) {
				HBox hBox = (HBox) vBox.getChildren().get(i);
				for (Node node : hBox.getChildren()) {
					VBox cardVBox = (VBox) node;
					if (((CardGraphics) cardVBox.getChildren().get(0)).getCard().getName().equals(cardTypes.getInstance().getName())) {
						HBox detailsHBox = (HBox) cardVBox.getChildren().get(1);
						Label countLabel = (Label) detailsHBox.getChildren().get(1);
						int count = Integer.parseInt(countLabel.getText().split("/")[0]);
						countLabel.setText(count + 1 + "/" + cardTypes.getInstance().getNoOfCards());
					}
				}
			}
		}
	}

	public void saveUsingPath(MouseEvent mouseEvent) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Save Deck File");
		fileChooser.setSelectedExtensionFilter(new FileChooser.ExtensionFilter("Deck Files",
			"*.deck"));

		File file = fileChooser.showSaveDialog(ViewLoader.getStage());
		if (file != null) {
			pregameController.setDeck(getDeckInfo());
			CommandResult result = pregameController.saveDeck(file.toPath());
			errorLabel.setText(result.message());
		}
	}

	public void next() {
		if (turn == 0) {
			CommandResult result = pregameController.setDeck(getDeckInfo());
			if (result.statusCode() == ResultCode.ACCEPT) {
				turn = 1;
				errorLabel.setText("");
			} else {
				errorLabel.setText(result.message());
				return;
			}

			System.err.println(getDeckInfo());
			result = pregameController.changeTurn();
			if (result.statusCode() == ResultCode.ACCEPT) {
				nextButton.setText("Start");
				turn++;
			} else {
				errorLabel.setText(result.message());
			}

			return;
		}

		System.err.println(getDeckInfo());

		CommandResult result = pregameController.setDeck(getDeckInfo());
		if (result.statusCode() == ResultCode.ACCEPT) {
			errorLabel.setText("");
		} else {
			errorLabel.setText(result.message());
			return;
		}

		result = pregameController.startGame();
		if (result.statusCode() == ResultCode.ACCEPT) {
			errorLabel.setText("game started successfully");
			ViewLoader.newScene("game");
		} else {
			errorLabel.setText(result.message());
		}
	}
}
