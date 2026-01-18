import java.util.Scanner;

public class Randy {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("_________________________________________________________");
        System.out.println("Hello! I'm Randy");
        System.out.println("What can I do for you?");
        System.out.println("_________________________________________________________");
        String input = scanner.nextLine();
        while (!input.equals("bye")) {
            System.out.println("\t" + "_________________________________________________________");
            System.out.println("\t" + input);
            System.out.println("\t" + "_________________________________________________________");
            input = scanner.nextLine();
        }

        System.out.println("_________________________________________________________");
        System.out.println("Bye. Hope to see you again soon!");
        System.out.println("_________________________________________________________");
    }
}
