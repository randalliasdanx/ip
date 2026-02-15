package randy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Represents a task with a due date.
 * If the date string is a valid yyyy-MM-dd format, it gets stored as a
 * LocalDate and displayed in a nicer format. Otherwise it stays as a string.
 */
public class Deadline extends Task {
    private Object dueDate; // either LocalDate or just the raw String

    /**
     * Creates a new Deadline task.
     *
     * @param desc Description of the task.
     * @param by   Due date string (yyyy-MM-dd or free text).
     */
    public Deadline(String desc, String by) {
        super(desc);
        try {
            this.dueDate = LocalDate.parse(by);
        } catch (DateTimeParseException e) {
            // not a valid date, just store as string
            this.dueDate = by;
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd yyyy");
        String dateStr;
        if (dueDate instanceof LocalDate) {
            dateStr = ((LocalDate) dueDate).format(fmt);
        } else {
            dateStr = dueDate.toString();
        }
        return "[D]" + super.toString() + " (by: " + dateStr + ")";
    }

    /**
     * {@inheritDoc}
     * Returns true only if the due date is a valid LocalDate and matches exactly.
     */
    @Override
    public boolean isOnDate(LocalDate d) {
        if (dueDate instanceof LocalDate) {
            return ((LocalDate) dueDate).equals(d);
        }
        return false;
    }
}
