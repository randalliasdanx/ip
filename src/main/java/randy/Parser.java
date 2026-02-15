package randy;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * Parses user input and executes the corresponding command.
 * Supports both GUI mode (returns response strings) and CLI mode (prints to Ui).
 * All responses use Vikkstar's energetic YouTuber personality.
 */
public class Parser {

    // command prefix lengths for substring extraction
    private static final int TODO_PREFIX_LEN = 5;      // "todo "
    private static final int EVENT_PREFIX_LEN = 6;     // "event "
    private static final int DEADLINE_PREFIX_LEN = 9;  // "deadline "
    private static final int FIND_PREFIX_LEN = 5;      // "find "

    // Vikkstar-style error messages
    private static final String ERR_UNKNOWN = "bruv that's not a thing... try again Deji!";
    private static final String ERR_EMPTY = "yo Deji you forgot the details fam!";
    private static final String ERR_EMPTY_DESC = "yo Deji you need to type a description!";
    private static final String ERR_EMPTY_LIST = "no tasks to work with Deji!";
    private static final String ERR_DUPLICATE = "yo Deji that task already exists!";
    private static final String ERR_DEADLINE_FORMAT =
            "nah fam, use this format: deadline <task> /by <date>";
    private static final String ERR_EVENT_FORMAT =
            "wrong format Deji! use: event <task> /from <start> /to <end>";
    private static final String ERR_EVENT_DATE_ORDER =
            "the start date can't be after the end date Deji!";
    private static final String ERR_DATE_FORMAT = "Deji use the date format: on yyyy-MM-dd";
    private static final String ERR_INVALID_NUMBER = "that's not a valid number bruv!";

    /**
     * Normalises user input by trimming whitespace, collapsing multiple
     * spaces into one, and lowercasing for consistent command matching.
     *
     * @param input The raw user input.
     * @return The normalised input string, or empty string if null.
     */
    private static String normalize(String input) {
        if (input == null) {
            return "";
        }
        return input.trim().replaceAll("\\s+", " ");
    }

