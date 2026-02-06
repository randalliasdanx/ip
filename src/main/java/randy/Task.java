package randy;

import java.time.LocalDate;

/**
 * A task you gotta do.
 */
public class Task {
    protected String desc;
    protected boolean done;

    public Task(String desc) {
        this.desc = desc;
        this.done = false;
    }

    public String getIcon() {
        return done ? "X" : " ";
    }

    @Override 
    public String toString() {
        return "[" + getIcon() + "] " + desc;
    }

    public void setDone() {
        this.done = true;
    }

    public void setUndone() {
        this.done = false;
    }

    public boolean isDone() {
        return done;
    }

    // override in subclasses that have dates
    public boolean isOnDate(LocalDate d) {
        return false;
    }
}
