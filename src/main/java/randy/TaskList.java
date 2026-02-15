package randy;

import java.util.ArrayList;
import java.time.LocalDate;

/**
 * Manages a list of tasks with add, remove, search, and filter operations.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /** Creates an empty task list. */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Creates a task list from a pre-existing ArrayList of tasks.
     *
     * @param loadedTasks The tasks to initialise the list with.
     */
    public TaskList(ArrayList<Task> loadedTasks) {
        assert loadedTasks != null : "loaded tasks should not be null";
        this.tasks = loadedTasks;
    }

    /**
     * Adds one or more tasks to the list (varargs).
     *
     * @param newTasks The task(s) to add.
     */
    public void add(Task... newTasks) {
        for (Task t : newTasks) {
            assert t != null : "task to add should not be null";
            tasks.add(t);
        }
    }

    /**
     * Removes and returns the task at the given index.
     *
     * @param idx Zero-based index of the task to remove.
     * @return The removed task.
     */
    public Task remove(int idx) {
        assert idx >= 0 && idx < tasks.size() : "index out of bounds";
        return tasks.remove(idx);
    }

    /**
     * Returns the task at the given index.
     *
     * @param idx Zero-based index.
     * @return The task at that index.
     */
    public Task get(int idx) {
        assert idx >= 0 && idx < tasks.size() : "index out of bounds";
        return tasks.get(idx);
    }

    /** Returns the number of tasks in the list. */
    public int size() {
        return tasks.size();
    }

    /** Returns true if the task list is empty. */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Marks the task at the given index as done.
     *
     * @param idx Zero-based index.
     * @return The task that was marked.
     */
    public Task setDone(int idx) {
        assert idx >= 0 && idx < tasks.size() : "index out of bounds";
        Task t = tasks.get(idx);
        t.setDone();
        return t;
    }

    /**
     * Marks the task at the given index as not done.
     *
     * @param idx Zero-based index.
     * @return The task that was unmarked.
     */
    public Task setUndone(int idx) {
        assert idx >= 0 && idx < tasks.size() : "index out of bounds";
        Task t = tasks.get(idx);
        t.setUndone();
        return t;
    }

    /**
     * Returns a new TaskList containing only tasks that fall on the given date.
     *
     * @param date The date to filter by.
     * @return A TaskList of matching tasks.
     */
    public TaskList filterByDate(LocalDate date) {
        assert date != null : "date should not be null";
        TaskList filtered = new TaskList();
        if (date == null) {
            return filtered;
        }
        for (Task t : tasks) {
            if (t.isOnDate(date)) {
                filtered.add(t);
            }
        }
        return filtered;
    }

    /**
     * Searches for tasks whose string representation contains the keyword.
     * Matching is case-insensitive.
     *
     * @param keyword The keyword to search for.
     * @return A TaskList of matching tasks.
     */
    public TaskList search(String keyword) {
        assert keyword != null : "keyword should not be null";
        TaskList matches = new TaskList();
        if (keyword == null || keyword.trim().isEmpty()) {
            return matches;
        }
        String lowerKeyword = keyword.toLowerCase();
        for (Task t : tasks) {
            if (t.toString().toLowerCase().contains(lowerKeyword)) {
                matches.add(t);
            }
        }
        return matches;
    }

    /**
     * Checks if index is within valid range.
     *
     * @param idx The index to check.
     * @return true if the index is valid.
     */
    private boolean isValidIndex(int idx) {
        return idx >= 0 && idx < tasks.size();
    }

    /**
     * Checks if an identical task already exists in the list.
     * Comparison is based on the string representation of the task.
     *
     * @param task The task to check for duplicates.
     * @return true if a task with the same string representation exists.
     */
    public boolean hasDuplicate(Task task) {
        if (task == null) {
            return false;
        }
        String taskStr = task.toString();
        for (Task t : tasks) {
            if (t.toString().equals(taskStr)) {
                return true;
            }
        }
        return false;
    }
}
