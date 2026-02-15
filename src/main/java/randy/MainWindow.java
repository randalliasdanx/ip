package randy;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

/**
 * Controller for main chat window.
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

    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(chatBox.heightProperty());
        // Vikkstar welcome msg
        chatBox.getChildren().add(
            DialogBox.forRandy(
                "YO WHAT'S GOOD! I'm Vikkstar, your task manager!\nlet's get productive Deji!",
                vikkstarPic)
        );
    }

    public void setRandy(Randy r) {
        randy = r;
    }

    @FXML
    private void handleSend() {
        String input = inputField.getText();
        String response = randy.getResponse(input);
        chatBox.getChildren().addAll(
            DialogBox.forUser(input, dejiPic),
            DialogBox.forRandy(response, vikkstarPic)
        );
        inputField.clear();
    }
}
