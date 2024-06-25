package ir.sharif.view.game;

import ir.sharif.model.game.*;
import ir.sharif.model.game.abilities.Berserker;
import ir.sharif.utils.ConstantsLoader;
import ir.sharif.view.ViewLoader;
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

public class CardGraphics extends VBox {
	private final Card card;
	private final int width;
	private final int height;
	private final int iconSize = 50;

	private HBox icons;
	private Label nameLabel;
	private ImageView imageView;

	public CardGraphics(Card card, double scale) {
		this.card = card;
		this.width = Integer.parseInt(ConstantsLoader.getInstance().getProperty("card.defaultwidth"));
		this.height = (int)(width * Float.parseFloat(ConstantsLoader.getInstance().getProperty("card.scale")));
		this.getStyleClass().add("card"); // Add the style class to the VBox
		this.setMaxWidth(width); // Set the preferred width of the VBox
		this.setMaxHeight(height);
		this.setMinWidth(width); // Set the preferred width of the VBox
		this.setMinHeight(height);

		init();
		scale(this, scale);
		this.getStylesheets().add(getClass().getResource("/CSS/card.css").toExternalForm());
	}

	private void init() {
		icons = new HBox();
		icons.getStyleClass().add("icons"); // Add the style class to the HBox
		if (card.isHero()) {
			icons.getChildren().add(loadIcon("hero"));
		}

		if (card.getAbility() != null) {
			icons.getChildren().add(loadIcon(card.getAbility().getClass().getSimpleName()));
		}

		setBackground();

		nameLabel = new Label(card.getName());
		nameLabel.getStyleClass().add("name"); // Add the style class to the Label

		setAlignment(Pos.CENTER);

		imageView = new ImageView();
		InputStream imageStream = getClass().getResourceAsStream("/images/hero.png");
		Image image = new Image(imageStream, width, height, false, true);
		imageView.setImage(image);
		imageView.setFitHeight(height * 2 / 3);
		imageView.getStyleClass().add("image"); // Add the style class to the ImageView
		this.getChildren().addAll(icons, imageView, nameLabel);
	}

	public void scale(Node node, double scale) {
		this.setScaleX(scale);
		this.setScaleY(scale);
//		nameLabel.setScaleX(scale);
//		nameLabel.setScaleY(scale);
//		imageView.setScaleX(scale);
//		imageView.setScaleY(scale);
//		icons.setScaleX(scale);
//		icons.setScaleY(scale);

		for (Node icon : icons.getChildren()) {
			if (icon instanceof ImageView) {
				ImageView iconView = (ImageView) icon;
				icon.setScaleX(scale);
				icon.setScaleY(scale);
			}
		}
	}

	public void update() {

	}

	private ImageView loadIcon(String iconName) {
		try {
			InputStream iconStream = getClass().getResourceAsStream("/icons/" + iconName + ".png");
			Image iconImage = new Image(iconStream);
			ImageView iconView = new ImageView(iconImage);
			iconView.setFitHeight(iconSize);
			iconView.setFitWidth(iconSize);
			return iconView;
		} catch (Exception e) {
			System.err.println("Error loading icon: " + iconName);
			e.printStackTrace();
			return null;
		}
	}

	private void setBackground() {
		Image image = new Image(ViewLoader.class.getResource("/images/old_card.png").toExternalForm(),
			ConstantsLoader.getInstance().getMenuWidth(), ConstantsLoader.getInstance().getMenuHeight(),
			false, false);

		BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT,
			BackgroundRepeat.NO_REPEAT,
			BackgroundPosition.DEFAULT,
			BackgroundSize.DEFAULT);;
		Background background = new Background(backgroundImage);
		this.setBackground(background);
	}

	public Card getCard() {
		return card;
	}
}