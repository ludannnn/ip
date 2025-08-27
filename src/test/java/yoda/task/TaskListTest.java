package yoda.task;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {

    @Test
    void constructor_nullInitial_createsEmptyList() {
        TaskList tl = new TaskList(null);
        assertEquals(0, tl.size());
    }

    @Test
    void constructor_withInitial_preservesOrderAndSize() {
        List<Task> init = new ArrayList<>();
        init.add(new ToDoTask("a"));
        init.add(new ToDoTask("b"));

        TaskList tl = new TaskList(init);

        assertEquals(2, tl.size());
        assertEquals("[T][ ] a", tl.get(0).toString());
        assertEquals("[T][ ] b", tl.get(1).toString());
    }

    @Test
    void add_increasesSize_andGetReturnsSameTask() {
        TaskList tl = new TaskList(null);
        Task t = new ToDoTask("read");
        tl.add(t);

        assertEquals(1, tl.size());
        assertSame(t, tl.get(0));
    }

    @Test
    void remove_returnsRemovedTask_andDecreasesSize() {
        Task a = new ToDoTask("a");
        Task b = new ToDoTask("b");
        TaskList tl = new TaskList(List.of(a, b));

        Task removed = tl.remove(0);

        assertSame(a, removed);
        assertEquals(1, tl.size());
        assertSame(b, tl.get(0));
    }

    @Test
    void mark_setsDoneFlag() {
        Task t = new ToDoTask("task");
        TaskList tl = new TaskList(List.of(t));

        tl.mark(0);

        assertTrue(t.toString().startsWith("[T][X]"),
                "Expected marked task to start with [T][X], but was: " + t);
    }

    @Test
    void unmark_clearsDoneFlag() {
        Task t = new ToDoTask("task");
        t.markDone(); // pre-mark
        TaskList tl = new TaskList(List.of(t));

        tl.unmark(0);

        assertTrue(t.toString().startsWith("[T][ ]"),
                "Expected unmarked task to start with [T][ ], but was: " + t);
    }

    @Test
    void get_invalidIndex_throws() {
        TaskList tl = new TaskList(null);
        assertThrows(IndexOutOfBoundsException.class, () -> tl.get(0));
    }

    @Test
    void remove_invalidIndex_throws() {
        TaskList tl = new TaskList(null);
        assertThrows(IndexOutOfBoundsException.class, () -> tl.remove(1));
    }

    @Test
    void mark_invalidIndex_throws() {
        TaskList tl = new TaskList(null);
        assertThrows(IndexOutOfBoundsException.class, () -> tl.mark(5));
    }

    @Test
    void unmark_invalidIndex_throws() {
        TaskList tl = new TaskList(null);
        assertThrows(IndexOutOfBoundsException.class, () -> tl.unmark(-1));
    }

    @Test
    void asList_isLiveView_modificationsReflect() {
        TaskList tl = new TaskList(null);
        List<Task> live = tl.asList();

        live.add(new ToDoTask("x"));

        assertEquals(1, tl.size());
        assertEquals("[T][ ] x", tl.get(0).toString());
    }
}
