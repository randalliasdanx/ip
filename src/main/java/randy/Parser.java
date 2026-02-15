package randy;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Parses user input and executes the corresponding command.
 * Supports both GUI mode (returns response strings) and CLI mode (prints to Ui).
 */
public class Parser {

    // these are just the lengths of the command words + the space after
    private static final int TODO_LEN = 5;      // "todo "
    private static final int EVENT_LEN = 6;     // "event "
    private static final int DEADLINE_LEN = 9;  // "deadline "
    private static final int FIND_LEN = 5;      // "find "

    // error messages - keeping them here so i dont repeat myself
    private static final String ERR_UNKNOWN = "huh? idk what that means";
    private static final String ERR_EMPTY = "uh you forgot to add details";
    private static final String ERR_DEADLINE_FMT = "wrong format lah, use: deadline <task> /by <date>";
    private static final String ERR_EVENT_FMT = "wrong format lah, use: event <task> /from <start> /to <end>";
    private static final String ERR_DATE_FMT = "use this format: on yyyy-MM-dd";
    private static final String ERR_BAD_NUM = "thats not a valid task number";

    /**
     * Processes user input and returns the appropriate response string.
     * Used by the GUI to display Randy's reply.
     *
     * @param input The raw user input.
     * @param tasks The current task list to operate on.
     * @return A response string to show to the user.
     */
    public static String processInput(String input, TaskList tasks) {
        // handle null or blank input so we dont crash
        if (input == null || input.trim().isEmpty()) {
            return ERR_EMPTY;
        }
        input = input.trim();

        String[] words = input.split(" ");
        String cmd = words[0].toLowerCase();

        if (!Prefix.contains(cmd)) {
            return ERR_UNKNOWN;
        }

        // commands that need more than just the keyword
        if (words.length == 1 && !cmd.equals("list") && !cmd.equals("bye")) {
            return ERR_EMPTY;
        }

        switch (cmd) {
        case "bye":
            return "peace out! see ya later";
        case "list":
            return showList(tasks);
        case "mark":
            return doMark(words, tasks);
        case "unmark":
            return doUnmark(words, tasks);
        case "todo":
            return doTodo(input, tasks);
        case "deadline":
            return doDeadline(input, tasks);
        case "event":
            return doEvent(input, tasks);
        case "delete":
            return doDelete(words, tasks);
        case "on":
            return doOn(words, tasks);
        case "find":
            return doFind(input, tasks);
        default:
            return ERR_UNKNOWN;
        }
    }

