package randy;

import java.util.Scanner;

/**
 * Handles all CLI user interface operations for Vikkstar task manager.
 * All messages use Vikkstar's energetic YouTuber personality.
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
        System.out.println("YO WHAT'S GOOD! I'm Vikkstar, your task manager!");
        System.out.println("let's get productive Deji!");
    }

    public void sayBye() {
        System.out.println(DIVIDER);
        System.out.println("aight Deji, catch you later! stay winning!");
        System.out.println(DIVIDER);
    }

    public void askForInput() {
        System.out.println("whatcha need fam?");
        System.out.println(DIVIDER);
    }

    public void printSavedTasks(TaskList tasks) {
        if (tasks.size() > 0) {
            System.out.println("oi Deji, found your saved tasks:");
            System.out.println(DIVIDER);
            for (int i = 0; i < tasks.size(); i++) {
                System.out.println(tasks.get(i));
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
        System.out.println("\tbruv that's not a thing... try again Deji!");
        line();
    }

    public void printEmpty() {
        line();
        System.out.println("\tyo Deji you forgot the details fam!");
        line();
    }

    public void printList(TaskList tasks) {
        line();
        System.out.println("\there's the lineup Deji:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + tasks.get(i));
        }
        line();
    }

    public void printMarked(Task t) {
        line();
        System.out.println("\tBIG W Deji! smashed it:");
        System.out.println("\t" + t);
        line();
    }

    public void printUnmarked(Task t) {
        line();
        System.out.println("\talright Deji, unmarked this one:");
        System.out.println("\t" + t);
        line();
    }

    public void printAdded(Task t, int total) {
        line();
        System.out.println("\tLESGOOO! added that one Deji:");
        System.out.println("\t" + t);
        System.out.println("\tyou now have " + total + " tasks in the bag!");
        line();
    }

    public void printDeleted(Task t, int total) {
        line();
        System.out.println("\tgone and dusted Deji:");
        System.out.println("\t" + t);
        System.out.println("\tyou now have " + total + " tasks");
        line();
    }

    public void printTasksOnDate(TaskList results, String date) {
        line();
        System.out.println("\ttasks on " + date + " Deji:");
        if (results.size() == 0) {
            System.out.println("\tnothing on this day fam!");
        } else {
            for (int i = 0; i < results.size(); i++) {
                System.out.println("\t" + (i + 1) + ". " + results.get(i));
            }
        }
        line();
    }

    public void printLoadError() {
        System.out.println("couldn't load saved tasks Deji, starting fresh!");
    }

    public void printSearchResults(TaskList results) {
        line();
        System.out.println("\tfound these for ya Deji:");
        if (results.size() == 0) {
            System.out.println("\tnah nothing matched fam");
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
