package randy;

/**
 * Represents a basic task with no date attached.
 */
public class ToDo extends Task {

    /**
     * Creates a new ToDo task.
     *
     * @param desc Description of the task.
     */
    public ToDo(String desc) {
        super(desc);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
