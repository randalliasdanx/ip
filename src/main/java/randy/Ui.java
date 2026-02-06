package randy;

import java.util.Scanner;

/**
 * Handles all the printing stuff.
 */
public class Ui {
    private static final String DIVIDER = "____________________________________________________________";
    private Scanner sc;

    public Ui() {
        sc = new Scanner(System.in);
    }

    public String read() {
        return sc.nextLine().trim();
    }

    private void line() {
        System.out.println("\t" + DIVIDER);
    }

    public void greet() {
        System.out.println(DIVIDER);
        System.out.println("yo! i'm Randy");
    }

    public void sayBye() {
        System.out.println(DIVIDER);
        System.out.println("peace out! see ya later");
        System.out.println(DIVIDER);
    }

    public void askForInput() {
        System.out.println("whatcha need?");
        System.out.println(DIVIDER);
    }

    public void printSavedTasks(TaskList tasks) {
        if (tasks.size() > 0) {
            System.out.println("found your saved tasks:");
            System.out.println(DIVIDER);
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println((i + 1) + ". " + tasks.get(i));
            }
            System.out.println(DIVIDER);
        }
    }

    public void printReminders(TaskList upcoming) {
        if (upcoming.size() > 0) {
            System.out.println("⚠️  heads up! deadlines coming up:");
            System.out.println(DIVIDER);
            for (int i = 0; i < upcoming.size(); i++) {
                System.out.println("• " + upcoming.get(i));
            }
            System.out.println(DIVIDER);
        }
    }

    public void printError(String msg) {
        line();
        System.out.println("\t" + msg);
        line();
    }

    public void printUnknown() {
        line();
        System.out.println("\thuh? idk what that means bro");
        line();
    }

    public void printEmpty() {
        line();
        System.out.println("\tuh you forgot to add details");
        line();
    }

    public void printList(TaskList tasks) {
        line();
        System.out.println("\there's what you got:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + tasks.get(i));
        }
        line();
    }

    public void printMarked(Task t) {
        line();
        System.out.println("\tnice! marked as done:");
        System.out.println("\t" + t);
        line();
    }

    public void printUnmarked(Task t) {
        line();
        System.out.println("\tok unmarked this one:");
        System.out.println("\t" + t);
        line();
    }

    public void printAdded(Task t, int total) {
        line();
        System.out.println("\tadded:");
        System.out.println("\t" + t);
        System.out.println("\tyou now have " + total + " tasks");
        line();
    }

    public void printDeleted(Task t, int total) {
        line();
        System.out.println("\tdeleted:");
        System.out.println("\t" + t);
        System.out.println("\tyou now have " + total + " tasks");
        line();
    }

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

    public void printLoadError() {
        System.out.println("couldn't load saved tasks, starting fresh");
    }

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

    public void shutdown() {
        sc.close();
    }
}
