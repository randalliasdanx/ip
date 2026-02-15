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
 * Represents a single chat bubble in the GUI conversation.
 * Displays a message with a profile picture, styled differently
 * for user messages, bot messages, and error messages.
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
        ObservableList<Node> kids = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(kids);
        getChildren().setAll(kids);
        setAlignment(Pos.TOP_LEFT);
    }

    /**
     * Creates a dialog box for the user's message (right-aligned).
     *
     * @param msg The message text.
     * @param img The user's profile image.
     * @return A new DialogBox styled for the user.
     */
    public static DialogBox forUser(String msg, Image img) {
        DialogBox box = new DialogBox(msg, img);
        box.text.getStyleClass().add("user-bubble");
        box.getStyleClass().add("user-dialog");
        return box;
    }

    /**
     * Creates a dialog box for Randy's reply (left-aligned, flipped).
     *
     * @param msg The response text.
     * @param img Randy's profile image.
     * @return A new DialogBox styled for the bot.
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
     * @param msg The error message text.
     * @param img The bot's profile image.
     * @return A styled DialogBox with error highlighting.
     */
    public static DialogBox forError(String msg, Image img) {
        DialogBox box = new DialogBox(msg, img);
        box.text.getStyleClass().add("error-bubble");
        box.getStyleClass().add("bot-dialog");
        box.flip();
        return box;
    }
}
