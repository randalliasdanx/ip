package randy;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main GUI application entry point.
 * Loads the FXML layout, applies CSS styling, and displays the chat window.
 */
public class Main extends Application {

    private Randy randy = new Randy("./data/storage.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(Main.class.getResource("/view/styles.css").toExternalForm());
            stage.setScene(scene);
            stage.setTitle("Randy");
            stage.setMinHeight(220);
            stage.setMinWidth(300);
            stage.setResizable(true);
            loader.<MainWindow>getController().setRandy(randy);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
