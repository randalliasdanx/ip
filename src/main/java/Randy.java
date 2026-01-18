import java.util.Scanner;
public class Randy {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Task[] arr = new Task[100];
        int index = 0;
        System.out.println("_________________________________________________________");
        System.out.println("Hello! I'm Randy");
        System.out.println("What can I do for you?");
        System.out.println("_________________________________________________________");
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            String[] split = input.split(" ");
            if (input.equals("list")) {
                System.out.println("\t" + "_________________________________________________________");
                System.out.println("\t" + "Here are the tasks in your list:");
                for (int i = 0; i < index; i++) {
                    System.out.println("\t" + Integer.toString(i + 1)  + ". " + arr[i]);
                }
                System.out.println("\t" + "_________________________________________________________");
            }
            else if (split[0].equals("mark")) {
                int taskNum = Integer.parseInt(split[1]);
                Task t = arr[taskNum - 1];
                t.mark();
                System.out.println("\t" + "_________________________________________________________");
                System.out.println("\t" + "Nice! I've marked this task as done:");
                System.out.println("\t" + t);
            }
            else if (split[0].equals("unmark")) {
                int taskNum = Integer.parseInt(split[1]);
                Task t = arr[taskNum - 1];
                t.unmark();
                System.out.println("\t" + "_________________________________________________________");
                System.out.println("\t" + "OK, I've marked this task as not done yet:");
                System.out.println("\t" + t);
            }
            else {
                System.out.println("\t" + "_________________________________________________________");
                System.out.println("\t" + "added: " + input);
                arr[index] = new Task(input);
                index++;
                System.out.println("\t" + "_________________________________________________________");

            }
            input = scanner.nextLine();
        }

        System.out.println("_________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("_________________________________________________________");
    }
}
