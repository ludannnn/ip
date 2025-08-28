package yoda.task;

/**
 * A simple task without any date/time fields.
 */
public class ToDoTask extends Task {

    /**
     * Creates a todo task with the given description.
     *
     * @param desc description of the task
     */
    public ToDoTask(String desc) {
        super(desc);
    }

    /**
     * Returns the display form of this task, e.g. {@code [T][ ] read book} or {@code [T][X] read book}.
     *
     * @return formatted string for UI display
     */
    @Override
    public String toString() {
        return "[T]" + "[" + this.getStatusIcon() + "] " + this.description;
    }

    /**
     * Serializes this task to a single save line: {@code T | <0|1> | <desc>}.
     *
     * @return pipe-separated save line
     */
    @Override
    public String toSaveLine() {
        return "T | " + (isDone ? 1 : 0) + " | " + description;
    }
}


