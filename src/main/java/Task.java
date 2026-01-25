import java.time.LocalDate;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    @Override 
    public String toString() {
        return "[" + getStatusIcon() + "]" + " " + description;
    }

    public void mark() {
        this.isDone = true;
    }

    public void unmark() {
        this.isDone = false;
    }

    /**
     * Checks if this task occurs on the given date.
     * @param date The date to check against
     * @return true if the task occurs on this date, false otherwise
     */
    public boolean occursOn(LocalDate date) {
        return false; // Base Task doesn't have a date
    }
}
