package randy;

import java.time.LocalDate;

/**
 * Represents a task with a description and a completion status.
 * This is the base class for ToDo, Deadline, and Event.
 */
public class Task {
    protected String desc;
    protected boolean done;

    /**
     * Creates a new task that is initially not done.
     *
     * @param desc Description of the task.
     */
    public Task(String desc) {
        this.desc = desc;
        this.done = false;
    }

    /**
     * Returns the description of this task.
     *
     * @return The task description.
     */
    public String getDescription() {
        return desc;
    }

    /**
     * Returns the status icon for this task.
     *
     * @return "X" if done, " " (space) if not done.
     */
    public String getIcon() {
        return done ? "X" : " ";
    }

    @Override
    public String toString() {
        return "[" + getIcon() + "] " + desc;
    }

    /** Marks this task as done. */
    public void setDone() {
        this.done = true;
    }

    /** Marks this task as not done. */
    public void setUndone() {
        this.done = false;
    }

    /**
     * Checks if this task falls on the given date.
     * Subclasses with date fields should override this.
     *
     * @param d The date to check against.
     * @return true if the task occurs on that date.
     */
    public boolean isOnDate(LocalDate d) {
        return false;
    }
}
