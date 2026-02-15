package randy;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Parses user input and executes the appropriate command.
 */
public class Parser {
    
    // Command prefix lengths for substring extraction
    private static final int TODO_PREFIX_LEN = 5;      // "todo "
    private static final int EVENT_PREFIX_LEN = 6;     // "event "
    private static final int DEADLINE_PREFIX_LEN = 9;  // "deadline "
    private static final int FIND_PREFIX_LEN = 5;      // "find "

    // Error messages
    private static final String ERR_UNKNOWN = "huh? idk what that means";
    private static final String ERR_EMPTY = "uh you forgot to add details";
    private static final String ERR_DEADLINE_FORMAT = "format: deadline <task> /by <date>";
    private static final String ERR_EVENT_FORMAT = "format: event <task> /from <start> /to <end>";
    private static final String ERR_DATE_FORMAT = "use format: on yyyy-MM-dd";
    private static final String ERR_INVALID_NUMBER = "please enter a valid task number";
    
    /**
     * Processes user input and returns response string (for GUI).
     */
    public static String processInput(String input, TaskList tasks) {
        String[] words = input.split(" ");
        String cmd = words[0];

        if (!Prefix.contains(cmd)) {
            return ERR_UNKNOWN;
        }
        
        if (words.length == 1 && !cmd.equals("list") && !cmd.equals("bye")) {
            return ERR_EMPTY;
        }

        switch (cmd) {
        case "bye":
            return "peace out! see ya later";
        case "list":
            return formatTaskList(tasks);
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
            return ERR_UNKNOWN;
        }
    }

