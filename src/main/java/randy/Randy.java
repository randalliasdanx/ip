package randy;

import java.io.IOException;

/**
 * Main application class for the Randy chatbot.
 * Ties together Storage, TaskList, Ui, and Parser.
 */
public class Randy {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Creates a new Randy instance, loading tasks from the given file path.
     *
     * @param filePath Path to the storage file.
     */
    public Randy(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        storage.init();

        try {
            tasks = new TaskList(storage.loadTasks());
        } catch (IOException e) {
            ui.printLoadError();
            tasks = new TaskList();
        }
    }

    /**
     * Processes user input and returns a response string.
     * Called by the GUI to get Randy's reply.
     *
     * @param input The user's raw input.
     * @return The response to display.
     */
    public String getResponse(String input) {
        String reply = Parser.processInput(input, tasks);
        storage.writeToFile(tasks);
        return reply;
    }

    /** Runs the CLI loop, reading input until the user says bye. */
    public void run() {
        ui.greet();
        ui.printSavedTasks(tasks);
        ui.askForInput();

        String line = ui.read();
        while (Parser.execute(line, tasks, ui)) {
            line = ui.read();
        }

        ui.sayBye();
        storage.writeToFile(tasks);
        ui.shutdown();
    }

    public static void main(String[] args) {
        new Randy("./data/storage.txt").run();
    }
}
