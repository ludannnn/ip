package yoda.gui;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import yoda.Yoda;

import java.util.Objects;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;
    @FXML
    private Button helpButton;

    private Yoda yoda;

    private final Image userImage =
            new Image(Objects.requireNonNull(getClass().getResource("/images/Luke.png")).toExternalForm());
    private final Image yodaImage =
            new Image(Objects.requireNonNull(getClass().getResource("/images/Yoda.png")).toExternalForm());

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());
    }

    /** Injects the Duke instance */
    public void setYoda(Yoda y) {
        yoda = y;
    }

    /**
     * Creates two dialog boxes, one echoing user input and the other containing Duke's reply and then appends them to
     * the dialog container. Clears the user input after processing.
     */
    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = yoda.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getYodaDialog(response, yodaImage)
        );
        userInput.clear();

        if (yoda.shouldExit()) {
            Platform.exit();
        }
    }

    @FXML
    private void handleHelp() {
        String response = yoda.getResponse("help");
        dialogContainer.getChildren().add(
                DialogBox.getYodaDialog(response, yodaImage)
        );
    }
}
