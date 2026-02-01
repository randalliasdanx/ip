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
 * Chat bubble with pic and text.
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

    // flip so pic is on left (for Randy's replies)
    private void flip() {
        ObservableList<Node> children = FXCollections.observableArrayList(this.getChildren());
        Collections.reverse(children);
        getChildren().setAll(children);
        setAlignment(Pos.TOP_LEFT);
    }

    public static DialogBox forUser(String msg, Image img) {
        return new DialogBox(msg, img);
    }

    public static DialogBox forRandy(String msg, Image img) {
        DialogBox box = new DialogBox(msg, img);
        box.flip();
        return box;
    }
}
