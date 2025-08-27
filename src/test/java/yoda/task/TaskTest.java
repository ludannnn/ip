package yoda.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TaskFromSaveLineTest {

    @Test
    void parse_todo_notDone() {
        Task t = Task.fromSaveLine("T | 0 | read book");
        assertTrue(t instanceof ToDoTask);
        assertEquals("[T][ ] read book", t.toString());
    }

    @Test
    void parse_todo_done() {
        Task t = Task.fromSaveLine("T | 1 | read book");
        assertTrue(t instanceof ToDoTask);
        assertEquals("[T][X] read book", t.toString());
    }

    @Test
    void parse_deadline_isoDatetime() {
        Task t = Task.fromSaveLine("D | 0 | return book | 2019-12-02T18:00");
        assertTrue(t instanceof DeadlineTask);
        // pretty string comes from DeadlineTask formatter
        assertTrue(t.toString().contains("Dec 2 2019, 18:00"), t.toString());
        assertTrue(t.toString().startsWith("[D][ ]"));
    }

    @Test
    void parse_event_isoDates_andDone() {
        Task t = Task.fromSaveLine("E | 1 | project meeting | 2019-12-02 | 2019-12-03");
        assertTrue(t instanceof EventTask);
        String s = t.toString();
        assertTrue(s.contains("Dec 2 2019"), s);
        assertTrue(s.contains("Dec 3 2019"), s);
        assertTrue(s.startsWith("[E][X]"), s);
    }

    @Test
    void parse_trimsSpacesAroundPipes() {
        Task t = Task.fromSaveLine("  T   |   0   |   buy milk   ");
        assertEquals("[T][ ] buy milk", t.toString());
    }

    @Test
    void roundTrip_todo() {
        Task original = new ToDoTask("alpha");
        original.markDone();
        String line = original.toSaveLine();         // e.g., "T | 1 | alpha"
        Task parsed = Task.fromSaveLine(line);
        assertEquals(original.toString(), parsed.toString());
    }

    @Test
    void roundTrip_deadline_iso() {
        Task original = new DeadlineTask("return", "2019-12-02T09:30");
        String line = original.toSaveLine();         // "D | 0 | return | 2019-12-02T09:30"
        Task parsed = Task.fromSaveLine(line);
        assertEquals(original.toString(), parsed.toString());
    }

    @Test
    void roundTrip_event_iso() {
        Task original = new EventTask("standup", "2019-12-02T10:00", "2019-12-02T10:15");
        original.markDone();
        String line = original.toSaveLine();
        Task parsed = Task.fromSaveLine(line);
        assertEquals(original.toString(), parsed.toString());
    }

    @Test
    void parse_unknownType_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class,
                () -> Task.fromSaveLine("X | 0 | blah"));
    }

    @Test
    void parse_badDoneFlagOrMissingFields_throwRuntime() {
        // bad number for done flag
        assertThrows(RuntimeException.class,
                () -> Task.fromSaveLine("T | not-a-number | x"));
        // missing required fields for deadline
        assertThrows(RuntimeException.class,
                () -> Task.fromSaveLine("D | 0 | onlydesc"));
    }
}
