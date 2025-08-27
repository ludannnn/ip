import java.util.Scanner;

public class Ui {
    private final Scanner scanner;

    public Ui(Scanner scanner) { this.scanner = scanner; }

    public void showWelcome() {
        System.out.println("""
                _______________________
                Yoda, I am.
                _______________________
                Do for you, what can I?
                """);
    }

    public String readCommand() { return scanner.nextLine().trim(); }

    public void showLine() { System.out.println("_".repeat(61)); }

    public void showList(TaskList tasks) {
        showLine();
        System.out.println("The tasks in your list, here are:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        showLine();
    }

    public void showAdded(Task t, int newCount) {
        showLine();
        System.out.println("Added this task, I have:");
        System.out.println("    " + t);
        System.out.println(newCount + " tasks in the list," + " Now you have.");
        showLine();
    }

    public void showMarked(Task t) {
        showLine();
        System.out.println("Marked this task as done, I have:\n" + t);
        showLine();
    }

    public void showUnmarked(Task t) {
        showLine();
        System.out.println("not done yet, is this task:\n" + t);
        showLine();
    }

    public void showDeleted(Task t) {
        showLine();
        System.out.println("Deleted this task, I have:\n" + t);
        showLine();
    }

    public void showUnknown() {
        showLine();
        System.out.println("What you are trying to say, I do not understand.");
        showLine();
    }

    public void showError(String msg) {
        showLine();
        System.out.println(msg);
        showLine();
    }

    public void showBye() {
        System.out.println("Farewell, I bid you.");
    }

    public void close() { scanner.close(); }
}
