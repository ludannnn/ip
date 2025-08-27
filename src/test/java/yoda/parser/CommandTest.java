package yoda.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CommandTest {
    @Test
    void bye_factorySetsType_andAllFieldsNull() {
        Command c = Command.bye();
        assertEquals(Command.Type.BYE, c.type);
        assertAll(
                () -> assertNull(c.desc),
                () -> assertNull(c.by),
                () -> assertNull(c.from),
                () -> assertNull(c.to),
                () -> assertNull(c.index)
        );
    }

    @Test
    void list_factorySetsType_andAllFieldsNull() {
        Command c = Command.list();
        assertEquals(Command.Type.LIST, c.type);
        assertAll(
                () -> assertNull(c.desc),
                () -> assertNull(c.by),
                () -> assertNull(c.from),
                () -> assertNull(c.to),
                () -> assertNull(c.index)
        );
    }

    @Test
    void unknown_factorySetsType_andAllFieldsNull() {
        Command c = Command.unknown();
        assertEquals(Command.Type.UNKNOWN, c.type);
        assertAll(
                () -> assertNull(c.desc),
                () -> assertNull(c.by),
                () -> assertNull(c.from),
                () -> assertNull(c.to),
                () -> assertNull(c.index)
        );
    }

    @Test
    void todo_factorySetsDesc_only() {
        Command c = Command.todo("read book");
        assertEquals(Command.Type.TODO, c.type);
        assertEquals("read book", c.desc);
        assertAll(
                () -> assertNull(c.by),
                () -> assertNull(c.from),
                () -> assertNull(c.to),
                () -> assertNull(c.index)
        );
    }

    @Test
    void deadline_factorySetsDescAndBy_only() {
        Command c = Command.deadline("return book", "2/12/2019 1800");
        assertEquals(Command.Type.DEADLINE, c.type);
        assertEquals("return book", c.desc);
        assertEquals("2/12/2019 1800", c.by);
        assertAll(
                () -> assertNull(c.from),
                () -> assertNull(c.to),
                () -> assertNull(c.index)
        );
    }

    @Test
    void event_factorySetsDescFromTo_only() {
        Command c = Command.event("project meeting", "2/12/2019 1800", "2/12/2019 2000");
        assertEquals(Command.Type.EVENT, c.type);
        assertEquals("project meeting", c.desc);
        assertEquals("2/12/2019 1800", c.from);
        assertEquals("2/12/2019 2000", c.to);
        assertAll(
                () -> assertNull(c.by),
                () -> assertNull(c.index)
        );
    }

    @Test
    void mark_factorySetsIndex_only() {
        Command c = Command.mark(3);
        assertEquals(Command.Type.MARK, c.type);
        assertEquals(3, c.index);
        assertAll(
                () -> assertNull(c.desc),
                () -> assertNull(c.by),
                () -> assertNull(c.from),
                () -> assertNull(c.to)
        );
    }

    @Test
    void unmark_factorySetsIndex_only() {
        Command c = Command.unmark(0);
        assertEquals(Command.Type.UNMARK, c.type);
        assertEquals(0, c.index);
        assertAll(
                () -> assertNull(c.desc),
                () -> assertNull(c.by),
                () -> assertNull(c.from),
                () -> assertNull(c.to)
        );
    }

    @Test
    void delete_factorySetsIndex_only() {
        // include this test only if you have Command.delete(int)
        Command c = Command.delete(1);
        assertEquals(Command.Type.DELETE, c.type);
        assertEquals(1, c.index);
        assertAll(
                () -> assertNull(c.desc),
                () -> assertNull(c.by),
                () -> assertNull(c.from),
                () -> assertNull(c.to)
        );
    }

    @Test
    void factoriesReturnDistinctInstances() {
        Command a = Command.todo("x");
        Command b = Command.todo("x");
        assertNotSame(a, b); // ensure we’re not caching/mutating one shared object
    }
}
