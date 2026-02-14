package randy;

/**
 * Represents a simple todo task without any date or time constraints.
 * This is the most basic type of task in the system.
 */
public class ToDo extends Task {

    /**
     * Creates a new ToDo task.
     *
     * @param desc Description of what needs to be done
     */
    public ToDo(String desc) {
        super(desc);
    }

    /**
     * Returns string representation with [T] prefix.
     */
    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
