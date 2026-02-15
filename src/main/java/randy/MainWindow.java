package randy;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for the main chat window in the GUI.
 * Handles user input and displays the conversation between the user and Randy.
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

    /** Initialises the controller, binds scroll and displays the welcome message. */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(chatBox.heightProperty());
        chatBox.getChildren().add(
            DialogBox.forRandy("yo! i'm Randy\nwhatcha need?", randyPic)
        );
    }

    /**
     * Sets the Randy instance for this controller to use.
     *
     * @param r The Randy instance.
     */
    public void setRandy(Randy r) {
        randy = r;
    }

    @FXML
    private void handleSend() {
        String input = inputField.getText();
        if (input == null || input.trim().isEmpty()) {
            return; // dont send blank messages
        }
        String response = randy.getResponse(input);
        chatBox.getChildren().addAll(
            DialogBox.forUser(input, userPic),
            DialogBox.forRandy(response, randyPic)
        );
        inputField.clear();
    }
}
