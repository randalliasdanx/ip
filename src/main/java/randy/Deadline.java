package randy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Locale;

/**
 * Task with a due date.
 */
public class Deadline extends Task {
    private Object dueDate; // can be LocalDate or String

    // formats to try when parsing dates
    private static final DateTimeFormatter[] FORMATS = {
        DateTimeFormatter.ISO_LOCAL_DATE,                          // 2026-01-28
        DateTimeFormatter.ofPattern("MMM dd yyyy", Locale.ENGLISH), // Jan 28 2026
        DateTimeFormatter.ofPattern("MMM d yyyy", Locale.ENGLISH),  // Jan 8 2026
        DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH),  // 28 Jan 2026
        DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH), // 28 Jan 2026
        DateTimeFormatter.ofPattern("d/M/yyyy"),                    // 28/1/2026
        DateTimeFormatter.ofPattern("dd/MM/yyyy"),                  // 28/01/2026
    };

    public Deadline(String desc, String by) {
        super(desc);
        this.dueDate = parseDate(by);
    }

    // try multiple date formats
    private Object parseDate(String by) {
        String cleaned = by.trim().replaceAll("(\\d)(st|nd|rd|th)", "$1"); // remove 10th -> 10
        for (DateTimeFormatter fmt : FORMATS) {
            try {
                return LocalDate.parse(cleaned, fmt);
            } catch (DateTimeParseException e) {
                // try next format
            }
        }
        return by; // couldn't parse, keep as string
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

    /**
     * Returns the due date if it's a LocalDate, null otherwise.
     */
    public LocalDate getDate() {
        if (dueDate instanceof LocalDate) {
            return (LocalDate) dueDate;
        }
        return null;
    }
}
