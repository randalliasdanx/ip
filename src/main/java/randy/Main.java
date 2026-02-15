package randy;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Main GUI app.
 */
public class Main extends Application {

    private Randy randy = new Randy("./data/storage.txt");

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Vikkstar Task Manager");
            stage.setMinHeight(220);
            stage.setMinWidth(417);
            loader.<MainWindow>getController().setRandy(randy);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
