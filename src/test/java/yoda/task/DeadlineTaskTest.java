package yoda.task;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DeadlineTaskTest {

    @Test
    void parsesIsoDate_only() {
        DeadlineTask t = new DeadlineTask("return book", "2019-12-02");
        // pretty print
        assertTrue(t.toString().contains("Dec 2 2019"));
        // saved in ISO date
        assertEquals("D | 0 | return book | 2019-12-02", t.toSaveLine());
    }

    @Test
    void parsesSlashDateTime_HHmm() {
        DeadlineTask t = new DeadlineTask("return book", "2/12/2019 1800");
        assertTrue(t.toString().contains("Dec 2 2019, 18:00"));
        assertEquals("D | 0 | return book | 2019-12-02T18:00", t.toSaveLine());
    }

    @Test
    void parsesMonthTextDateTime() {
        DeadlineTask t = new DeadlineTask("demo", "Dec 2 2019, 18:00");
        assertTrue(t.toString().contains("Dec 2 2019, 18:00"));
        assertEquals("D | 0 | demo | 2019-12-02T18:00", t.toSaveLine());
    }

    @Test
    void noDate_given_usesDashPretty_andEmptyIso() {
        DeadlineTask t = new DeadlineTask("return book");
        assertTrue(t.toString().contains("(by: —)"));
        // trailing space after the last pipe is expected by your formatter
        assertEquals("D | 0 | return book | ", t.toSaveLine());
    }

    @Test
    void invalidDate_throws() {
        IllegalArgumentException ex =
                assertThrows(IllegalArgumentException.class,
                        () -> new DeadlineTask("x", "not-a-date"));
        assertEquals("Description and deadline, please tell", ex.getMessage());
    }

    @Test
    void markDone_affectsIcon_andSaveLineFlag() {
        DeadlineTask t = new DeadlineTask("return book", "2019-12-02T18:00");
        t.markDone();
        assertTrue(t.toString().startsWith("[D][X]"));
        assertEquals("D | 1 | return book | 2019-12-02T18:00", t.toSaveLine());
    }
}