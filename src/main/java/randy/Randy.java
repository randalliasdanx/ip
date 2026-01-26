package randy;

import java.io.IOException;

/**
 * Main class for the Randy chatbot application.
 * Handles initialization and the main program loop.
 */
public class Randy {
    private Storage storage;
    private TaskList arr;
    private Ui ui;

    /**
     * Creates a new Randy chatbot instance.
     * @param path File path for storing tasks.
     */
    public Randy(String path) {
        ui = new Ui();
        storage = new Storage(path);
        storage.setupDirectory();
        
        try {
            arr = new TaskList(storage.load());
        } catch (IOException e) {
            ui.showLoadingError();
            arr = new TaskList();
        }
    }

    /**
     * Runs the main loop of the chatbot.
     */
    public void run() {
        ui.showWelcome();
        ui.showLoadedTasks(arr);
        ui.showPrompt();

        String input = ui.readCommand();
        while (Parser.parse(input, arr, ui)) {
            input = ui.readCommand();
        }

        ui.showBye();
        storage.save(arr);
        ui.close();
    }

    public static void main(String[] args) {
        new Randy("./data/storage.txt").run();
    }
}
