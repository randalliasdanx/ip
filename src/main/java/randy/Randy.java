package randy;

import java.io.IOException;

/**
 * Randy - your friendly task manager chatbot.
 * 
 * This is the main application class that orchestrates the task management system.
 * Randy manages tasks using three core components:
 * - Storage: Persists tasks to/from disk
 * - TaskList: Manages the collection of tasks in memory
 * - Ui: Handles user interaction in CLI mode
 * 
 * Randy supports both CLI and GUI interaction modes through different entry points.
 */
public class Randy {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

    /**
     * Initializes Randy with a storage file path.
     * Loads previously saved tasks from storage, or starts with an empty list
     * if loading fails.
     * 
     * @param filePath the path to the storage file for persisting tasks
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
     * This method is designed for GUI mode, where each user input
     * produces a single response string without blocking.
     * 
     * @param input the user's input command
     * @return the bot's response to the input
     */
    public String getResponse(String input) {
        String reply = Parser.processInput(input, tasks);
        storage.writeToFile(tasks);
        return reply;
    }

    /**
     * Runs the CLI interactive mode.
     * Displays a greeting, loads saved tasks, and continuously prompts
     * for user input until the user exits (bye command).
     */
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

    /**
     * Main entry point for the CLI application.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        new Randy("./data/storage.txt").run();
    }
}
