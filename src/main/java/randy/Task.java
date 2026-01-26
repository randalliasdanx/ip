package randy;

import java.time.LocalDate;

/**
 * Represents a task with a description and completion status.
 */
public class Task {
    protected String description;
    protected boolean isDone;

    /**
     * Creates a new task with the given description.
     * @param description The task description.
     */
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    /**
     * Returns X if done, space if not done.
     * @return Status icon string.
     */
    public String getStatusIcon() {
        return (isDone ? "X" : " ");
    }

    @Override 
    public String toString() {
        return "[" + getStatusIcon() + "]" + " " + description;
    }

    /**
     * Marks this task as done.
     */
    public void mark() {
        this.isDone = true;
    }

    /**
     * Marks this task as not done.
     */
    public void unmark() {
        this.isDone = false;
    }

    /**
     * Checks if this task occurs on the given date.
     * @param date The date to check.
     * @return true if task occurs on this date, false otherwise.
     */
    public boolean occursOn(LocalDate date) {
        return false;
    }
}
