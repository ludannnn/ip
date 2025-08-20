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

            try {
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
                        if (arg.isEmpty()) throw new IllegalArgumentException("Mark what item, do you want?");
                        int idx = Integer.parseInt(arg) - 1;
                        if (idx < 0 || idx >= tasks.size()) throw new IndexOutOfBoundsException();
                        tasks.get(idx).markDone();
                        printLine();
                        System.out.println("Marked this task as done, I have:\n" + tasks.get(idx));
                        printLine();
                    }
                    break;

                    case "unmark": {
                        if (arg.isEmpty()) throw new IllegalArgumentException("Unark what item, do you want?");
                        int idx = Integer.parseInt(arg) - 1;
                        if (idx < 0 || idx >= tasks.size()) throw new IndexOutOfBoundsException();
                        tasks.get(idx).markNotDone();
                        printLine();
                        System.out.println("not done yet, is this task:\n" + tasks.get(idx));
                        printLine();
                    }
                    break;

                    case "todo": {
                        if (arg.isEmpty()) throw new IllegalArgumentException("Todo, what do you need?");
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
                        // expect: <desc> /by <when>
                        String[] d = arg.split("\\s*/by\\s*", 2);
                        if (d.length < 2 || d[0].isBlank() || d[1].isBlank())
                            throw new IllegalArgumentException("Description and deadline, please tell");
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
                        // expect: <desc> /from <start> /to <end>
                        String[] e1 = arg.split("\\s*/from\\s*", 2);
                        if (e1.length < 2 || e1[0].isBlank())
                            throw new IllegalArgumentException("Description, start and end, please tell");
                        String[] e2 = e1[1].split("\\s*/to\\s*", 2);
                        if (e2.length < 2 || e2[0].isBlank() || e2[1].isBlank())
                            throw new IllegalArgumentException("Description, start and end, please tell");
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
                        // for any other unknown inputs like blah blah
                        printLine();
                        System.out.println("What you are trying to say, I do not understand.");
                }
            } catch (NumberFormatException e) {
                printLine();
                System.out.println("A number, that is not. (e.g., mark 2)");
                printLine();
            } catch (IndexOutOfBoundsException e) {
                printLine();
                System.out.println("Within the list, that task number is not.");
                printLine();
            } catch (IllegalArgumentException e) {
                printLine();
                System.out.println(e.getMessage());
                printLine();
            } catch (Exception e) {
                printLine();
                System.out.println("Unexpected error, it is: " + e.getMessage());
                printLine();
            }
        }

        scanner.close();
    }

    private static void printLine() {
        System.out.println("_".repeat(61));
    }
}



