package yoda.task;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class EventTaskTest {

    @Test
    void formatsRange_prettyAndIso() {
        var e = new EventTask("meeting", "2019-12-02 1400", "2019-12-02 1600");
        var s = e.toString();
        assertTrue(s.contains("from: Dec 2 2019, 14:00"));
        assertTrue(s.contains("to: Dec 2 2019, 16:00"));
        assertEquals("E | 0 | meeting | 2019-12-02T14:00:00 | 2019-12-02T16:00:00", e.toSaveLine());
    }
}