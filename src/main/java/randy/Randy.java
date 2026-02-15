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
     * Returns the welcome message including any saved tasks.
     * Called by the GUI on startup to show the initial greeting.
     *
     * @return The welcome message with saved tasks if any.
     */
    public String getWelcome() {
        StringBuilder sb = new StringBuilder();
        sb.append("YO WHAT'S GOOD! I'm Vikkstar, your task manager!");
        sb.append("\nlet's get productive Deji!");
        if (tasks.size() > 0) {
            sb.append("\n\noi Deji, found your saved tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                sb.append("\n").append(i + 1).append(". ").append(tasks.get(i));
            }
        }
        return sb.toString();
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
