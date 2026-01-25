package randy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Event extends Task {
    protected Object start;
    protected Object end;

    public Event(String description, String start, String end) {
        super(description);
        try {
            this.start = LocalDate.parse(start);
        } catch (DateTimeParseException e) {
            this.start = start;
        }
        try {
            this.end = LocalDate.parse(end);
        } catch (DateTimeParseException e) {
            this.end = end;
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd yyyy");
        String startStr = (start instanceof LocalDate) 
            ? ((LocalDate) start).format(formatter) 
            : start.toString();
        String endStr = (end instanceof LocalDate) 
            ? ((LocalDate) end).format(formatter) 
            : end.toString();
        return "[E]" + super.toString() + " (from: " + startStr + " to: " + endStr + ")";
    }

    @Override
    public boolean occursOn(LocalDate date) {
        if (start instanceof LocalDate && end instanceof LocalDate) {
            LocalDate startDate = (LocalDate) start;
            LocalDate endDate = (LocalDate) end;
            return !date.isBefore(startDate) && !date.isAfter(endDate);
        } else if (start instanceof LocalDate) {
            return ((LocalDate) start).equals(date);
        }
        return false;
    }
}
