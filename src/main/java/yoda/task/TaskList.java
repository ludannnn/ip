package yoda.task;

import java.util.ArrayList;
import java.util.List;

public class TaskList {
    private final ArrayList<Task> tasks;

    public TaskList(List<Task> initial) {
        this.tasks = new ArrayList<>(initial == null ? List.of() : initial);
    }

    public int size() { return tasks.size(); }
    public Task get(int idx) { return tasks.get(idx); }

    public void add(Task t) { tasks.add(t); }
    public Task remove(int idx) { return tasks.remove(idx); }

    public void mark(int idx) { tasks.get(idx).markDone(); }
    public void unmark(int idx) { tasks.get(idx).markNotDone(); }

    public ArrayList<Task> asList() { return tasks; }
}
