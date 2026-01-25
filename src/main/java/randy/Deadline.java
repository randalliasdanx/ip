package randy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Deadline extends Task {
    protected Object by;

    public Deadline(String description, String by) {
        super(description);
        try {
            this.by = LocalDate.parse(by);
        } catch (DateTimeParseException e) {
            this.by = by;
        }
    }

    @Override
    public String toString() {
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
