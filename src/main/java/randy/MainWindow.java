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
 * Displays Vikkstar (bot) and Deji (user) conversation.
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

    private Image dejiPic = new Image(this.getClass().getResourceAsStream("/images/DaUser.png"));
    private Image vikkstarPic = new Image(this.getClass().getResourceAsStream("/images/DaRandy.png"));

    /** Initialises the controller and binds the scroll pane. */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(chatBox.heightProperty());
    }

    /**
     * Sets the Randy instance and displays the welcome message with saved tasks.
     *
     * @param r The Randy instance.
     */
    public void setRandy(Randy r) {
        randy = r;
        // show welcome + saved tasks now that we have access to the task list
        chatBox.getChildren().add(
            DialogBox.forRandy(randy.getWelcome(), vikkstarPic)
        );
    }

    @FXML
    private void handleSend() {
        String input = inputField.getText();
        if (input == null || input.trim().isEmpty()) {
            return; // dont send blank messages
        }
        String response = randy.getResponse(input);

        chatBox.getChildren().add(DialogBox.forUser(input, dejiPic));

        // use error styling if the response is an error message
        if (Parser.isErrorResponse(response)) {
            chatBox.getChildren().add(DialogBox.forError(response, vikkstarPic));
        } else {
            chatBox.getChildren().add(DialogBox.forRandy(response, vikkstarPic));
        }

        inputField.clear();
    }
}
