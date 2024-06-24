package ir.sharif.view.controllers;

import ir.sharif.model.game.*;
import ir.sharif.view.ViewLoader;
import ir.sharif.view.game.CardGraphics;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import javax.swing.text.View;
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
	public void initialize() {
		for (Faction faction : Faction.values())
			factionList.getItems().add(faction.getName());

		factionList.valueProperty().addListener((obs, oldVal, newVal) -> {
			updateLeaders();
		});

		leaderList.valueProperty().addListener((obs, oldVal, newVal) -> {
			updateCardLists();
		});

		leaderList.setMinWidth(300);
		factionList.setMinWidth(300);

	}

	private void updateLeaders() {
		if (factionList.getValue() == null || factionList.getValue().toString().isEmpty())
			return;

		Faction faction = Faction.findFaction(factionList.getValue().toString());
		if (faction == null)
			return;

		for (LeaderType leaderType : LeaderType.values()) {
			if (leaderType.getFaction() == faction) {
				leaderList.getItems().add(leaderType.getName());
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

			if (cardTypes.getInstance().getFaction() == Faction.findFaction(factionList.getValue().toString())) {
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
		DeckInfo deckInfo = new DeckInfo();
		deckInfo.setFaction(Faction.findFaction(factionList.getValue().toString()));
		deckInfo.setLeader(LeaderType.valueOf(leaderList.getValue().toString()));

		for (CardTypes cardTypes : CardTypes.values()) {
			VBox vBox = (VBox) cardsPane.getContent();
			for (int i = 0; i < vBox.getChildren().size(); i++) {
				HBox hBox = (HBox) vBox.getChildren().get(i);
				Label countLabel = (Label) hBox.getChildren().get(1);
				int count = Integer.parseInt(countLabel.getText().split("/")[0]);
				for (int j = 0; j < count; j++)
					deckInfo.addCard(cardTypes);
			}
		}

		return deckInfo;
	}

	public void loadUsingName(MouseEvent mouseEvent) {
		DeckInfo deckInfo = getDeckInfo();

	}

	public void saveUsingName(MouseEvent mouseEvent) {
		DeckInfo deckInfo = getDeckInfo();

	}

	public void loadUsingPath(MouseEvent mouseEvent) {
		// get a path using a file chooser
		Path path = null;
	}
}
