package ir.sharif.view.gui.terminal;

import ir.sharif.model.CommandResult;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class TerminalGUI {
	private Stage stage;
	private TextArea outputArea;

	public TerminalGUI(Function<String, CommandResult> commandRunner) {
		stage = new Stage();
		stage.setTitle("Game Terminal");

		outputArea = new TextArea();
		outputArea.setMinHeight(300);
		outputArea.setEditable(false); // The output area should not be editable
		outputArea.setStyle("-fx-control-inner-background:#1d1d1d; -fx-font-family: Monospaced; -fx-highlight-fill: dodgerblue; -fx-highlight-text-fill: white; -fx-text-fill: lime;"); // Mac terminal style

		Scene scene = getTerminalScene(outputArea);

		stage.setScene(scene);
		stage.show();

		// Adjust the size of the stage to fit the content
		stage.sizeToScene();
	}

	@NotNull
	private Scene getTerminalScene(TextArea outputArea) {
		TextField inputField = new TextField();
		inputField.setPromptText("Write your input here: "); // Append $ to input part
		inputField.setStyle("-fx-control-inner-background:#1d1d1d; -fx-font-family: Monospaced; -fx-highlight-fill: dodgerblue; -fx-highlight-text-fill: white; -fx-text-fill: lime; -fx-prompt-text-fill: #aaaaaa;"); // Mac terminal style
		inputField.setOnAction(event -> {
			String input = inputField.getText();
			// TODO: Process the input and display the output in the outputArea
			// For now, we just echo the input
			outputArea.appendText("> " + input + "\n");
			inputField.clear();
		});

		VBox layout = new VBox(outputArea, inputField); // Remove padding to eliminate white space
		layout.setSpacing(0); // Remove spacing between TextArea and TextField
		layout.setPadding(new Insets(0)); // Remove padding
		VBox.setMargin(outputArea, new Insets(0)); // Remove margins
		VBox.setMargin(inputField, new Insets(0)); // Remove margins

		Scene scene = new Scene(layout);
		scene.setFill(Color.BLACK);
		return scene;
	}

	public void writeOutput(String output) {
		outputArea.appendText(output + "\n");

	}
}