    private static String showList(TaskList tasks) {
        if (tasks.size() == 0) {
            return "no tasks yet! go add some";
        }
        StringBuilder sb = new StringBuilder("here's what you got:");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append("\n").append(i + 1).append(". ").append(tasks.get(i));
        }
        return sb.toString();
    }

    private static String showResults(TaskList results, String header) {
        StringBuilder sb = new StringBuilder(header);
        if (results.size() == 0) {
            sb.append("\nno matches found");
        } else {
            for (int i = 0; i < results.size(); i++) {
                sb.append("\n").append(i + 1).append(". ").append(results.get(i));
            }
        }
        return sb.toString();
    }

    private static String doMark(String[] words, TaskList tasks) {
        try {
            int num = Integer.parseInt(words[1]);
            Task t = tasks.setDone(num - 1);
            return "nice! marked as done:\n" + t;
        } catch (NumberFormatException e) {
            return ERR_BAD_NUM;
        } catch (IndexOutOfBoundsException | AssertionError e) {
            return "task number out of range";
        }
    }

    private static String doUnmark(String[] words, TaskList tasks) {
        try {
            int num = Integer.parseInt(words[1]);
            Task t = tasks.setUndone(num - 1);
            return "ok unmarked this one:\n" + t;
        } catch (NumberFormatException e) {
            return ERR_BAD_NUM;
        } catch (IndexOutOfBoundsException | AssertionError e) {
            return "task number out of range";
        }
    }

    private static String doTodo(String input, TaskList tasks) {
        String desc = input.substring(TODO_LEN).trim();
        if (desc.isEmpty()) {
            return ERR_EMPTY;
        }
        Task t = new ToDo(desc);
        tasks.add(t);
        return taskAddedMsg(t, tasks.size());
    }

    private static String doDeadline(String input, TaskList tasks) {
        if (input.length() <= DEADLINE_LEN) {
            return ERR_DEADLINE_FMT;
        }
        String rest = input.substring(DEADLINE_LEN).trim();
        String[] parts = rest.split(" /by ");

        if (parts.length != 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            return ERR_DEADLINE_FMT;
        }

        Task t = new Deadline(parts[0].trim(), parts[1].trim());
        tasks.add(t);
        return taskAddedMsg(t, tasks.size());
    }

    private static String doEvent(String input, TaskList tasks) {
        if (input.length() <= EVENT_LEN) {
            return ERR_EVENT_FMT;
        }
        String rest = input.substring(EVENT_LEN).trim();
        String[] parts = rest.split(" /from | /to ");

        if (parts.length != 3 || parts[0].trim().isEmpty()
                || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            return ERR_EVENT_FMT;
        }

        Task t = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
        tasks.add(t);
        return taskAddedMsg(t, tasks.size());
    }

    private static String doDelete(String[] words, TaskList tasks) {
        try {
            int idx = Integer.parseInt(words[1]);
            Task t = tasks.remove(idx - 1);
            return "deleted:\n" + t + "\nyou now have " + tasks.size() + " tasks";
        } catch (NumberFormatException e) {
            return ERR_BAD_NUM;
        } catch (IndexOutOfBoundsException | AssertionError e) {
            return "task number out of range";
        }
    }

    private static String doOn(String[] words, TaskList tasks) {
        if (words.length < 2) {
            return ERR_DATE_FMT;
        }
        try {
            LocalDate date = LocalDate.parse(words[1]);
            TaskList filtered = tasks.filterByDate(date);
            return showResults(filtered, "tasks on " + date + ":");
        } catch (DateTimeParseException e) {
            return ERR_DATE_FMT;
        }
    }

    private static String doFind(String input, TaskList tasks) {
        if (input.length() <= FIND_LEN) {
            return "find what? give me a keyword";
        }
        String keyword = input.substring(FIND_LEN).trim();
        if (keyword.isEmpty()) {
            return "find what? give me a keyword";
        }
        TaskList matches = tasks.search(keyword);
        return showResults(matches, "found these:");
    }

    private static String taskAddedMsg(Task t, int total) {
        return "added:\n" + t + "\nyou now have " + total + " tasks";
    }

    /**
     * Executes a command in CLI mode, printing output via the Ui.
     *
     * @param input The raw user input.
     * @param tasks The current task list to operate on.
     * @param ui    The Ui instance for printing output.
     * @return false if the user wants to exit (bye), true otherwise.
     */
    public static boolean execute(String input, TaskList tasks, Ui ui) {
        if (input == null || input.trim().isEmpty()) {
            ui.printEmpty();
            return true;
        }
        input = input.trim();

        String[] words = input.split(" ");
        String cmd = words[0].toLowerCase();

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
            ui.printError(ERR_BAD_NUM);
        } catch (IndexOutOfBoundsException | AssertionError e) {
            ui.printError("task number out of range");
        }
    }

    private static void cliUnmark(String[] words, TaskList tasks, Ui ui) {
        try {
            int num = Integer.parseInt(words[1]);
            Task t = tasks.setUndone(num - 1);
            ui.printUnmarked(t);
        } catch (NumberFormatException e) {
            ui.printError(ERR_BAD_NUM);
        } catch (IndexOutOfBoundsException | AssertionError e) {
            ui.printError("task number out of range");
        }
    }

    private static void cliTodo(String input, TaskList tasks, Ui ui) {
        String desc = input.substring(TODO_LEN).trim();
        if (desc.isEmpty()) {
            ui.printError(ERR_EMPTY);
            return;
        }
        Task t = new ToDo(desc);
        tasks.add(t);
        ui.printAdded(t, tasks.size());
    }

    private static void cliDeadline(String input, TaskList tasks, Ui ui) {
        if (input.length() <= DEADLINE_LEN) {
            ui.printError(ERR_DEADLINE_FMT);
            return;
        }
        String rest = input.substring(DEADLINE_LEN).trim();
        String[] parts = rest.split(" /by ");

        if (parts.length != 2 || parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) {
            ui.printError(ERR_DEADLINE_FMT);
            return;
        }

        Task t = new Deadline(parts[0].trim(), parts[1].trim());
        tasks.add(t);
        ui.printAdded(t, tasks.size());
    }

    private static void cliEvent(String input, TaskList tasks, Ui ui) {
        if (input.length() <= EVENT_LEN) {
            ui.printError(ERR_EVENT_FMT);
            return;
        }
        String rest = input.substring(EVENT_LEN).trim();
        String[] parts = rest.split(" /from | /to ");

        if (parts.length != 3 || parts[0].trim().isEmpty()
                || parts[1].trim().isEmpty() || parts[2].trim().isEmpty()) {
            ui.printError(ERR_EVENT_FMT);
            return;
        }

        Task t = new Event(parts[0].trim(), parts[1].trim(), parts[2].trim());
        tasks.add(t);
        ui.printAdded(t, tasks.size());
    }

    private static void cliDelete(String[] words, TaskList tasks, Ui ui) {
        try {
            int idx = Integer.parseInt(words[1]);
            Task t = tasks.remove(idx - 1);
            ui.printDeleted(t, tasks.size());
        } catch (NumberFormatException e) {
            ui.printError(ERR_BAD_NUM);
        } catch (IndexOutOfBoundsException | AssertionError e) {
            ui.printError("task number out of range");
        }
    }

    private static void cliOn(String[] words, TaskList tasks, Ui ui) {
        if (words.length < 2) {
            ui.printError(ERR_DATE_FMT);
            return;
        }
        try {
            LocalDate date = LocalDate.parse(words[1]);
            TaskList filtered = tasks.filterByDate(date);
            ui.printTasksOnDate(filtered, date.toString());
        } catch (DateTimeParseException e) {
            ui.printError(ERR_DATE_FMT);
        }
    }

    private static void cliFind(String input, TaskList tasks, Ui ui) {
        if (input.length() <= FIND_LEN) {
            ui.printError("find what? give me a keyword");
            return;
        }
        String keyword = input.substring(FIND_LEN).trim();
        if (keyword.isEmpty()) {
            ui.printError("find what? give me a keyword");
            return;
        }
        TaskList matches = tasks.search(keyword);
        ui.printSearchResults(matches);
    }
}
