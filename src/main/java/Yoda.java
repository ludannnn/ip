import java.util.Scanner;
import java.util.ArrayList;

public class Yoda {
    public static void main(String[] args) {
        final String name = "Yoda";
        ArrayList<String> tasks = new ArrayList<>();

        System.out.println("""
                _______________________
                Hi i am Yoda
                _______________________
                What can i do for you?
                """);

        Scanner scanner = new Scanner(System.in);
        boolean isRunning = true;
        String input;

        while (isRunning) {
            input = scanner.nextLine();
            if (input.equalsIgnoreCase("bye")) {
                isRunning = false;
                break;
            } else if (input.equalsIgnoreCase("list")) {
                int num = 1;
                for (String task : tasks) {
                    System.out.println("_______________________");
                    System.out.println(num + ". " + task);
                    num++;
                }
            } else {
                System.out.println("_______________________");
                System.out.println("added: " + input);
                System.out.println("_______________________");
                tasks.add(input);
            }
        }
        System.out.println("Bye. See you again!");
        scanner.close();
    }
}


