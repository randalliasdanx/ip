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

/**
 * Represents a single chat bubble in the GUI conversation.
 * Contains a text label and a profile picture ImageView.
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
    }

    // flips the children so pic goes on the left side (for randy's messages)
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
        return new DialogBox(msg, img);
    }

    /**
     * Creates a dialog box for Randy's reply (left-aligned, flipped).
     *
     * @param msg The response text.
     * @param img Randy's profile image.
     * @return A new DialogBox styled for Randy.
     */
    public static DialogBox forRandy(String msg, Image img) {
        DialogBox box = new DialogBox(msg, img);
        box.flip();
        return box;
    }
}
