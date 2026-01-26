package randy;

/**
 * Represents a simple todo task without any date.
 */
public class ToDo extends Task {
    /**
     * Creates a ToDo task.
     * @param description Task description.
     */
    public ToDo(String description) {
        super(description);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
