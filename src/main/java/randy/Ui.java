package randy;

import java.util.Scanner;

/**
 * Handles all user interface operations for CLI mode.
 * 
 * This class is responsible for:
 * - Reading user input from console
 * - Displaying formatted output to console
 * - Showing error messages and status updates
 * 
 * Uses a divider to visually separate sections of output.
 */
public class Ui {
    private static final String DIVIDER = "____________________________________________________________";
    private Scanner sc;

    /**
     * Creates a new UI handler.
     * Initializes a Scanner for reading user input from standard input.
     */
    public Ui() {
        sc = new Scanner(System.in);
    }

    /**
     * Reads a line of input from the user.
     * 
     * @return the user's input with leading/trailing whitespace removed
     */
    public String read() {
        return sc.nextLine().trim();
    }

    /**
     * Prints a divider line for visual separation.
     */
    private void line() {
        System.out.println("\t" + DIVIDER);
    }

    /**
     * Displays the greeting message when the application starts.
     */
    public void greet() {
        System.out.println(DIVIDER);
        System.out.println("yo! i'm Randy");
    }

    /**
     * Displays the goodbye message when the application exits.
     */
    public void sayBye() {
        System.out.println(DIVIDER);
        System.out.println("peace out! see ya later");
        System.out.println(DIVIDER);
    }

    /**
     * Prompts the user for input.
     */
    public void askForInput() {
        System.out.println("whatcha need?");
        System.out.println(DIVIDER);
    }

    /**
     * Displays all previously saved tasks.
     * Called when the application starts to remind user of existing tasks.
     * 
     * @param tasks the list of saved tasks
     */
    public void printSavedTasks(TaskList tasks) {
        if (tasks.size() > 0) {
            System.out.println("found your saved tasks:");
            System.out.println(DIVIDER);
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(tasks.get(i));
            }
            System.out.println(DIVIDER);
        }
    }

    /**
     * Displays an error message.
     * 
     * @param msg the error message to display
     */
    public void printError(String msg) {
        line();
        System.out.println("\t" + msg);
        line();
    }

    /**
     * Displays an error message for unknown commands.
     */
    public void printUnknown() {
        line();
        System.out.println("\thuh? idk what that means bro");
        line();
    }

    /**
     * Displays an error message for empty command details.
     */
    public void printEmpty() {
        line();
        System.out.println("\tuh you forgot to add details");
        line();
    }

    /**
     * Displays all tasks in the current list.
     * 
     * @param tasks the task list to display
     */
    public void printList(TaskList tasks) {
        line();
        System.out.println("\there's what you got:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + tasks.get(i));
        }
        line();
    }

    /**
     * Displays a task after it has been marked as done.
     * 
     * @param t the task that was marked done
     */
    public void printMarked(Task t) {
        line();
        System.out.println("\tnice! marked as done:");
        System.out.println("\t" + t);
        line();
    }

    /**
     * Displays a task after it has been marked as not done.
     * 
     * @param t the task that was unmarked
     */
    public void printUnmarked(Task t) {
        line();
        System.out.println("\tok unmarked this one:");
        System.out.println("\t" + t);
        line();
    }

    /**
     * Displays a task after it has been added.
     * 
     * @param t the newly added task
     * @param total the total number of tasks now
     */
    public void printAdded(Task t, int total) {
        line();
        System.out.println("\tadded:");
        System.out.println("\t" + t);
        System.out.println("\tyou now have " + total + " tasks");
        line();
    }

    /**
     * Displays a task after it has been deleted.
     * 
     * @param t the deleted task
     * @param total the total number of tasks remaining
     */
    public void printDeleted(Task t, int total) {
        line();
        System.out.println("\tdeleted:");
        System.out.println("\t" + t);
        System.out.println("\tyou now have " + total + " tasks");
        line();
    }

    /**
     * Displays tasks occurring on a specific date.
     * 
     * @param results the filtered task list
     * @param date the date string to display
     */
    public void printTasksOnDate(TaskList results, String date) {
        line();
        System.out.println("\ttasks on " + date + ":");
        if (results.size() == 0) {
            System.out.println("\tnothing on this day");
        } else {
            for (int i = 0; i < results.size(); i++) {
                System.out.println("\t" + (i + 1) + ". " + results.get(i));
            }
        }
        line();
    }

    /**
     * Displays an error message when task loading fails.
     */
    public void printLoadError() {
        System.out.println("couldn't load saved tasks, starting fresh");
    }

    /**
     * Displays search results for tasks matching a keyword.
     * 
     * @param results the tasks matching the search
     */
    public void printSearchResults(TaskList results) {
        line();
        System.out.println("\tfound these:");
        if (results.size() == 0) {
            System.out.println("\tno matches");
        } else {
            for (int i = 0; i < results.size(); i++) {
                System.out.println("\t" + (i + 1) + ". " + results.get(i));
            }
        }
        line();
    }

    /**
     * Closes the Scanner when the application shuts down.
     */
    public void shutdown() {
        sc.close();
    }
}
