package yoda.task;

import java.util.ArrayList;
import java.util.List;


/** Mutable list wrapper for tasks with convenience operations. */
public class TaskList {
    private final ArrayList<Task> tasks;

    /**
     * Creates a new list, copying the given initial tasks.
     * @param initial initial tasks; may be null (treated as empty)
     */
    public TaskList(List<Task> initial) {
        this.tasks = new ArrayList<>(initial == null ? List.of() : initial);
    }

    /** @return number of tasks in the list */
    public int size() { return tasks.size(); }

    /**
     * Returns the task at the given index.
     * @param idx 0-based index
     * @return the task at {@code idx}
     * @throws IndexOutOfBoundsException if {@code idx} is invalid
     */
    public Task get(int idx) { return tasks.get(idx); }

    /**
     * Adds a task to the end of the list.
     * @param t task to add
     */
    public void add(Task t) { tasks.add(t); }

    /**
     * Removes and returns the task at the given index.
     * @param idx 0-based index
     * @return the removed task
     * @throws IndexOutOfBoundsException if {@code idx} is invalid
     */
    public Task remove(int idx) { return tasks.remove(idx); }


    /**
     * Marks the task at the given index as done.
     * @param idx 0-based index
     * @throws IndexOutOfBoundsException if {@code idx} is invalid
     */
    public void mark(int idx) { tasks.get(idx).markDone(); }

    /**
     * Marks the task at the given index as not done.
     * @param idx 0-based index
     * @throws IndexOutOfBoundsException if {@code idx} is invalid
     */
    public void unmark(int idx) { tasks.get(idx).markNotDone(); }

    /**
     * Returns the underlying mutable list (live view).
     * @return the internal {@link ArrayList} backing this TaskList
     */
    public ArrayList<Task> asList() { return tasks; }
}
