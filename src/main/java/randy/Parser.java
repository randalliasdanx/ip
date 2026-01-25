package randy;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Parser {
    
    public static boolean parse(String input, TaskList arr, Ui ui) {
        String[] split = input.split(" ");
        String cmd = split[0];

        if (split.length == 1 && !Prefix.isValid(cmd)) {
            ui.showUnknownCommand();
            return true;
        }
        
        if (split.length == 1 && !cmd.equals("list") && !cmd.equals("bye")) {
            ui.showMissingDescription();
            return true;
        }

        switch (cmd) {
            case "bye":
                return false;
                
            case "list":
                ui.showList(arr);
                break;
                
            case "mark":
                doMark(split, arr, ui);
                break;
                
            case "unmark":
                doUnmark(split, arr, ui);
                break;
                
            case "todo":
                doTodo(input, arr, ui);
                break;
                
            case "deadline":
                doDeadline(input, arr, ui);
                break;
                
            case "event":
                doEvent(input, arr, ui);
                break;
                
            case "delete":
                doDelete(split, arr, ui);
                break;
                
            case "on":
                doOn(split, arr, ui);
                break;
                
            default:
                Task t = new Task(input);
                arr.add(t);
                ui.showAdded(t, arr.size());
                break;
        }
        
        return true;
    }

    private static void doMark(String[] split, TaskList arr, Ui ui) {
        int taskNum = Integer.parseInt(split[1]);
        Task t = arr.markTask(taskNum - 1);
        ui.showMarked(t);
    }

    private static void doUnmark(String[] split, TaskList arr, Ui ui) {
        int taskNum = Integer.parseInt(split[1]);
        Task t = arr.unmarkTask(taskNum - 1);
        ui.showUnmarked(t);
    }

    private static void doTodo(String input, TaskList arr, Ui ui) {
        String inp = input.substring(5);
        Task t = new ToDo(inp);
        arr.add(t);
        ui.showAdded(t, arr.size());
    }

    private static void doDeadline(String input, TaskList arr, Ui ui) {
        String rest = input.substring(9);
        String[] parts = rest.split(" /by ");
        
        if (parts.length != 2) {
            ui.showError("Please insert a valid deadline (use: deadline <desc> /by <date>)");
            return;
        }
        
        String description = parts[0];
        String by = parts[1];
        Task t = new Deadline(description, by);
        arr.add(t);
        ui.showAdded(t, arr.size());
    }

    private static void doEvent(String input, TaskList arr, Ui ui) {
        String rest = input.substring(6);
        String[] parts = rest.split(" /from | /to ");
        
        if (parts.length != 3) {
            ui.showError("Please insert a valid event (use: event <desc> /from <start> /to <end>)");
            return;
        }
        
        String description = parts[0];
        String from = parts[1];
        String to = parts[2];
        Task t = new Event(description, from, to);
        arr.add(t);
        ui.showAdded(t, arr.size());
    }

    private static void doDelete(String[] split, TaskList arr, Ui ui) {
        int ind = Integer.valueOf(split[1]);
        Task t = arr.delete(ind - 1);
        ui.showDeleted(t, arr.size());
    }

    private static void doOn(String[] split, TaskList arr, Ui ui) {
        try {
            LocalDate searchDate = LocalDate.parse(split[1]);
            TaskList results = arr.getTasksOn(searchDate);
            ui.showTasksOn(results, searchDate.toString());
        } catch (DateTimeParseException e) {
            ui.showError("Please use yyyy-MM-dd format (e.g., on 2019-12-02)");
        }
    }
}
