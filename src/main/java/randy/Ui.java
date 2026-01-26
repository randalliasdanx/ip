package randy;

import java.util.Scanner;

/**
 * Handles all user interface operations - reading input and displaying output.
 */
public class Ui {
    private static final String LINE = "_________________________________________________________";
    private Scanner scanner;

    /**
     * Creates a new Ui and initializes the scanner.
     */
    public Ui() {
        scanner = new Scanner(System.in);
    }

    /**
     * Reads and returns user input.
     * @return The trimmed user input.
     */
    public String readCommand() {
        return scanner.nextLine().trim();
    }

    public void printLine() {
        System.out.println("\t" + LINE);
    }

    /**
     * Shows the welcome message.
     */
    public void showWelcome() {
        System.out.println(LINE);
        System.out.println("Hello! I'm Randy");
    }

    /**
     * Shows the goodbye message.
     */
    public void showBye() {
        System.out.println(LINE);
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    public void showPrompt() {
        System.out.println("What can I do for you?");
        System.out.println(LINE);
    }

    /**
     * Shows tasks loaded from storage.
     * @param arr The loaded tasks.
     */
    public void showLoadedTasks(TaskList arr) {
        if (arr.size() > 0) {
            System.out.println("Here are your tasks!");
            System.out.println(LINE);
            for (int i = 0; i < arr.size(); i++) {
                System.out.println(arr.get(i));
            }
            System.out.println(LINE);
        }
    }

    /**
     * Shows an error message.
     * @param msg The error message.
     */
    public void showError(String msg) {
        printLine();
        System.out.println("\t" + msg);
        printLine();
    }

    public void showUnknownCommand() {
        printLine();
        System.out.println("\t" + "I do not understand what you're trying to say, please try again! ~ From Randy");
        printLine();
    }

    public void showMissingDescription() {
        printLine();
        System.out.println("\t" + "Please add the necessary description and information in the command! ~ From Randy");
        printLine();
    }

    /**
     * Shows all tasks in the list.
     * @param arr The task list to display.
     */
    public void showList(TaskList arr) {
        printLine();
        System.out.println("\t" + "Here are the tasks in your list:");
        for (int i = 0; i < arr.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + arr.get(i));
        }
        printLine();
    }

    public void showMarked(Task t) {
        printLine();
        System.out.println("\t" + "Nice! I've marked this task as done:");
        System.out.println("\t" + t);
        printLine();
    }

    public void showUnmarked(Task t) {
        printLine();
        System.out.println("\t" + "OK, I've marked this task as not done yet:");
        System.out.println("\t" + t);
        printLine();
    }

    /**
     * Shows confirmation that a task was added.
     * @param t The added task.
     * @param size The new total number of tasks.
     */
    public void showAdded(Task t, int size) {
        printLine();
        System.out.println("\t" + "Got it. I've added this task: ");
        System.out.println("\t" + t);
        System.out.println("\t" + "Now you have " + size + " tasks in the list.");
        printLine();
    }

    public void showDeleted(Task t, int size) {
        printLine();
        System.out.println("\t" + "Noted. I've removed this task:");
        System.out.println("\t" + t);
        System.out.println("\t" + "Now you have " + size + " tasks in the list.");
        printLine();
    }

    public void showTasksOn(TaskList results, String date) {
        printLine();
        System.out.println("\t" + "Tasks on " + date + ":");
        if (results.size() == 0) {
            System.out.println("\t" + "No tasks found on this date.");
        } else {
            for (int i = 0; i < results.size(); i++) {
                System.out.println("\t" + (i + 1) + ". " + results.get(i));
            }
        }
        printLine();
    }

    public void showLoadingError() {
        System.out.println("Error loading tasks. Starting with empty list.");
    }

    /**
     * Shows tasks matching the search keyword.
     * @param results The matching tasks.
     */
    public void showFound(TaskList results) {
        printLine();
        System.out.println("\t" + "Here are the matching tasks in your list:");
        if (results.size() == 0) {
            System.out.println("\t" + "No matching tasks found.");
        } else {
            for (int i = 0; i < results.size(); i++) {
                System.out.println("\t" + (i + 1) + ". " + results.get(i));
            }
        }
        printLine();
    }

    /**
     * Closes the scanner.
     */
    public void close() {
        scanner.close();
    }
}
