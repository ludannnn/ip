import java.time.LocalDate;

public abstract class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " "); // mark done task with X
    }

    public void markDone() {
        this.isDone = true;
    }

    public void markNotDone() {
        this.isDone = false;
    }

    public abstract String toSaveLine();

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
}
