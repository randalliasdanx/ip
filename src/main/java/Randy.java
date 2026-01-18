import java.util.Scanner;

public class Randy {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] arr = new String[100];
        int index = 0;
        System.out.println("_________________________________________________________");
        System.out.println("Hello! I'm Randy");
        System.out.println("What can I do for you?");
        System.out.println("_________________________________________________________");
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            if (input.equals("list")) {
                System.out.println("\t" + "_________________________________________________________");
                for (int i = 0; i < index; i++) {
                    System.out.println("\t" + Integer.toString(i + 1)  + ". " + arr[i]);
                }
                System.out.println("\t" + "_________________________________________________________");
            }
            else {
                System.out.println("\t" + "_________________________________________________________");
                System.out.println("\t" + "added: " + input);
                arr[index] = input;
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
