package yoda.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EventTaskTest {

    @Test
    void parsesDateOnly_forStartAndEnd() {
        EventTask t = new EventTask("hackathon", "2019-12-02", "2019-12-03");

        // pretty output
        String s = t.toString();
        assertTrue(s.contains("Dec 2 2019"), s);
        assertTrue(s.contains("Dec 3 2019"), s);

        // canonical save (ISO date)
        assertEquals("E | 0 | hackathon | 2019-12-02 | 2019-12-03", t.toSaveLine());
    }

    @Test
    void parsesSlashDateTime_HHmm() {
        EventTask t = new EventTask("project meeting", "2/12/2019 1800", "2/12/2019 2000");

        String s = t.toString();
        assertTrue(s.contains("Dec 2 2019, 18:00"), s);
        assertTrue(s.contains("Dec 2 2019, 20:00"), s);

        // canonical save (ISO date-time)
        assertEquals("E | 0 | project meeting | 2019-12-02T18:00 | 2019-12-02T20:00", t.toSaveLine());
    }

    @Test
    void parsesIsoDateTime_withT() {
        EventTask t = new EventTask("flight", "2019-12-02T06:30", "2019-12-02T08:15");

        String s = t.toString();
        assertTrue(s.contains("Dec 2 2019, 06:30"), s);
        assertTrue(s.contains("Dec 2 2019, 08:15"), s);

        assertEquals("E | 0 | flight | 2019-12-02T06:30 | 2019-12-02T08:15", t.toSaveLine());
    }

    @Test
    void invalidStart_throws() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class,
                        () -> new EventTask("x", "not-a-date", "2019-12-02"));
        assertEquals("Description, start and end, please tell", ex.getMessage());
    }

    @Test
    void blankEnd_throws() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class,
                        () -> new EventTask("x", "2019-12-02", " "));
        assertEquals("Description, start and end, please tell", ex.getMessage());
    }

    @Test
    void markDone_affectsIcon_andSaveFlag() {
        EventTask t = new EventTask("session", "2019-12-02T10:00", "2019-12-02T11:00");
        t.markDone();

        assertTrue(t.toString().startsWith("[E][X]"));
        assertEquals("E | 1 | session | 2019-12-02T10:00 | 2019-12-02T11:00", t.toSaveLine());
    }
}