    private static String formatTaskList(TaskList tasks) {
        if (tasks.size() == 0) {
            return "no tasks yet!";
        }
        StringBuilder sb = new StringBuilder("here's what you got:");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append("\n").append(i + 1).append(". ").append(tasks.get(i));
        }
        return sb.toString();
    }

    private static String formatSearchResults(TaskList results, String header) {
        StringBuilder sb = new StringBuilder(header);
        if (results.size() == 0) {
            sb.append("\nno matches");
        } else {
            for (int i = 0; i < results.size(); i++) {
                sb.append("\n").append(i + 1).append(". ").append(results.get(i));
            }
        }
        return sb.toString();
    }

    private static String handleMark(String[] words, TaskList tasks) {
        try {
            int taskNum = Integer.parseInt(words[1]);
            Task t = tasks.setDone(taskNum - 1);
            return "nice! marked as done:\n" + t;
        } catch (NumberFormatException e) {
            return ERR_INVALID_NUMBER;
        } catch (IndexOutOfBoundsException e) {
            return "task number out of range";
        }
    }

    private static String handleUnmark(String[] words, TaskList tasks) {
        try {
            int taskNum = Integer.parseInt(words[1]);
            Task t = tasks.setUndone(taskNum - 1);
            return "ok unmarked this one:\n" + t;
        } catch (NumberFormatException e) {
            return ERR_INVALID_NUMBER;
        } catch (IndexOutOfBoundsException e) {
            return "task number out of range";
        }
    }

    private static String handleTodo(String input, TaskList tasks) {
        String desc = input.substring(TODO_PREFIX_LEN);
        Task t = new ToDo(desc);
        tasks.add(t);
        return formatTaskAdded(t, tasks.size());
    }

    private static String handleDeadline(String input, TaskList tasks) {
        String rest = input.substring(DEADLINE_PREFIX_LEN);
        String[] parts = rest.split(" /by ");
        
        if (parts.length != 2) {
            return ERR_DEADLINE_FORMAT;
        }
        
        Task t = new Deadline(parts[0], parts[1]);
        tasks.add(t);
        return formatTaskAdded(t, tasks.size());
    }

    private static String handleEvent(String input, TaskList tasks) {
        String rest = input.substring(EVENT_PREFIX_LEN);
        String[] parts = rest.split(" /from | /to ");
        
        if (parts.length != 3) {
            return ERR_EVENT_FORMAT;
        }
        
        Task t = new Event(parts[0], parts[1], parts[2]);
        tasks.add(t);
        return formatTaskAdded(t, tasks.size());
    }

    private static String handleDelete(String[] words, TaskList tasks) {
        try {
            int idx = Integer.parseInt(words[1]);
            Task t = tasks.remove(idx - 1);
            return "deleted:\n" + t + "\nyou now have " + tasks.size() + " tasks";
        } catch (NumberFormatException e) {
            return ERR_INVALID_NUMBER;
        } catch (IndexOutOfBoundsException e) {
            return "task number out of range";
        }
    }

    private static String handleOn(String[] words, TaskList tasks) {
        try {
            LocalDate date = LocalDate.parse(words[1]);
            TaskList filtered = tasks.filterByDate(date);
            return formatSearchResults(filtered, "tasks on " + date + ":");
        } catch (DateTimeParseException e) {
            return ERR_DATE_FORMAT;
        }
    }

    private static String handleFind(String input, TaskList tasks) {
        String keyword = input.substring(FIND_PREFIX_LEN).trim();
        if (keyword.isEmpty()) {
            return "find what?";
        }
        TaskList matches = tasks.search(keyword);
        return formatSearchResults(matches, "found these:");
    }

    private static String formatTaskAdded(Task t, int totalTasks) {
        return "added:\n" + t + "\nyou now have " + totalTasks + " tasks";
    }

    /**
     * Checks if a response from processInput is an error message.
     * Used by the GUI to apply error styling to error responses.
     *
     * @param response the response string from processInput
     * @return true if the response is an error message
     */
    public static boolean isErrorResponse(String response) {
        return response.equals(ERR_UNKNOWN)
                || response.equals(ERR_EMPTY)
                || response.equals(ERR_DEADLINE_FORMAT)
                || response.equals(ERR_EVENT_FORMAT)
                || response.equals(ERR_DATE_FORMAT)
                || response.equals(ERR_INVALID_NUMBER)
                || response.equals("task number out of range")
                || response.equals("find what?");
    }
    
    /**
     * Executes command and updates UI (for CLI).
     * Returns false if user wants to exit, true otherwise.
     */
    public static boolean execute(String input, TaskList tasks, Ui ui) {
        String[] words = input.split(" ");
        String cmd = words[0];

        if (!Prefix.contains(cmd)) {
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
            ui.printUnknown();
            break;
        }
        
        return true;
    }

    private static void cliMark(String[] words, TaskList tasks, Ui ui) {
        try {
            int num = Integer.parseInt(words[1]);
            Task t = tasks.setDone(num - 1);
            ui.printMarked(t);
        } catch (NumberFormatException e) {
            ui.printError(ERR_INVALID_NUMBER);
        } catch (IndexOutOfBoundsException e) {
            ui.printError("task number out of range");
        }
    }

    private static void cliUnmark(String[] words, TaskList tasks, Ui ui) {
        try {
            int num = Integer.parseInt(words[1]);
            Task t = tasks.setUndone(num - 1);
            ui.printUnmarked(t);
        } catch (NumberFormatException e) {
            ui.printError(ERR_INVALID_NUMBER);
        } catch (IndexOutOfBoundsException e) {
            ui.printError("task number out of range");
        }
    }

    private static void cliTodo(String input, TaskList tasks, Ui ui) {
        String desc = input.substring(TODO_PREFIX_LEN);
        Task t = new ToDo(desc);
        tasks.add(t);
        ui.printAdded(t, tasks.size());
    }

    private static void cliDeadline(String input, TaskList tasks, Ui ui) {
        String rest = input.substring(DEADLINE_PREFIX_LEN);
        String[] parts = rest.split(" /by ");
        
        if (parts.length != 2) {
            ui.printError(ERR_DEADLINE_FORMAT);
            return;
        }
        
        Task t = new Deadline(parts[0], parts[1]);
        tasks.add(t);
        ui.printAdded(t, tasks.size());
    }

    private static void cliEvent(String input, TaskList tasks, Ui ui) {
        String rest = input.substring(EVENT_PREFIX_LEN);
        String[] parts = rest.split(" /from | /to ");
        
        if (parts.length != 3) {
            ui.printError(ERR_EVENT_FORMAT);
            return;
        }
        
        Task t = new Event(parts[0], parts[1], parts[2]);
        tasks.add(t);
        ui.printAdded(t, tasks.size());
    }

    private static void cliDelete(String[] words, TaskList tasks, Ui ui) {
        try {
            int idx = Integer.parseInt(words[1]);
            Task t = tasks.remove(idx - 1);
            ui.printDeleted(t, tasks.size());
        } catch (NumberFormatException e) {
            ui.printError(ERR_INVALID_NUMBER);
        } catch (IndexOutOfBoundsException e) {
            ui.printError("task number out of range");
        }
    }

    private static void cliOn(String[] words, TaskList tasks, Ui ui) {
        try {
            LocalDate date = LocalDate.parse(words[1]);
            TaskList filtered = tasks.filterByDate(date);
            ui.printTasksOnDate(filtered, date.toString());
        } catch (DateTimeParseException e) {
            ui.printError(ERR_DATE_FORMAT);
        }
    }

    private static void cliFind(String input, TaskList tasks, Ui ui) {
        String keyword = input.substring(FIND_PREFIX_LEN).trim();
        if (keyword.isEmpty()) {
            ui.printError("find what?");
            return;
        }
        TaskList matches = tasks.search(keyword);
        ui.printSearchResults(matches);
    }
}
