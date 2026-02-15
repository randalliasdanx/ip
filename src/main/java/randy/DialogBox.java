package randy;

import java.io.IOException;
import java.util.Collections;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;

/**
 * A custom dialog box component for the chat interface.
 * Displays a message with an optional profile picture, styled
 * differently for user messages, bot messages, and error messages.
 */
public class DialogBox extends HBox {
    @FXML
    private Label text;
    @FXML
    private ImageView pic;

    private DialogBox(String msg, Image img) {
        try {
            FXMLLoader loader = new FXMLLoader(MainWindow.class.getResource("/view/DialogBox.fxml"));
            loader.setController(this);
            loader.setRoot(this);
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }

        text.setText(msg);
        pic.setImage(img);
        clipImageToCircle();
    }

    /**
     * Clips the profile image into a circle shape for a cleaner look.
     */
    private void clipImageToCircle() {
        Circle clip = new Circle(20, 20, 20);
        pic.setClip(clip);
    }

    /**
     * Flips the dialog box so the picture appears on the left (for bot replies).
     */
    private void flip() {
        ObservableList<Node> children = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(children);
        getChildren().setAll(children);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a dialog box for user messages, right-aligned with user styling.
     *
     * @param msg the user's message text
     * @param img the user's profile image
     * @return a styled DialogBox for the user
     */
    public static DialogBox forUser(String msg, Image img) {
        DialogBox box = new DialogBox(msg, img);
        box.text.getStyleClass().add("user-bubble");
        box.getStyleClass().add("user-dialog");
        return box;
    }

    /**
     * Creates a dialog box for bot messages, left-aligned with bot styling.
     *
     * @param msg the bot's response text
     * @param img the bot's profile image
     * @return a styled DialogBox for the bot
     */
    public static DialogBox forRandy(String msg, Image img) {
        DialogBox box = new DialogBox(msg, img);
        box.text.getStyleClass().add("bot-bubble");
        box.getStyleClass().add("bot-dialog");
        box.flip();
        return box;
    }

    /**
     * Creates a dialog box for error messages, left-aligned with red error styling
     * to draw the user's attention to mistakes.
     *
     * @param msg the error message text
     * @param img the bot's profile image
     * @return a styled DialogBox with error highlighting
     */
    public static DialogBox forError(String msg, Image img) {
        DialogBox box = new DialogBox(msg, img);
        box.text.getStyleClass().add("error-bubble");
        box.getStyleClass().add("bot-dialog");
        box.flip();
        return box;
    }
}
