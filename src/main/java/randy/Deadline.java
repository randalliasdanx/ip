package randy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Task with a due date.
 * Uses strict date parsing to reject impossible dates like Feb 30.
 */
public class Deadline extends Task {
    private Object dueDate; // can be LocalDate or String

    /** Strict formatter that rejects invalid dates like 2025-02-30. */
    private static final DateTimeFormatter STRICT_DATE_FORMAT =
            new DateTimeFormatterBuilder()
                    .appendPattern("uuuu-MM-dd")
                    .toFormatter()
                    .withResolverStyle(ResolverStyle.STRICT);

    public Deadline(String desc, String by) {
        super(desc);
        // try parsing as date with strict validation, otherwise keep as string
        try {
            this.dueDate = LocalDate.parse(by, STRICT_DATE_FORMAT);
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
