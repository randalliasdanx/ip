import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    protected Object by; // Store the deadline as LocalDate or String

    // Constructor accepting a description and a date string
    public Deadline(String description, String by) {
        super(description);
        try {
            this.by = LocalDate.parse(by);  // Parse the date string to LocalDate
        } catch (DateTimeParseException e) {
            this.by = by;
        }
    }

    @Override
    public String toString() {
        // Format the deadline in "MMM dd yyyy" format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        if (by instanceof LocalDate) {
            return "[D]" + super.toString() + " (by: " + ((LocalDate) by).format(formatter) + ")";
        } else {
            return "[D]" + super.toString() + " (by: " + by + ")";
        }
    }

    @Override
    public boolean occursOn(LocalDate date) {
        if (by instanceof LocalDate) {
            return ((LocalDate) by).equals(date);
        }
        return false;
    }
}
