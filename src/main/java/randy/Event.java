package randy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that spans a time range with a start and end.
 * Dates are parsed into LocalDate if possible, otherwise kept as strings.
 */
public class Event extends Task {
    private Object from; // LocalDate or String
    private Object to;   // LocalDate or String

    /**
     * Creates a new Event task.
     *
     * @param desc Description of the event.
     * @param from Start date/time string.
     * @param to   End date/time string.
     */
    public Event(String desc, String from, String to) {
        super(desc);
        // try to parse each date, fall back to string if cant
        try {
            this.from = LocalDate.parse(from);
        } catch (DateTimeParseException e) {
            this.from = from;
        }
        try {
            this.to = LocalDate.parse(to);
        } catch (DateTimeParseException e) {
            this.to = to;
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd yyyy");
        String fromStr;
        String toStr;
        if (from instanceof LocalDate) {
            fromStr = ((LocalDate) from).format(fmt);
        } else {
            fromStr = from.toString();
        }
        if (to instanceof LocalDate) {
            toStr = ((LocalDate) to).format(fmt);
        } else {
            toStr = to.toString();
        }
        return "[E]" + super.toString() + " (from: " + fromStr + " to: " + toStr + ")";
    }

    /**
     * {@inheritDoc}
     * If both from and to are valid dates, checks if d falls within the range (inclusive).
     * If only from is a date, checks for exact match.
     */
    @Override
    public boolean isOnDate(LocalDate d) {
        // if both are dates, check if d falls within the range (inclusive)
        if (from instanceof LocalDate && to instanceof LocalDate) {
            LocalDate start = (LocalDate) from;
            LocalDate end = (LocalDate) to;
            return !d.isBefore(start) && !d.isAfter(end);
        }
        // if only from is a date, just check exact match
        if (from instanceof LocalDate) {
            return ((LocalDate) from).equals(d);
        }
        return false;
    }
}
