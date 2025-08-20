import java.util.Scanner;

public class Yoda {
    public static void main(String[] args) {
        final String name = "Yoda";

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
            } else {
                System.out.println("_______________________");
                System.out.println(input);
            }
        }


        System.out.println("Bye. See you again!");
        scanner.close();
    }
}
