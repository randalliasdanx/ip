package randy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Represents a task that spans a time range with a start and end.
 * Uses strict date parsing to reject impossible dates like Feb 30.
 */
public class Event extends Task {
    private Object from;
    private Object to;

    /** Strict formatter that rejects invalid dates like 2025-02-30. */
    private static final DateTimeFormatter STRICT_DATE_FORMAT =
            new DateTimeFormatterBuilder()
                    .appendPattern("uuuu-MM-dd")
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);

    /**
     * Creates a new Event task.
     *
     * @param desc Description of the event.
     * @param from Start date/time string.
     * @param to   End date/time string.
     */
    public Event(String desc, String from, String to) {
        super(desc);
        // parse dates with strict validation if possible
        try {
            this.from = LocalDate.parse(from, STRICT_DATE_FORMAT);
        } catch (DateTimeParseException e) {
            this.from = from;
        }
        try {
            this.to = LocalDate.parse(to, STRICT_DATE_FORMAT);
        } catch (DateTimeParseException e) {
            this.to = to;
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd yyyy");
        String fromStr = (from instanceof LocalDate)
            ? ((LocalDate) from).format(fmt)
            : from.toString();
        String toStr = (to instanceof LocalDate)
            ? ((LocalDate) to).format(fmt)
            : to.toString();
        return "[E]" + super.toString() + " (from: " + fromStr + " to: " + toStr + ")";
    }

    /**
     * {@inheritDoc}
     * If both from and to are valid dates, checks if d falls within the range (inclusive).
     * If only from is a date, checks for exact match.
     */
    @Override
    public boolean isOnDate(LocalDate d) {
        // check if date falls within event range
        if (from instanceof LocalDate && to instanceof LocalDate) {
            LocalDate start = (LocalDate) from;
            LocalDate end = (LocalDate) to;
            return !d.isBefore(start) && !d.isAfter(end);
        } else if (from instanceof LocalDate) {
            return ((LocalDate) from).equals(d);
        }
        return false;
    }
}
