package randy;

import java.util.Scanner;

/**
 * Handles all user interface operations for CLI mode.
 * All messages use Vikkstar's energetic YouTuber personality.
 */
public class Ui {
    private static final String DIVIDER = "____________________________________________________________";
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

    private void line() {
        System.out.println("\t" + DIVIDER);
    }

    /** Prints a greeting message. */
    public void greet() {
        System.out.println(DIVIDER);
        System.out.println("YO WHAT'S GOOD! I'm Vikkstar, your task manager!");
        System.out.println("let's get productive Deji!");
    }

    /** Prints a farewell message. */
    public void sayBye() {
        System.out.println(DIVIDER);
        System.out.println("aight Deji, catch you later! stay winning!");
        System.out.println(DIVIDER);
    }

    /** Prompts the user for input. */
    public void askForInput() {
        System.out.println("whatcha need fam?");
        System.out.println(DIVIDER);
    }

    /**
     * Prints previously saved tasks on startup if there are any.
     *
     * @param tasks The loaded task list.
     */
    public void printSavedTasks(TaskList tasks) {
        if (!tasks.isEmpty()) {
            System.out.println("oi Deji, found your saved tasks:");
            System.out.println(DIVIDER);
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(tasks.get(i));
            }
            System.out.println(DIVIDER);
        }
    }

    /**
     * Prints a generic error message.
     *
     * @param msg The error message to display.
     */
    public void printError(String msg) {
        line();
        System.out.println("\t" + msg);
        line();
    }

    /** Prints an error for unrecognised commands. */
    public void printUnknown() {
        line();
        System.out.println("\tbruv that's not a thing... try again Deji!");
        line();
    }

    /** Prints an error for commands that are missing required details. */
    public void printEmpty() {
        line();
        System.out.println("\tyo Deji you forgot the details fam!");
        line();
    }

    /**
     * Prints all tasks in the list.
     *
     * @param tasks The task list to display.
     */
    public void printList(TaskList tasks) {
        line();
        System.out.println("\there's the lineup Deji:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + tasks.get(i));
        }
        line();
    }

    /**
     * Prints confirmation that a task has been marked as done.
     *
     * @param t The task that was marked.
     */
    public void printMarked(Task t) {
        line();
        System.out.println("\tBIG W Deji! smashed it:");
        System.out.println("\t" + t);
        line();
    }

    /**
     * Prints confirmation that a task has been unmarked.
     *
     * @param t The task that was unmarked.
     */
    public void printUnmarked(Task t) {
        line();
        System.out.println("\talright Deji, unmarked this one:");
        System.out.println("\t" + t);
        line();
    }

    /**
     * Prints confirmation that a task has been added.
     *
     * @param t     The task that was added.
     * @param total The new total number of tasks.
     */
    public void printAdded(Task t, int total) {
        line();
        System.out.println("\tLESGOOO! added that one Deji:");
        System.out.println("\t" + t);
        System.out.println("\tyou now have " + total + " tasks in the bag!");
        line();
    }

    /**
     * Prints confirmation that a task has been deleted.
     *
     * @param t     The task that was deleted.
     * @param total The new total number of tasks.
     */
    public void printDeleted(Task t, int total) {
        line();
        System.out.println("\tgone and dusted Deji:");
        System.out.println("\t" + t);
        System.out.println("\tyou now have " + total + " tasks");
        line();
    }

    /**
     * Prints tasks that fall on a specific date.
     *
     * @param results The filtered task list.
     * @param date    The date string being queried.
     */
    public void printTasksOnDate(TaskList results, String date) {
        line();
        System.out.println("\ttasks on " + date + " Deji:");
        if (results.isEmpty()) {
            System.out.println("\tnothing on this day fam!");
        } else {
            for (int i = 0; i < results.size(); i++) {
                System.out.println("\t" + (i + 1) + ". " + results.get(i));
            }
        }
        line();
    }

    /** Prints a message when saved tasks could not be loaded. */
    public void printLoadError() {
        System.out.println("couldn't load saved tasks Deji, starting fresh!");
    }

    /**
     * Prints search results for a find query.
     *
     * @param results The matching tasks.
     */
    public void printSearchResults(TaskList results) {
        line();
        System.out.println("\tfound these for ya Deji:");
        if (results.isEmpty()) {
            System.out.println("\tnah nothing matched fam");
        } else {
            for (int i = 0; i < results.size(); i++) {
                System.out.println("\t" + (i + 1) + ". " + results.get(i));
            }
        }
        line();
    }

    /** Closes the Scanner resource. */
    public void shutdown() {
        sc.close();
    }
}
