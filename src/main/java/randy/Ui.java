package randy;

import java.util.Scanner;

/**
 * Handles all user interface operations for CLI mode,
 * including reading input and printing formatted output.
 */
public class Ui {
    private static final String LINE = "____________________________________________________________";
    private Scanner sc;

    /** Initialises the Ui with a Scanner for reading from standard input. */
    public Ui() {
        sc = new Scanner(System.in);
    }

    /**
     * Reads the next line of user input.
     *
     * @return The trimmed input string.
     */
    public String read() {
        return sc.nextLine().trim();
    }

    private void divider() {
        System.out.println("\t" + LINE);
    }

    /** Prints a greeting message. */
    public void greet() {
        System.out.println(LINE);
        System.out.println("yo! i'm Randy");
    }

    /** Prints a farewell message. */
    public void sayBye() {
        System.out.println(LINE);
        System.out.println("peace out! see ya later");
        System.out.println(LINE);
    }

    /** Prompts the user for input. */
    public void askForInput() {
        System.out.println("whatcha need?");
        System.out.println(LINE);
    }

    /**
     * Prints previously saved tasks on startup if there are any.
     *
     * @param tasks The loaded task list.
     */
    public void printSavedTasks(TaskList tasks) {
        if (tasks.size() > 0) {
            System.out.println("found your saved tasks:");
            System.out.println(LINE);
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(tasks.get(i));
            }
            System.out.println(LINE);
        }
    }

    /**
     * Prints a generic error message.
     *
     * @param msg The error message to display.
     */
    public void printError(String msg) {
        divider();
        System.out.println("\t" + msg);
        divider();
    }

    /** Prints an error for unrecognised commands. */
    public void printUnknown() {
        divider();
        System.out.println("\thuh? idk what that means bro");
        divider();
    }

    /** Prints an error for commands that are missing required details. */
    public void printEmpty() {
        divider();
        System.out.println("\tuh you forgot to add details");
        divider();
    }

    /**
     * Prints all tasks in the list.
     *
     * @param tasks The task list to display.
     */
    public void printList(TaskList tasks) {
        divider();
        System.out.println("\there's what you got:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + tasks.get(i));
        }
        divider();
    }

    /**
     * Prints confirmation that a task has been marked as done.
     *
     * @param t The task that was marked.
     */
    public void printMarked(Task t) {
        divider();
        System.out.println("\tnice! marked as done:");
        System.out.println("\t" + t);
        divider();
    }

    /**
     * Prints confirmation that a task has been unmarked.
     *
     * @param t The task that was unmarked.
     */
    public void printUnmarked(Task t) {
        divider();
        System.out.println("\tok unmarked this one:");
        System.out.println("\t" + t);
        divider();
    }

    /**
     * Prints confirmation that a task has been added.
     *
     * @param t     The task that was added.
     * @param total The new total number of tasks.
     */
    public void printAdded(Task t, int total) {
        divider();
        System.out.println("\tadded:");
        System.out.println("\t" + t);
        System.out.println("\tyou now have " + total + " tasks");
        divider();
    }

    /**
     * Prints confirmation that a task has been deleted.
     *
     * @param t     The task that was deleted.
     * @param total The new total number of tasks.
     */
    public void printDeleted(Task t, int total) {
        divider();
        System.out.println("\tdeleted:");
        System.out.println("\t" + t);
        System.out.println("\tyou now have " + total + " tasks");
        divider();
    }

    /**
     * Prints tasks that fall on a specific date.
     *
     * @param results The filtered task list.
     * @param date    The date string being queried.
     */
    public void printTasksOnDate(TaskList results, String date) {
        divider();
        System.out.println("\ttasks on " + date + ":");
        if (results.size() == 0) {
            System.out.println("\tnothing on this day");
        } else {
            for (int i = 0; i < results.size(); i++) {
                System.out.println("\t" + (i + 1) + ". " + results.get(i));
            }
        }
        divider();
    }

    /** Prints a message when saved tasks could not be loaded. */
    public void printLoadError() {
        System.out.println("couldnt load saved tasks, starting fresh");
    }

    /**
     * Prints search results for a find query.
     *
     * @param results The matching tasks.
     */
    public void printSearchResults(TaskList results) {
        divider();
        System.out.println("\tfound these:");
        if (results.size() == 0) {
            System.out.println("\tno matches");
        } else {
            for (int i = 0; i < results.size(); i++) {
                System.out.println("\t" + (i + 1) + ". " + results.get(i));
            }
        }
        divider();
    }

    /** Closes the Scanner resource. */
    public void shutdown() {
        sc.close();
    }
}
