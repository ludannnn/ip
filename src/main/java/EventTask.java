import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

public class EventTask extends Task {
    private final String startPretty;
    private final String endPretty;
    private final String startIso;
    private final String endIso;

    private static final DateTimeFormatter OUT_DATE =
            DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH);
    private static final DateTimeFormatter OUT_DATETIME =
            DateTimeFormatter.ofPattern("MMM d yyyy, HH:mm", Locale.ENGLISH);

    //for date-only inputs
    private static final DateTimeFormatter[] DATE_PATTERNS = new DateTimeFormatter[] {
            DateTimeFormatter.ISO_LOCAL_DATE,               // 2019-12-02
            DateTimeFormatter.ofPattern("d/M/uuuu"),        // 2/12/2019
            DateTimeFormatter.ofPattern("d-M-uuuu"),        // 2-12-2019
            DateTimeFormatter.ofPattern("MMM d uuuu", Locale.ENGLISH)
    };

    //for date+time inputs
    private static final DateTimeFormatter[] DATETIME_PATTERNS = new DateTimeFormatter[] {
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,          // 2019-12-02T18:00
            DateTimeFormatter.ofPattern("d/M/uuuu HHmm"),   // 2/12/2019 1800
            DateTimeFormatter.ofPattern("d/M/uuuu HH:mm"),  // 2/12/2019 18:00
            DateTimeFormatter.ofPattern("uuuu-MM-dd HHmm"), // 2019-12-02 1800
            DateTimeFormatter.ofPattern("uuuu-MM-dd HH:mm"),// 2019-12-02 18:00
            DateTimeFormatter.ofPattern("MMM d uuuu, HH:mm", Locale.ENGLISH) // Dec 2 2019, 18:00
    };

    public EventTask(String desc, String startInput, String endInput) {
        super(desc);

        String[] s = parseAny(startInput);
        this.startPretty = s[0];
        this.startIso = s[1];

        String[] e = parseAny(endInput);
        this.endPretty = e[0];
        this.endIso = e[1];
    }

    @Override
    public String toString(){
        return "[E]" + "[" + this.getStatusIcon() + "] " + this.description +
                " (from: " + this.startPretty + " to: " + this.endPretty + ")";
    }

    @Override
    public String toSaveLine() {
        return "E | " + (isDone ? 1 : 0) + " | " + description + " | " + startIso + " | " + endIso;
    }

    //helper functions

    // returns {pretty, iso}; throws if cannot parse
    private static String[] parseAny(String input) {
        if (input == null || input.isBlank()) {
            throw new IllegalArgumentException("Description, start and end, please tell");
        }

        LocalDateTime ldt = tryParseDateTime(input);
        if (ldt != null) {
            return new String[] {
                    ldt.format(OUT_DATETIME),
                    ldt.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)   // 2019-12-02T18:00
            };
        }

        LocalDate ld = tryParseDate(input);
        if (ld != null) {
            return new String[] {
                    ld.format(OUT_DATE),
                    ld.format(DateTimeFormatter.ISO_LOCAL_DATE)         // 2019-12-02
            };
        }

        throw new IllegalArgumentException("Description, start and end, please tell");
    }

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
}
