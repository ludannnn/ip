import java.util.Scanner;
import java.util.ArrayList;

public class Yoda {
    public static void main(String[] args) {
        final String name = "Yoda";
        ArrayList<Task> tasks = new ArrayList<>();

        System.out.println("""
                _______________________
                Yoda, I am.
                _______________________
                Do for you, what can I?
                """);

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) continue;

            String[] parts = line.split("\\s+", 2);
            String cmd = parts[0].toLowerCase();
            String arg = (parts.length > 1) ? parts[1].trim() : "";

            switch (cmd) {
                case "bye":
                    System.out.println("Farewell, I bid you.");
                    running = false;
                    break;

                case "list":
                    printLine();
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                    printLine();
                    break;

                case "mark": {
                    int idx = Integer.parseInt(arg) - 1;
                    if (idx == -1) break;
                    tasks.get(idx).markDone();
                    printLine();
                    System.out.println("Marked this task as done, I have:\n" + tasks.get(idx));
                    printLine();
                }
                    break;

                case "unmark": {
                    int idx = Integer.parseInt(arg) - 1;
                    if (idx == -1) break;
                    tasks.get(idx).markNotDone();
                    printLine();
                    System.out.println("not done yet, is this task:\n" + tasks.get(idx));
                    printLine();
                }
                break;

                default:
                    tasks.add(new Task(line));
                    printLine();
                    System.out.println("added: " + line);
                    printLine();
                    break;
            }
        }

        scanner.close();
    }

    private static void printLine() {
        System.out.println("_".repeat(61));
    }
}



