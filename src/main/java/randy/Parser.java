package randy;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Figures out what the user wants and does it.
 */
public class Parser {
    
    // GUI version - returns string response
    public static String processInput(String input, TaskList tasks) {
        String[] words = input.split(" ");
        String cmd = words[0];

        // check if command exists
        if (words.length == 1 && !Prefix.contains(cmd)) {
            return "huh? idk what that means";
        }
        
        // check if description is missing
        if (words.length == 1 && !cmd.equals("list") && !cmd.equals("bye")) {
            return "uh you forgot to add details";
        }

        switch (cmd) {
        case "bye":
            return "peace out! see ya later";
            
        case "list":
            return buildList(tasks);
            
        case "mark":
            return handleMark(words, tasks);
            
        case "unmark":
            return handleUnmark(words, tasks);
            
        case "todo":
            return handleTodo(input, tasks);
            
        case "deadline":
            return handleDeadline(input, tasks);
            
        case "event":
            return handleEvent(input, tasks);
            
        case "delete":
            return handleDelete(words, tasks);
            
        case "on":
            return handleOn(words, tasks);

        case "find":
            return handleFind(input, tasks);
            
        default:
            Task t = new Task(input);
            tasks.add(t);
            return taskAdded(t, tasks.size());
        }
    }

    private static String buildList(TaskList tasks) {
        StringBuilder sb = new StringBuilder("here's what you got:\n");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append((i + 1) + ". " + tasks.get(i));
            if (i < tasks.size() - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    private static String handleMark(String[] words, TaskList tasks) {
        int num = Integer.parseInt(words[1]);
        Task t = tasks.setDone(num - 1);
        return "nice! marked as done:\n" + t;
    }

    private static String handleUnmark(String[] words, TaskList tasks) {
        int num = Integer.parseInt(words[1]);
        Task t = tasks.setUndone(num - 1);
        return "ok unmarked this one:\n" + t;
    }

    private static String handleTodo(String input, TaskList tasks) {
        String desc = input.substring(5);
        Task t = new ToDo(desc);
        tasks.add(t);
        return taskAdded(t, tasks.size());
    }

    private static String handleDeadline(String input, TaskList tasks) {
        String rest = input.substring(9);
        String[] parts = rest.split(" /by ");
        
        if (parts.length != 2) {
            return "format: deadline <task> /by <date>";
        }
        
        Task t = new Deadline(parts[0], parts[1]);
        tasks.add(t);
        return taskAdded(t, tasks.size());
    }

    private static String handleEvent(String input, TaskList tasks) {
        String rest = input.substring(6);
        String[] parts = rest.split(" /from | /to ");
        
        if (parts.length != 3) {
            return "format: event <task> /from <start> /to <end>";
        }
        
        Task t = new Event(parts[0], parts[1], parts[2]);
        tasks.add(t);
        return taskAdded(t, tasks.size());
    }

    private static String handleDelete(String[] words, TaskList tasks) {
        int idx = Integer.parseInt(words[1]);
        Task t = tasks.remove(idx - 1);
        return "deleted:\n" + t + "\nyou now have " + tasks.size() + " tasks";
    }

    private static String handleOn(String[] words, TaskList tasks) {
        try {
            LocalDate date = LocalDate.parse(words[1]);
            TaskList filtered = tasks.filterByDate(date);
            StringBuilder sb = new StringBuilder("tasks on " + date + ":\n");
            if (filtered.size() == 0) {
                sb.append("nothing on this day");
            } else {
                for (int i = 0; i < filtered.size(); i++) {
                    sb.append((i + 1) + ". " + filtered.get(i));
                    if (i < filtered.size() - 1) {
                        sb.append("\n");
                    }
                }
            }
            return sb.toString();
        } catch (DateTimeParseException e) {
            return "use format: on yyyy-MM-dd";
        }
    }

    private static String handleFind(String input, TaskList tasks) {
        String keyword = input.substring(5).trim();
        if (keyword.isEmpty()) {
            return "find what?";
        }
        TaskList matches = tasks.search(keyword);
        StringBuilder sb = new StringBuilder("found these:\n");
        if (matches.size() == 0) {
            sb.append("no matches");
        } else {
            for (int i = 0; i < matches.size(); i++) {
                sb.append((i + 1) + ". " + matches.get(i));
                if (i < matches.size() - 1) {
                    sb.append("\n");
                }
            }
        }
        return sb.toString();
    }

    private static String taskAdded(Task t, int size) {
        return "added:\n" + t + "\nyou now have " + size + " tasks";
    }
    
    // CLI version - returns true to keep going, false to quit
    public static boolean execute(String input, TaskList tasks, Ui ui) {
        String[] words = input.split(" ");
        String cmd = words[0];

        if (words.length == 1 && !Prefix.contains(cmd)) {
            ui.printUnknown();
            return true;
        }
        
        if (words.length == 1 && !cmd.equals("list") && !cmd.equals("bye")) {
            ui.printEmpty();
            return true;
        }

        switch (cmd) {
        case "bye":
            return false;
            
        case "list":
            ui.printList(tasks);
            break;
            
        case "mark":
            cliMark(words, tasks, ui);
            break;
            
        case "unmark":
            cliUnmark(words, tasks, ui);
            break;
            
        case "todo":
            cliTodo(input, tasks, ui);
            break;
            
        case "deadline":
            cliDeadline(input, tasks, ui);
            break;
            
        case "event":
            cliEvent(input, tasks, ui);
            break;
            
        case "delete":
            cliDelete(words, tasks, ui);
            break;
            
        case "on":
            cliOn(words, tasks, ui);
            break;

        case "find":
            cliFind(input, tasks, ui);
            break;
            
        default:
            Task t = new Task(input);
            tasks.add(t);
            ui.printAdded(t, tasks.size());
            break;
        }
        
        return true;
    }

    private static void cliMark(String[] words, TaskList tasks, Ui ui) {
        int num = Integer.parseInt(words[1]);
        Task t = tasks.setDone(num - 1);
        ui.printMarked(t);
    }

    private static void cliUnmark(String[] words, TaskList tasks, Ui ui) {
        int num = Integer.parseInt(words[1]);
        Task t = tasks.setUndone(num - 1);
        ui.printUnmarked(t);
    }

    private static void cliTodo(String input, TaskList tasks, Ui ui) {
        String desc = input.substring(5);
        Task t = new ToDo(desc);
        tasks.add(t);
        ui.printAdded(t, tasks.size());
    }

    private static void cliDeadline(String input, TaskList tasks, Ui ui) {
        String rest = input.substring(9);
        String[] parts = rest.split(" /by ");
        
        if (parts.length != 2) {
            ui.printError("format: deadline <task> /by <date>");
            return;
        }
        
        Task t = new Deadline(parts[0], parts[1]);
        tasks.add(t);
        ui.printAdded(t, tasks.size());
    }

    private static void cliEvent(String input, TaskList tasks, Ui ui) {
        String rest = input.substring(6);
        String[] parts = rest.split(" /from | /to ");
        
        if (parts.length != 3) {
            ui.printError("format: event <task> /from <start> /to <end>");
            return;
        }
        
        Task t = new Event(parts[0], parts[1], parts[2]);
        tasks.add(t);
        ui.printAdded(t, tasks.size());
    }

    private static void cliDelete(String[] words, TaskList tasks, Ui ui) {
        int idx = Integer.parseInt(words[1]);
        Task t = tasks.remove(idx - 1);
        ui.printDeleted(t, tasks.size());
    }

    private static void cliOn(String[] words, TaskList tasks, Ui ui) {
        try {
            LocalDate date = LocalDate.parse(words[1]);
            TaskList filtered = tasks.filterByDate(date);
            ui.printTasksOnDate(filtered, date.toString());
        } catch (DateTimeParseException e) {
            ui.printError("use format: on yyyy-MM-dd");
        }
    }

    private static void cliFind(String input, TaskList tasks, Ui ui) {
        String keyword = input.substring(5).trim();
        if (keyword.isEmpty()) {
            ui.printError("find what?");
            return;
        }
        TaskList matches = tasks.search(keyword);
        ui.printSearchResults(matches);
    }
}
