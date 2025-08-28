package yoda.ui;

import java.util.Scanner;
import yoda.task.Task;
import yoda.task.TaskList;



/**
 * Handles all user interaction: printing messages and reading commands.
 */
public class Ui {
    private final Scanner scanner;

    /**
     * Creates a UI that reads from the given scanner.
     * @param scanner input source (e.g., new Scanner(System.in))
     */
    public Ui(Scanner scanner) { this.scanner = scanner; }

    /**
     * Prints out welcome message
     */
    public void showWelcome() {
        System.out.println("""
                _______________________
                Yoda, I am.
                _______________________
                Do for you, what can I?
                """);
    }

    /**
     * Reads one line from input and trims it.
     * @return the trimmed line (may be empty)
     */
    public String readCommand() { return scanner.nextLine().trim(); }

    /**
     * Prints out a straight line made from '_'
     * as a demarkation
     */
    public void showLine() { System.out.println("_".repeat(61)); }

    /**
     * Prints out the current TaskList with 1 based numbering
     *
     * @param tasks
     */
    public void showList(TaskList tasks) {
        showLine();
        System.out.println("The tasks in your list, here are:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
        showLine();
    }

    /** Prints the confirmation for an added task.
     * @param t the task added
     * @param newCount total tasks after adding
     */
    public void showAdded(Task t, int newCount) {
        showLine();
        System.out.println("Added this task, I have:");
        System.out.println("    " + t);
        System.out.println(newCount + " tasks in the list," + " Now you have.");
        showLine();
    }

    /** Prints the confirmation for marking a task done. */
    public void showMarked(Task t) {
        showLine();
        System.out.println("Marked this task as done, I have:\n" + t);
        showLine();
    }

    /** Prints the confirmation for unmarking a task. */
    public void showUnmarked(Task t) {
        showLine();
        System.out.println("not done yet, is this task:\n" + t);
        showLine();
    }

    /** Prints the confirmation for deleting a task. */
    public void showDeleted(Task t) {
        showLine();
        System.out.println("Deleted this task, I have:\n" + t);
        showLine();
    }

    /** Prints the fallback for unknown commands. */
    public void showUnknown() {
        showLine();
        System.out.println("What you are trying to say, I do not understand.");
        showLine();
    }

    /**
     * Prints an error block with the given message.
     * @param msg human-readable error message
     */
    public void showError(String msg) {
        showLine();
        System.out.println(msg);
        showLine();
    }

    /** Prints the farewell line. */
    public void showBye() {
        System.out.println("Farewell, I bid you.");
    }

    /** Closes the scanner. */
    public void close() { scanner.close(); }
}
