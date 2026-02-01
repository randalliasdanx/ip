package randy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Task with a due date.
 */
public class Deadline extends Task {
    private Object dueDate; // can be LocalDate or String

    public Deadline(String desc, String by) {
        super(desc);
        // try parsing as date, otherwise keep as string
        try {
            this.dueDate = LocalDate.parse(by);
        } catch (DateTimeParseException e) {
            this.dueDate = by;
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MMM dd yyyy");
        String dateStr = (dueDate instanceof LocalDate) 
            ? ((LocalDate) dueDate).format(fmt) 
            : dueDate.toString();
        return "[D]" + super.toString() + " (by: " + dateStr + ")";
    }

    @Override
    public boolean isOnDate(LocalDate d) {
        if (dueDate instanceof LocalDate) {
            return ((LocalDate) dueDate).equals(d);
        }
        return false;
    }
}
