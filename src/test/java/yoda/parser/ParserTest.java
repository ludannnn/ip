package yoda.parser;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest {

    @Test
    void parse_blank_returnsUnknown() {
        Command c = Parser.parse("   ");
        assertEquals(Command.Type.UNKNOWN, c.type);
    }

    @Test
    void parse_bye_isCaseInsensitive() {
        Command c = Parser.parse("BYE");
        assertEquals(Command.Type.BYE, c.type);
    }

    @Test
    void parse_list_ok() {
        Command c = Parser.parse("list");
        assertEquals(Command.Type.LIST, c.type);
    }

    @Test
    void parse_todo_ok() {
        Command c = Parser.parse("todo read book");
        assertEquals(Command.Type.TODO, c.type);
        assertEquals("read book", c.desc);
    }

    @Test
    void parse_deadline_ok() {
        Command c = Parser.parse("deadline return book /by 2/12/2019 1800");
        assertEquals(Command.Type.DEADLINE, c.type);
        assertEquals("return book", c.desc);
        assertEquals("2/12/2019 1800", c.by);
    }

    @Test
    void parse_event_ok() {
        Command c = Parser.parse("event project meeting /from 2/12/2019 1800 /to 2/12/2019 2000");
        assertEquals(Command.Type.EVENT, c.type);
        assertEquals("project meeting", c.desc);
        assertEquals("2/12/2019 1800", c.from);
        assertEquals("2/12/2019 2000", c.to);
    }

    @Test
    void parse_mark_ok_1BasedIndex() {
        Command c = Parser.parse("mark 3");
        assertEquals(Command.Type.MARK, c.type);
        assertEquals(2, c.index);   // 3 -> 0-based 2
    }

    @Test
    void parse_unmark_ok() {
        Command c = Parser.parse("unmark 1");
        assertEquals(Command.Type.UNMARK, c.type);
        assertEquals(0, c.index);
    }

    @Test
    void parse_delete_ok() {
        Command c = Parser.parse("delete 2");
        assertEquals(Command.Type.DELETE, c.type);
        assertEquals(1, c.index);
    }

    @Test
    void parse_unknown_default() {
        Command c = Parser.parse("blablabla");
        assertEquals(Command.Type.UNKNOWN, c.type);
    }

    @Test
    void parse_trimsExtraSpaces() {
        Command c = Parser.parse("   todo     buy bread   ");
        assertEquals(Command.Type.TODO, c.type);
        assertEquals("buy bread", c.desc);
    }
}