    /**
     * Processes user input and returns the appropriate response string.
     * Used by the GUI to display the bot's reply.
     *
     * @param input The raw user input.
     * @param tasks The current task list to operate on.
     * @return A response string to show to the user.
     */
    public static String processInput(String input, TaskList tasks) {
        input = normalize(input);
        if (input.isEmpty()) {
            return ERR_EMPTY;
        }

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
            return "aight Deji, catch you later! stay winning!";
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
            return "no tasks yet Deji! you're either free or slacking...";
        }
        StringBuilder sb = new StringBuilder("here's the lineup Deji:");
        for (int i = 0; i < tasks.size(); i++) {
            sb.append("\n").append(i + 1).append(". ").append(tasks.get(i));
        }
        return sb.toString();
    }

    private static String formatSearchResults(TaskList results, String header) {
        StringBuilder sb = new StringBuilder(header);
        if (results.size() == 0) {
            sb.append("\nnah nothing matched fam");
        } else {
            for (int i = 0; i < results.size(); i++) {
                sb.append("\n").append(i + 1).append(". ").append(results.get(i));
            }
        }
        return sb.toString();
    }

    private static String handleMark(String[] words, TaskList tasks) {
        if (tasks.isEmpty()) {
            return ERR_EMPTY_LIST;
        }
        try {
            int taskNum = Integer.parseInt(words[1]);
            Task t = tasks.setDone(taskNum - 1);
            return "BIG W Deji! smashed it:\n" + t;
        } catch (NumberFormatException e) {
            return ERR_INVALID_NUMBER;
        } catch (IndexOutOfBoundsException | AssertionError e) {
            return "that task number doesn't exist Deji!";
        }
    }

    private static String handleUnmark(String[] words, TaskList tasks) {
        if (tasks.isEmpty()) {
            return ERR_EMPTY_LIST;
        }
        try {
            int taskNum = Integer.parseInt(words[1]);
            Task t = tasks.setUndone(taskNum - 1);
            return "alright Deji, unmarked this one:\n" + t;
        } catch (NumberFormatException e) {
            return ERR_INVALID_NUMBER;
        } catch (IndexOutOfBoundsException | AssertionError e) {
            return "that task number doesn't exist Deji!";
        }
    }

    private static String handleTodo(String input, TaskList tasks) {
        String desc = input.substring(TODO_PREFIX_LEN).trim();
        if (desc.isEmpty()) {
            return ERR_EMPTY_DESC;
        }
        Task t = new ToDo(desc);
        if (tasks.hasDuplicate(t)) {
            return ERR_DUPLICATE;
        }
        tasks.add(t);
        return formatTaskAdded(t, tasks.size());
    }

    private static String handleDeadline(String input, TaskList tasks) {
        if (input.length() <= DEADLINE_PREFIX_LEN) {
            return ERR_DEADLINE_FORMAT;
        }
        String rest = input.substring(DEADLINE_PREFIX_LEN).trim();
        String[] parts = rest.split(" /by ");

        if (parts.length != 2) {
            return ERR_DEADLINE_FORMAT;
        }

        String desc = parts[0].trim();
        String by = parts[1].trim();
        if (desc.isEmpty() || by.isEmpty()) {
            return ERR_DEADLINE_FORMAT;
        }

        Task t = new Deadline(desc, by);
        if (tasks.hasDuplicate(t)) {
            return ERR_DUPLICATE;
        }
        tasks.add(t);
        return formatTaskAdded(t, tasks.size());
    }

    private static String handleEvent(String input, TaskList tasks) {
        if (input.length() <= EVENT_PREFIX_LEN) {
            return ERR_EVENT_FORMAT;
        }
        String rest = input.substring(EVENT_PREFIX_LEN).trim();
        String[] parts = rest.split(" /from | /to ");

        if (parts.length != 3) {
            return ERR_EVENT_FORMAT;
        }

        String desc = parts[0].trim();
        String from = parts[1].trim();
        String to = parts[2].trim();
        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
            return ERR_EVENT_FORMAT;
        }

        // validate date order if both are valid dates
        try {
            LocalDate startDate = LocalDate.parse(from);
            LocalDate endDate = LocalDate.parse(to);
            if (startDate.isAfter(endDate)) {
                return ERR_EVENT_DATE_ORDER;
            }
        } catch (DateTimeParseException e) {
            // one or both are not dates, skip date order check
        }

        Task t = new Event(desc, from, to);
        if (tasks.hasDuplicate(t)) {
            return ERR_DUPLICATE;
        }
        tasks.add(t);
        return formatTaskAdded(t, tasks.size());
    }

    private static String handleDelete(String[] words, TaskList tasks) {
        if (tasks.isEmpty()) {
            return ERR_EMPTY_LIST;
        }
        try {
            int idx = Integer.parseInt(words[1]);
            Task t = tasks.remove(idx - 1);
            return "gone and dusted Deji:\n" + t + "\nyou now have " + tasks.size() + " tasks";
        } catch (NumberFormatException e) {
            return ERR_INVALID_NUMBER;
        } catch (IndexOutOfBoundsException | AssertionError e) {
            return "that task number doesn't exist Deji!";
        }
    }

    private static String handleOn(String[] words, TaskList tasks) {
        if (words.length < 2) {
            return ERR_DATE_FORMAT;
        }
        try {
            LocalDate date = LocalDate.parse(words[1]);
            TaskList filtered = tasks.filterByDate(date);
            return formatSearchResults(filtered, "tasks on " + date + " Deji:");
        } catch (DateTimeParseException e) {
            return ERR_DATE_FORMAT;
        }
    }

    private static String handleFind(String input, TaskList tasks) {
        if (input.length() <= FIND_PREFIX_LEN) {
            return "find what Deji? give me a keyword!";
        }
        String keyword = input.substring(FIND_PREFIX_LEN).trim();
        if (keyword.isEmpty()) {
            return "find what Deji? give me a keyword!";
        }
        TaskList matches = tasks.search(keyword);
        return formatSearchResults(matches, "found these for ya Deji:");
    }

    private static String formatTaskAdded(Task t, int totalTasks) {
        return "LESGOOO! added that one Deji:\n" + t
                + "\nyou now have " + totalTasks + " tasks in the bag!";
    }

    /**
     * Checks if a response from processInput is an error message.
     * Used by the GUI to apply error styling to error responses.
     *
     * @param response The response string from processInput.
     * @return true if the response is an error message.
     */
    public static boolean isErrorResponse(String response) {
        return response.equals(ERR_UNKNOWN)
                || response.equals(ERR_EMPTY)
                || response.equals(ERR_EMPTY_DESC)
                || response.equals(ERR_EMPTY_LIST)
                || response.equals(ERR_DUPLICATE)
                || response.equals(ERR_DEADLINE_FORMAT)
                || response.equals(ERR_EVENT_FORMAT)
                || response.equals(ERR_EVENT_DATE_ORDER)
                || response.equals(ERR_DATE_FORMAT)
                || response.equals(ERR_INVALID_NUMBER)
                || response.equals("that task number doesn't exist Deji!")
                || response.equals("find what Deji? give me a keyword!");
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
        input = normalize(input);
        if (input.isEmpty()) {
            ui.printEmpty();
            return true;
        }

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
        if (tasks.isEmpty()) {
            ui.printError(ERR_EMPTY_LIST);
            return;
        }
        try {
            int num = Integer.parseInt(words[1]);
            Task t = tasks.setDone(num - 1);
            ui.printMarked(t);
        } catch (NumberFormatException e) {
            ui.printError(ERR_INVALID_NUMBER);
        } catch (IndexOutOfBoundsException | AssertionError e) {
            ui.printError("that task number doesn't exist Deji!");
        }
    }

    private static void cliUnmark(String[] words, TaskList tasks, Ui ui) {
        if (tasks.isEmpty()) {
            ui.printError(ERR_EMPTY_LIST);
            return;
        }
        try {
            int num = Integer.parseInt(words[1]);
            Task t = tasks.setUndone(num - 1);
            ui.printUnmarked(t);
        } catch (NumberFormatException e) {
            ui.printError(ERR_INVALID_NUMBER);
        } catch (IndexOutOfBoundsException | AssertionError e) {
            ui.printError("that task number doesn't exist Deji!");
        }
    }

    private static void cliTodo(String input, TaskList tasks, Ui ui) {
        String desc = input.substring(TODO_PREFIX_LEN).trim();
        if (desc.isEmpty()) {
            ui.printError(ERR_EMPTY_DESC);
            return;
        }
        Task t = new ToDo(desc);
        if (tasks.hasDuplicate(t)) {
            ui.printError(ERR_DUPLICATE);
            return;
        }
        tasks.add(t);
        ui.printAdded(t, tasks.size());
    }

    private static void cliDeadline(String input, TaskList tasks, Ui ui) {
        if (input.length() <= DEADLINE_PREFIX_LEN) {
            ui.printError(ERR_DEADLINE_FORMAT);
            return;
        }
        String rest = input.substring(DEADLINE_PREFIX_LEN).trim();
        String[] parts = rest.split(" /by ");

        if (parts.length != 2) {
            ui.printError(ERR_DEADLINE_FORMAT);
            return;
        }

        String desc = parts[0].trim();
        String by = parts[1].trim();
        if (desc.isEmpty() || by.isEmpty()) {
            ui.printError(ERR_DEADLINE_FORMAT);
            return;
        }

        Task t = new Deadline(desc, by);
        if (tasks.hasDuplicate(t)) {
            ui.printError(ERR_DUPLICATE);
            return;
        }
        tasks.add(t);
        ui.printAdded(t, tasks.size());
    }

    private static void cliEvent(String input, TaskList tasks, Ui ui) {
        if (input.length() <= EVENT_PREFIX_LEN) {
            ui.printError(ERR_EVENT_FORMAT);
            return;
        }
        String rest = input.substring(EVENT_PREFIX_LEN).trim();
        String[] parts = rest.split(" /from | /to ");

        if (parts.length != 3) {
            ui.printError(ERR_EVENT_FORMAT);
            return;
        }

        String desc = parts[0].trim();
        String from = parts[1].trim();
        String to = parts[2].trim();
        if (desc.isEmpty() || from.isEmpty() || to.isEmpty()) {
            ui.printError(ERR_EVENT_FORMAT);
            return;
        }

        try {
            LocalDate startDate = LocalDate.parse(from);
            LocalDate endDate = LocalDate.parse(to);
            if (startDate.isAfter(endDate)) {
                ui.printError(ERR_EVENT_DATE_ORDER);
                return;
            }
        } catch (DateTimeParseException e) {
            // one or both are not dates, skip date order check
        }

        Task t = new Event(desc, from, to);
        if (tasks.hasDuplicate(t)) {
            ui.printError(ERR_DUPLICATE);
            return;
        }
        tasks.add(t);
        ui.printAdded(t, tasks.size());
    }

    private static void cliDelete(String[] words, TaskList tasks, Ui ui) {
        if (tasks.isEmpty()) {
            ui.printError(ERR_EMPTY_LIST);
            return;
        }
        try {
            int idx = Integer.parseInt(words[1]);
            Task t = tasks.remove(idx - 1);
            ui.printDeleted(t, tasks.size());
        } catch (NumberFormatException e) {
            ui.printError(ERR_INVALID_NUMBER);
        } catch (IndexOutOfBoundsException | AssertionError e) {
            ui.printError("task number out of range (1-" + tasks.size() + ")");
        }
    }

    private static void cliOn(String[] words, TaskList tasks, Ui ui) {
        if (words.length < 2) {
            ui.printError(ERR_DATE_FORMAT);
            return;
        }
        try {
            LocalDate date = LocalDate.parse(words[1]);
            TaskList filtered = tasks.filterByDate(date);
            ui.printTasksOnDate(filtered, date.toString());
        } catch (DateTimeParseException e) {
            ui.printError(ERR_DATE_FORMAT);
        }
    }

    private static void cliFind(String input, TaskList tasks, Ui ui) {
        if (input.length() <= FIND_PREFIX_LEN) {
            ui.printError("find what Deji? give me a keyword!");
            return;
        }
        String keyword = input.substring(FIND_PREFIX_LEN).trim();
        if (keyword.isEmpty()) {
            ui.printError("find what Deji? give me a keyword!");
            return;
        }
        TaskList matches = tasks.search(keyword);
        ui.printSearchResults(matches);
    }
}
