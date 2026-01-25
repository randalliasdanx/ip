import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Randy {
    private static ArrayList<Task> arr = new ArrayList<>();
    private static final String path = "../data/storage.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        File outputFile = new File(path);
        File parentDir = outputFile.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }
        
        System.out.println("_________________________________________________________");
        System.out.println("Hello! I'm Randy");
        loadTasksFromFile();
        if (arr.size() > 0) {
            System.out.println("Here are your tasks!");
            System.out.println("_________________________________________________________");
            for (int i = 0; i < arr.size(); i++) {
                System.out.println(arr.get(i));
            }
            
            System.out.println("_________________________________________________________");
        }

        System.out.println("What can I do for you?");
        System.out.println("_________________________________________________________");
        
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            input = input.trim();
            String[] split = input.split(" ");
            
            if (split.length == 1 && !Prefix.isValid(split[0])) {
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
                System.out.println("\t" + "_________________________________________________________");
            } else if (split[0].equals("unmark")) {
                int taskNum = Integer.parseInt(split[1]);
                Task t = arr.get(taskNum - 1);
                t.unmark();
                System.out.println("\t" + "_________________________________________________________");
                System.out.println("\t" + "OK, I've marked this task as not done yet:");
                System.out.println("\t" + t);
                System.out.println("\t" + "_________________________________________________________");
            } else if (split[0].equals("todo")) {
                String inp = input.substring(5);
                Task t = new ToDo(inp);
                arr.add(t);
                System.out.println("\t" + "_________________________________________________________");
                System.out.println("\t" + "Got it. I've added this task: ");
                System.out.println("\t" + t);
                System.out.println("\t" + "Now you have " + arr.size() + " tasks in the list.");
                System.out.println("\t" + "_________________________________________________________");
            } else if (split[0].equals("deadline")) {
                String rest = input.substring(9);
                String[] parts = rest.split(" /by ");
                if (parts.length != 2) {
                    System.out.println("\t" + "_________________________________________________________");
                    System.out.println("\t" + "Please insert a valid deadline (use: deadline <desc> /by <date>)");
                    System.out.println("\t" + "_________________________________________________________");
                } else {
                    String description = parts[0];
                    String by = parts[1];
                    Task t = new Deadline(description, by);
                    arr.add(t);
                    System.out.println("\t" + "_________________________________________________________");
                    System.out.println("\t" + "Got it. I've added this task: ");
                    System.out.println("\t" + t);
                    System.out.println("\t" + "Now you have " + arr.size() + " tasks in the list.");
                    System.out.println("\t" + "_________________________________________________________");
                }
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
                System.out.println("\t" + "_________________________________________________________");
            } else if (split[0].equals("delete")) {
                int ind = Integer.valueOf(split[1]);
                Task t = arr.get(ind - 1);
                arr.remove(ind - 1);
                System.out.println("\t" + "_________________________________________________________");
                System.out.println("\t" + "Noted. I've removed this task:");
                System.out.println("\t" + t);
                System.out.println("\t" + "Now you have " + arr.size() + " tasks in the list.");
                System.out.println("\t" + "_________________________________________________________");
            } else if (split[0].equals("on")) {
                // Find tasks occurring on a specific date
                try {
                    LocalDate searchDate = LocalDate.parse(split[1]);
                    System.out.println("\t" + "_________________________________________________________");
                    System.out.println("\t" + "Tasks on " + searchDate + ":");
                    int count = 0;
                    for (int i = 0; i < arr.size(); i++) {
                        Task t = arr.get(i);
                        if (t.occursOn(searchDate)) {
                            count++;
                            System.out.println("\t" + count + ". " + t);
                        }
                    }
                    if (count == 0) {
                        System.out.println("\t" + "No tasks found on this date.");
                    }
                    System.out.println("\t" + "_________________________________________________________");
                } catch (DateTimeParseException e) {
                    System.out.println("\t" + "_________________________________________________________");
                    System.out.println("\t" + "Please use yyyy-MM-dd format (e.g., on 2019-12-02)");
                    System.out.println("\t" + "_________________________________________________________");
                }
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
        writeToFile();
        System.out.println("_________________________________________________________");
        scanner.close();
    }

    private static void writeToFile() {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, false))) {
            for (Task task : arr) {
                bufferedWriter.write(task.toString());
                bufferedWriter.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void loadTasksFromFile() {
        File file = new File(path);
        if (!file.exists()) {
            return;  // Return if the file doesn't exist
        }

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Trim the line to remove leading/trailing whitespaces
                line = line.trim();

                // Skip empty lines to avoid errors
                if (line.isEmpty()) {
                    continue;
                }

                // Determine the task type and extract the description correctly
                Task task = null;
                if (line.startsWith("[T]")) {
                    // ToDo task: line starts with "[T]"
                    String description = line.substring(6).trim();  // Skip "[T][ ]" and any extra whitespace
                    task = new ToDo(description);
                } else if (line.startsWith("[D]")) {
                    // Deadline task: line starts with "[D]"
                    String description = line.substring(6, line.indexOf(" (by:")).trim();  
                    String by = line.substring(line.indexOf("(by:") + 5, line.indexOf(")")).trim();  // Extract the "by" date
                    task = new Deadline(description, by);
                } else if (line.startsWith("[E]")) {
                    // Event task: line starts with "[E]"
                    String description = line.substring(6, line.indexOf(" (from:")).trim();  // Get description before "(from:"
                    String from = line.substring(line.indexOf("(from:") + 6, line.indexOf(" to:")).trim();  // Extract start time
                    String to = line.substring(line.indexOf("to:") + 3, line.indexOf(")")).trim();  // Extract end time
                    task = new Event(description, from, to);
                }

                // If the task was successfully created, check the status and mark it accordingly
                if (task != null) {
                    String status = line.substring(4, 5);  // Get the task status ("[ ]" or "[X]")
                    if (status.equals("X")) {
                        task.mark();
                    }
                    // Add the task to the list
                    // System.out.println(task);
                    arr.add(task);
                } else {
                    System.out.println("Skipping invalid task format: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

}