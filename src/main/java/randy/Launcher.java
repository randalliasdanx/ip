package randy;

import javafx.application.Application;

/**
 * Entry point for the application.
 * Separate from Main to work around JavaFX classpath issues.
 */
public class Launcher {
    public static void main(String[] args) {
        Application.launch(Main.class, args);
    }
}
