package yoda.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ToDoTaskTest {

    @Test
    void toString_notDone_formatsCorrectly() {
        Task t = new ToDoTask("read book");
        assertEquals("[T][ ] read book", t.toString());
    }

    @Test
    void toString_done_formatsWithX() {
        Task t = new ToDoTask("read book");
        t.markDone();
        assertEquals("[T][X] read book", t.toString());
    }

    @Test
    void toSaveLine_notDone_correct() {
        Task t = new ToDoTask("buy milk");
        assertEquals("T | 0 | buy milk", t.toSaveLine());
    }

    @Test
    void toSaveLine_done_correct() {
        Task t = new ToDoTask("buy milk");
        t.markDone();
        assertEquals("T | 1 | buy milk", t.toSaveLine());
    }

    @Test
    void markNotDone_revertsFlag() {
        Task t = new ToDoTask("task");
        t.markDone();
        t.markNotDone();
        assertEquals("[T][ ] task", t.toString());
        assertEquals("T | 0 | task", t.toSaveLine());
    }

    @Test
    void roundTrip_viaFromSaveLine() {
        Task original = new ToDoTask("alpha");
        original.markDone();
        String line = original.toSaveLine();                 // "T | 1 | alpha"
        Task parsed = Task.fromSaveLine(line);
        assertEquals(original.toString(), parsed.toString()); // same printed form
    }
}