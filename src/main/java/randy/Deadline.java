package randy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a due date deadline.
 * 
 * A deadline task has a description and a due date.
 * The date can be specified as a LocalDate string (yyyy-MM-dd) or free text.
 */
public class Deadline extends Task {
    private Object dueDate; // can be LocalDate or String

    /**
     * Creates a deadline task.
     * 
     * @param desc the task description
     * @param by the due date (yyyy-MM-dd format or free text)
     */
    public Deadline(String desc, String by) {
        super(desc);
        assert by != null && !by.trim().isEmpty() : "deadline date cannot be null or empty";
        // try parsing as date, otherwise keep as string
        try {
            this.dueDate = LocalDate.parse(by);
        } catch (DateTimeParseException e) {
            this.dueDate = by;
        }
    }

    /**
     * Returns the string representation of the deadline task.
     * Format: [D][X] <description> (by: MMM dd yyyy) or [D][ ] <description> (by: <date>)
     * 
     * @return the deadline task as a formatted string
     */
    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd yyyy");
        String dateStr = (dueDate instanceof LocalDate) 
            ? ((LocalDate) dueDate).format(fmt) 
            : dueDate.toString();
        return "[D]" + super.toString() + " (by: " + dateStr + ")";
    }

    /**
     * Checks if this deadline occurs on the given date.
     * Returns true only if the due date matches exactly.
     * 
     * @param d the date to check
     * @return true if the due date equals the given date, false otherwise
     */
    @Override
    public boolean isOnDate(LocalDate d) {
        if (dueDate instanceof LocalDate) {
            return ((LocalDate) dueDate).equals(d);
        }
        return false;
    }
}
