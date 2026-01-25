package randy;

import java.io.IOException;

public class Randy {
    private Storage storage;
    private TaskList arr;
    private Ui ui;

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
