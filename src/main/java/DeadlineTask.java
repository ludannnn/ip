import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class DeadlineTask extends Task {
    private final String deadlinePretty;
    private final String deadlineIso;

    private static final DateTimeFormatter OUT_DATE =
            DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter OUT_DATETIME =
            DateTimeFormatter.ofPattern("MMM d yyyy, HH:mm", Locale.ENGLISH);

    // accepted date-only inputs
    private static final DateTimeFormatter[] DATE_PATTERNS = new DateTimeFormatter[] {
            DateTimeFormatter.ISO_LOCAL_DATE,               // 2019-12-02
            DateTimeFormatter.ofPattern("d/M/uuuu"),        // 2/12/2019
            DateTimeFormatter.ofPattern("d-M-uuuu"),        // 2-12-2019
            DateTimeFormatter.ofPattern("MMM d uuuu", Locale.ENGLISH) // Dec 2 2019 (for old saved data, optional)
    };

    // accepted date+time inputs
    private static final DateTimeFormatter[] DATETIME_PATTERNS = new DateTimeFormatter[] {
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,          // 2019-12-02T18:00
            DateTimeFormatter.ofPattern("d/M/uuuu HHmm"),   // 2/12/2019 1800
            DateTimeFormatter.ofPattern("d/M/uuuu HH:mm"),  // 2/12/2019 18:00
            DateTimeFormatter.ofPattern("uuuu-MM-dd HHmm"), // 2019-12-02 1800
            DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm"),// 2019-12-02 18:00
            DateTimeFormatter.ofPattern("MMM d uuuu, HH:mm", Locale.ENGLISH) // Dec 2 2019, 18:00
    };

    public DeadlineTask(String desc, String deadlineInput) {
        super(desc);

        if (deadlineInput == null || deadlineInput.isBlank()) {
            this.deadlinePretty = "—";
            this.deadlineIso = "";
            return;
        }

        LocalDateTime ldt = tryParseDateTime(deadlineInput);
        if (ldt != null) {
            this.deadlinePretty = ldt.format(OUT_DATETIME);
            this.deadlineIso = ldt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME); // 2019-12-02T18:00
            return;
        }

        LocalDate ld = tryParseDate(deadlineInput);
        if (ld != null) {
            this.deadlinePretty = ld.format(OUT_DATE);
            this.deadlineIso = ld.format(DateTimeFormatter.ISO_LOCAL_DATE); // 2019-12-02
            return;
        }

        throw new IllegalArgumentException("Description and deadline, please tell");
    }

    public DeadlineTask(String desc) { this(desc, ""); }

    private static LocalDateTime tryParseDateTime(String s) {
        for (DateTimeFormatter f : DATETIME_PATTERNS) {
            try { return LocalDateTime.parse(s, f); } catch (DateTimeParseException ignored) {}
        }
        return null;
    }

    private static LocalDate tryParseDate(String s) {
        for (DateTimeFormatter f : DATE_PATTERNS) {
            try { return LocalDate.parse(s, f); } catch (DateTimeParseException ignored) {}
        }
        return null;
    }

    @Override
    public String toString(){
        return "[D][" + this.getStatusIcon() + "] " + this.description + " (by: " + this.deadlinePretty + ")";
    }

    @Override
    public String toSaveLine() {
        return "D | " + (isDone ? 1 : 0) + " | " + description + " | " + deadlineIso;
    }
}
