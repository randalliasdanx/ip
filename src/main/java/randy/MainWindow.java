package randy;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main chat window.
 * Manages the chat display, user input, and interaction with the Randy bot.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox chatBox;
    @FXML
    private TextField inputField;
    @FXML
    private Button sendBtn;

    private Randy randy;

    private Image userPic = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image randyPic = new Image(this.getClass().getResourceAsStream("/images/DaRandy.png"));

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(chatBox.heightProperty());
        // welcome msg
        chatBox.getChildren().add(
            DialogBox.forRandy("yo! i'm Randy\nwhatcha need?", randyPic)
        );
    }

    public void setRandy(Randy r) {
        randy = r;
    }

    @FXML
    private void handleSend() {
        String input = inputField.getText();
        if (input.trim().isEmpty()) {
            return;
        }
        String response = randy.getResponse(input);

        chatBox.getChildren().add(DialogBox.forUser(input, userPic));

        if (Parser.isErrorResponse(response)) {
            chatBox.getChildren().add(DialogBox.forError(response, randyPic));
        } else {
            chatBox.getChildren().add(DialogBox.forRandy(response, randyPic));
        }

        inputField.clear();
    }
}
