package yoda.task;

import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class TaskListTest {

    @Test
    void add_mark_unmark_remove() {
        var tl = new TaskList(List.of());
        tl.add(new ToDoTask("read book"));
        assertEquals(1, tl.size());
        assertTrue(tl.get(0).toString().contains("read book"));

        tl.mark(0);
        assertTrue(tl.get(0).toString().contains("[X]"));

        tl.unmark(0);
        assertTrue(tl.get(0).toString().contains("[ ]"));

        var removed = tl.remove(0);
        assertTrue(removed.toString().contains("read book"));
        assertEquals(0, tl.size());
    }

    @Test
    void find_matchesDescriptionOnly() {
        var tl = new TaskList(List.of(
                new ToDoTask("read book"),
                new ToDoTask("return pen")
        ));
        var res = tl.find("book");
        assertEquals(1, res.size());
        assertTrue(res.get(0).toString().contains("read book"));
    }
}
