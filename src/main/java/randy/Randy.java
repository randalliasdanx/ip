package randy;

import java.io.IOException;

/**
 * Randy - your friendly task manager bot.
 */
public class Randy {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;

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

    // for GUI - returns what Randy says back
    public String getResponse(String input) {
        String reply = Parser.processInput(input, tasks);
        storage.writeToFile(tasks);
        return reply;
    }

    // CLI mode
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
