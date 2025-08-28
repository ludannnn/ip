package yoda.task;

/**
 * Base class for all tasks (todo, deadline, event).
 */
public abstract class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a task with the given description.
     *
     * @param description task description
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Reconstructs a task from a saved line.
     * Supported formats:
     * T | <0|1> | <desc>
     * D | <0|1> | <desc> | <date|datetime>
     * E | <0|1> | <desc> | <start> | <end>
     *
     * @param line the saved line to parse
     * @return the reconstructed task
     * @throws IllegalArgumentException       if the type code is unknown
     * @throws NumberFormatException          if the done flag is not a number
     * @throws ArrayIndexOutOfBoundsException if required fields are missing
     */
    public static Task fromSaveLine(String line) {
        // split on " | " with optional spaces
        String[] p = line.split("\\s*\\|\\s*");
        String type = p[0];
        int done = Integer.parseInt(p[1]);
        Task t;
        switch (type) {
        case "T":
            t = new ToDoTask(p[2]);
            break;
        case "D":
            t = new DeadlineTask(p[2], p[3]);
            break;
        case "E":
            t = new EventTask(p[2], p[3], p[4]);
            break;
        default:
            throw new IllegalArgumentException("Unknown type: " + type);
        }
        if (done == 1) t.markDone();
        return t;
    }

    /**
     * Returns the status icon for this task.
     *
     * @return "X" if done, otherwise a single space
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    /**
     * Marks this task as done.
     */
    public void markDone() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void markNotDone() {
        this.isDone = false;
    }

    /**
     * Serializes this task to a single save line.
     * Format examples:
     * T | 0 | read book
     * D | 1 | return book | 2019-12-02T18:00
     * E | 0 | meeting | 2019-12-02 | 2019-12-03
     *
     * @return the pipe-separated save line
     */
    public abstract String toSaveLine();
}
