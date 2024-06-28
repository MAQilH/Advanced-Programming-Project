package ir.sharif.view.game;

import ir.sharif.model.game.*;
import ir.sharif.model.game.abilities.Berserker;
import ir.sharif.utils.ConstantsLoader;
import ir.sharif.utils.Random;
import ir.sharif.view.ViewLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.InputStream;

public class CardGraphics extends Pane {
	private final Card card;
	private final double width;
	private final double height;
	private final int iconSize = 50;

	private VBox icons;
	private Label nameLabel;
	private Label powerLabel;
	private double scale = 1.0;

	public CardGraphics(Card card, double height) {
		this.card = card;
		this.width = height / Double.parseDouble(ConstantsLoader.getInstance().getProperty("card.scale"));
		this.height = height;
		double initWidth = Double.parseDouble(ConstantsLoader.getInstance().getProperty("card.width"));
		scale = width / initWidth;

		this.setMinWidth(width);
		this.setMaxWidth(width);
		this.setMinHeight(height);
		this.setMaxHeight(height);

		init();
	}

	private void init() {
		powerLabel = new Label();
		powerLabel.setText(String.valueOf(card.getPower()));

		icons = new VBox();
		icons.getStyleClass().add("icons"); // Add the style class to the HBox
		icons.getChildren().add(powerLabel);

		ImageView power;
		boolean isHero = false;
		if (card.isHero()) { // TODO: fix this
			power = new ImageView(this.getClass().getResource("/icons/hero.png").toExternalForm());
			isHero = true;
		} else {
			power = new ImageView(this.getClass().getResource("/icons/Normal.png").toExternalForm());
		}

		int size = (int) (height / (isHero ? 2 : 3));
		power.setFitHeight(size);
		power.setFitWidth(size);

		StackPane pane = new StackPane();
		pane.getChildren().add(power);
		pane.setMinHeight(size);
		pane.setMaxHeight(size);
		pane.setMinWidth(size);
		pane.setMaxWidth(size);
		power.setLayoutX(0);
		power.setLayoutY(0);
		pane.getChildren().add(powerLabel);
		powerLabel.setLayoutX(0);
		powerLabel.setLayoutY(0);
		powerLabel.setPadding(new Insets(size / (isHero ? 6 : 4), 0, 0, size / (isHero ? 6 : 4)));
		pane.setAlignment(Pos.TOP_LEFT);

		icons.getChildren().add(pane);

		if (card.getAbility() != null) {
			icons.getChildren().add(loadIcon(card.getAbility().getClass().getSimpleName()));
		}

		nameLabel = new Label(card.getName());
		nameLabel.getStyleClass().add("name"); // Add the style class to the Label
		nameLabel.setTextFill(Color.web("B8860B")); // Set the color of the name label to B8860B

		String faction = (card.getFaction() == null ? "neutral" : card.getFaction().toString().toLowerCase());
		if (faction.equals("nilfgaardian_empire")) faction = "nilfgaard";
		if (faction.equals("northen_realms")) faction = "realms";
		String fileName = "/images/sm/" + faction + "_" + CardTypes.getCardType(card.getName()).toString().toLowerCase() + ".jpg";
		try {
			setBackground(fileName);
		} catch (Exception e) {
			System.err.println("Error loading image: " + fileName);
			setBackground("/images/old_card.png");
		}

		nameLabel.setMaxWidth(width);
		nameLabel.setMinWidth(width);
		nameLabel.setAlignment(Pos.CENTER);
		BorderPane borderPane = new BorderPane();
		borderPane.setMaxHeight(height);
		borderPane.setMinHeight(height);
		borderPane.setMaxWidth(width);
		borderPane.setMinWidth(width);
		borderPane.setTop(icons);
		borderPane.setBottom(nameLabel);

		this.getChildren().addAll(borderPane);

		powerLabel.setScaleX(scale * 2d);
		powerLabel.setScaleY(scale * 2d);
	}

	private ImageView loadIcon(String iconName) {
		try {
			InputStream iconStream = getClass().getResourceAsStream("/icons/" + iconName + ".png");
			Image iconImage = new Image(iconStream);
			ImageView iconView = new ImageView(iconImage);
			iconView.setFitHeight(iconSize * scale);
			iconView.setFitWidth(iconSize * scale);
			return iconView;
		} catch (Exception e) {
			System.err.println("Error loading icon: " + iconName);
			e.printStackTrace();
			return null;
		}
	}

	private void setBackground(String path) {
		Image image = new Image(ViewLoader.class.getResource(path).toExternalForm(),
			width, height,
			false, false);

		BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
			BackgroundRepeat.NO_REPEAT,
			BackgroundPosition.DEFAULT,
			BackgroundSize.DEFAULT);
		Background background = new Background(backgroundImage);
		this.setBackground(background);
	}

	public Card getCard() {
		return card;
	}

	public void updatePower() {
		powerLabel.setText(String.valueOf(card.calculatePower()));
	}
}