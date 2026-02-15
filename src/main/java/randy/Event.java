package randy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task that spans a time period or range.
 * 
 * An event task has a description with a start and end time/date.
 * Both dates can be specified as LocalDate strings (yyyy-MM-dd) or free text.
 * When both dates are LocalDate objects, the isOnDate method checks if a date
 * falls within the event range (inclusive).
 */
public class Event extends Task {
    private Object from;
    private Object to;

    /**
     * Creates an event task with a date range.
     * 
     * @param desc the task description
     * @param from the start date (yyyy-MM-dd format or free text)
     * @param to the end date (yyyy-MM-dd format or free text)
     */
    public Event(String desc, String from, String to) {
        super(desc);
        assert from != null && !from.trim().isEmpty() : "event start date cannot be null or empty";
        assert to != null && !to.trim().isEmpty() : "event end date cannot be null or empty";
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

    /**
     * Returns the string representation of the event task.
     * Format: [E][X] <description> (from: MMM dd yyyy to: MMM dd yyyy)
     * 
     * @return the event task as a formatted string
     */
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
     * Checks if this event occurs on the given date.
     * If both dates are LocalDate objects, returns true if the given date
     * falls within the event range (inclusive).
     * If only the start date is a LocalDate, checks for exact match.
     * 
     * @param d the date to check
     * @return true if the date falls within or matches the event, false otherwise
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
