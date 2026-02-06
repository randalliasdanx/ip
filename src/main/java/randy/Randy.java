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

    // startup message showing saved tasks and reminders
    public String getStartupMessage() {
        StringBuilder sb = new StringBuilder("yo! i'm Randy\n");
        
        // show saved tasks
        if (tasks.size() > 0) {
            sb.append("\nfound your saved tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                sb.append("\n").append(i + 1).append(". ").append(tasks.get(i));
            }
        }
        
        // show reminders (upcoming deadlines in next 7 days)
        TaskList upcoming = tasks.getUpcoming(7);
        if (upcoming.size() > 0) {
            sb.append("\n\n⚠️ heads up! deadlines coming up:");
            for (int i = 0; i < upcoming.size(); i++) {
                sb.append("\n• ").append(upcoming.get(i));
            }
        }
        
        sb.append("\n\nwhatcha need?");
        return sb.toString();
    }

    // CLI mode
    public void run() {
        ui.greet();
        ui.printSavedTasks(tasks);
        ui.printReminders(tasks.getUpcoming(7));
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
