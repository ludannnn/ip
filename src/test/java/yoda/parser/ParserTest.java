package yoda.parser;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ParserTest {

    @Test
    void parseTodo_ok() {
        var c = Parser.parse("todo read book");
        assertEquals(Command.Type.TODO, c.type);
        assertEquals("read book", c.desc);
    }

    @Test
    void parseDeadline_ok() {
        var c = Parser.parse("deadline return book /by 2/12/2019 1800");
        assertEquals(Command.Type.DEADLINE, c.type);
        assertEquals("return book", c.desc);
        assertEquals("2/12/2019 1800", c.by);
    }

    @Test
    void parseEvent_ok() {
        var c = Parser.parse("event meet /from 2019-12-02 14:00 /to 2019-12-02 16:00");
        assertEquals(Command.Type.EVENT, c.type);
        assertEquals("meet", c.desc);
        assertEquals("2019-12-02 14:00", c.from);
        assertEquals("2019-12-02 16:00", c.to);
    }

    @Test
    void parseMark_requiresIndex() {
        var ex = assertThrows(IllegalArgumentException.class, () -> Parser.parse("mark"));
        assertTrue(ex.getMessage().toLowerCase().contains("mark"));
    }
}