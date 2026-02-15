package randy;

import java.time.LocalDate;

/**
 * Represents a task with a description and completion status.
 * 
 * This is the base class for all task types (ToDo, Deadline, Event).
 * A task has a description and can be marked as done or undone.
 * Subclasses may override the isOnDate method to support date filtering.
 */
public class Task {
    protected String desc;
    protected boolean done;

    /**
     * Creates a new task with the given description.
     * The task is initially marked as not done.
     * 
     * @param desc the task description
     */
    public Task(String desc) {
        assert desc != null && !desc.trim().isEmpty() : "task description cannot be null or empty";
        this.desc = desc;
        this.done = false;
    }

    /**
     * Returns the completion status icon.
     * Returns "X" if the task is done, " " otherwise.
     * 
     * @return the status icon
     */
    public String getIcon() {
        return done ? "X" : " ";
    }

    /**
     * Returns a string representation of the task.
     * Format: [X] <description> or [ ] <description>
     * 
     * @return the task as a formatted string
     */
    @Override 
    public String toString() {
        return "[" + getIcon() + "] " + desc;
    }

    /**
     * Marks this task as done.
     */
    public void setDone() {
        this.done = true;
    }

    /**
     * Marks this task as not done.
     */
    public void setUndone() {
        this.done = false;
    }

    /**
     * Checks if this task occurs on the given date.
     * Default implementation always returns false.
     * Subclasses with date information should override this method.
     * 
     * @param d the date to check
     * @return true if the task occurs on this date, false otherwise
     */
    public boolean isOnDate(LocalDate d) {
        return false;
    }
}
