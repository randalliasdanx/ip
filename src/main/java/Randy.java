import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Randy {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Task> arr = new ArrayList<>();
        Set<String> s = new HashSet<>();
        s.add("bye");
        s.add("list");
        s.add("mark");
        s.add("unmark");
        s.add("todo");
        s.add("deadline");
        s.add("event");
        System.out.println("_________________________________________________________");
        System.out.println("Hello! I'm Randy");
        System.out.println("What can I do for you?");
        System.out.println("_________________________________________________________");
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            input.trim();
            String[] split = input.split(" ");
            if (split.length == 1 && !s.contains(split[0])) {
                System.out.println(
                        "\t" + "_____________________________________________________________________________");
                System.out.println(
                        "\t" + "I do not understand what you're trying to say, please try again! ~ From Randy");
                System.out.println(
                        "\t" + "_____________________________________________________________________________");
            } else if (split.length == 1 && !split[0].equals("list")) {
                System.out.println(
                        "\t" + "_________________________________________________________________________________");
                System.out.println(
                        "\t" + "Please add the necessary description and information in the command! ~ From Randy");
                System.out.println(
                        "\t" + "_________________________________________________________________________________");
            } else if (input.equals("list")) {
                System.out.println("\t" + "_________________________________________________________");
                System.out.println("\t" + "Here are the tasks in your list:");
                for (int i = 0; i < arr.size(); i++) {
                    System.out.println("\t" + Integer.toString(i + 1) + ". " + arr.get(i));
                }
                System.out.println("\t" + "_________________________________________________________");
            } else if (split[0].equals("mark")) {
                int taskNum = Integer.parseInt(split[1]);
                Task t = arr.get(taskNum - 1);
                t.mark();
                System.out.println("\t" + "_________________________________________________________");
                System.out.println("\t" + "Nice! I've marked this task as done:");
                System.out.println("\t" + t);
            } else if (split[0].equals("unmark")) {
                int taskNum = Integer.parseInt(split[1]);
                Task t = arr.get(taskNum - 1);
                t.unmark();
                System.out.println("\t" + "_________________________________________________________");
                System.out.println("\t" + "OK, I've marked this task as not done yet:");
                System.out.println("\t" + t);
            } else if (split[0].equals("todo")) {
                String inp = input.substring(5);
                Task t = new ToDo(inp);
                arr.add(t);
                System.out.println("\t" + "_________________________________________________________");
                System.out.println("\t" + "Got it. I've added this task: ");
                System.out.println("\t" + t);
                System.out.println("\t" + "Now you have " + arr.size() + " tasks in the list.");
            } else if (split[0].equals("deadline")) {
                String rest = input.substring(9);
                String[] parts = rest.split(" /by ");
                String description = parts[0];
                String by = parts[1];
                Task t = new Deadline(description, by);
                arr.add(t);
                System.out.println("\t" + "_________________________________________________________");
                System.out.println("\t" + "Got it. I've added this task: ");
                System.out.println("\t" + t);
                System.out.println("\t" + "Now you have " + arr.size() + " tasks in the list.");
            } else if (split[0].equals("event")) {
                String rest = input.substring(6);
                String[] parts = rest.split(" /from | /to ");
                String description = parts[0];
                String from = parts[1];
                String to = parts[2];
                Task t = new Event(description, from, to);
                arr.add(t);
                System.out.println("\t" + "_________________________________________________________");
                System.out.println("\t" + "Got it. I've added this task: ");
                System.out.println("\t" + t);
                System.out.println("\t" + "Now you have " + arr.size() + " tasks in the list.");
            } else if (split[0].equals("delete")) {
                int ind = Integer.valueOf(split[1]);
                Task t = arr.get(ind - 1);
                arr.remove(ind - 1);
                System.out.println("\t" + "_________________________________________________________");
                System.out.println("\t" + "Noted. I've removed this  task:");
                System.out.println("\t" + t);
                System.out.println("\t" + "Now you have " + arr.size() + " tasks in the list.");
            } else {
                System.out.println("\t" + "_________________________________________________________");
                System.out.println("\t" + "added: " + input);
                arr.add(new Task(input));
                System.out.println("\t" + "_________________________________________________________");

            }
            input = scanner.nextLine();
        }

        System.out.println("_________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("_________________________________________________________");
    }
}
