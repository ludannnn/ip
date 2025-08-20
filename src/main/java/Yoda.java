import java.util.ArrayList;
import java.util.Scanner;

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

            // tokenise once
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
                    System.out.println("The tasks in your list, here are:");
                    for (int i = 0; i < tasks.size(); i++) {
                        System.out.println((i + 1) + ". " + tasks.get(i));
                    }
                    printLine();
                    break;

                case "mark": {
                    int idx = Integer.parseInt(arg) - 1;
                    tasks.get(idx).markDone();
                    printLine();
                    System.out.println("Marked this task as done, I have:\n" + tasks.get(idx));
                    printLine();
                }
                break;

                case "unmark": {
                    int idx = Integer.parseInt(arg) - 1;
                    tasks.get(idx).markNotDone();
                    printLine();
                    System.out.println("not done yet, is this task:\n" + tasks.get(idx));
                    printLine();
                }
                break;

                case "todo": {
                    Task t = new ToDoTask(arg);
                    printLine();
                    System.out.println("Added this task, I have:");
                    System.out.println("    " + t);
                    System.out.println((tasks.size() + 1) + " tasks in the list," + " Now you have.");
                    printLine();
                    tasks.add(t);
                }
                break;

                case "deadline": {
                    String[] d = arg.split("\\s*/by\\s*", 2);
                    Task t = new DeadlineTask(d[0].trim(), d[1].trim());
                    printLine();
                    System.out.println("Added this task, I have:");
                    System.out.println("    " + t);
                    System.out.println((tasks.size() + 1) + " tasks in the list," + " Now you have.");
                    printLine();
                    tasks.add(t);
                }
                break;

                case "event": {
                    String[] e1 = arg.split("\\s*/from\\s*", 2);
                    String[] e2 = e1[1].split("\\s*/to\\s*", 2);
                    Task t = new EventTask(e1[0].trim(), e2[0].trim(), e2[1].trim());
                    printLine();
                    System.out.println("Added this task, I have:");
                    System.out.println("    " + t);
                    System.out.println((tasks.size() + 1) + " tasks in the list," + " Now you have.");
                    printLine();
                    tasks.add(t);
                }
                break;

                default:
                    Task t = new ToDoTask(line);
                    printLine();
                    System.out.println("added: " + line);
                    printLine();
                    tasks.add(t);
                    break;
            }
        }

        scanner.close();
    }

    private static void printLine() {
        System.out.println("_".repeat(61));
    }
}



