package yoda.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeadlineTaskTest {

    @Test
    void formatsDateOnly_prettyAndIso() {
        var d = new DeadlineTask("project", "2019-12-02");
        assertTrue(d.toString().contains("(by: Dec 2 2019)"));
        assertEquals("D | 0 | project | 2019-12-02", d.toSaveLine());
    }
}