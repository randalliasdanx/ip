package randy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Task that spans a time range.
 */
public class Event extends Task {
    private Object from;
    private Object to;

    /**
     * Creates an event task with a time range.
     *
     * @param desc Task description
     * @param from Start date or time
     * @param to End date or time
     */
    public Event(String desc, String from, String to) {
        super(desc);
        // parse dates if possible
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
        String fromStr = (from instanceof LocalDate) 
            ? ((LocalDate) from).format(fmt) 
            : from.toString();
        String toStr = (to instanceof LocalDate) 
            ? ((LocalDate) to).format(fmt) 
            : to.toString();
        return "[E]" + super.toString() + " (from: " + fromStr + " to: " + toStr + ")";
    }

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
