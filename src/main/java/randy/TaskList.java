package randy;

import java.util.ArrayList;
import java.time.LocalDate;

/**
 * Manages a collection of tasks with add, remove, and search operations.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    /**
     * Creates an empty task list.
     */
    public TaskList() {
        tasks = new ArrayList<>();
    }

    /**
     * Creates a task list from existing tasks.
     */
    public TaskList(ArrayList<Task> loadedTasks) {
        assert loadedTasks != null : "loaded tasks should not be null";
        this.tasks = loadedTasks;
    }

    // varargs - can pass 1 or more tasks: add(task1) or add(task1, task2, task3)
    public void add(Task... newTasks) {
        for (Task t : newTasks) {
            assert t != null : "task to add should not be null";
            tasks.add(t);
        }
    }

    /**
     * Removes and returns the task at the given index.
     */
    public Task remove(int idx) {
        assert idx >= 0 && idx < tasks.size() : "index out of bounds";
        return tasks.remove(idx);
    }

    /**
     * Returns the task at the given index.
     */
    public Task get(int idx) {
        assert idx >= 0 && idx < tasks.size() : "index out of bounds";
        return tasks.get(idx);
    }

    /**
     * Returns the number of tasks.
     */
    public int size() {
        return tasks.size();
    }

    /**
     * Checks if task list is empty.
     */
    public boolean isEmpty() {
        return tasks.isEmpty();
    }

    /**
     * Marks task at index as done and returns it.
     */
    public Task setDone(int idx) {
        assert idx >= 0 && idx < tasks.size() : "index out of bounds";
        Task t = tasks.get(idx);
        t.setDone();
        return t;
    }

    /**
     * Marks task at index as not done and returns it.
     */
    public Task setUndone(int idx) {
        assert idx >= 0 && idx < tasks.size() : "index out of bounds";
        Task t = tasks.get(idx);
        t.setUndone();
        return t;
    }

    /**
     * Returns tasks occurring on the given date.
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
     * Returns tasks containing the keyword (case-insensitive).
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
     */
    private boolean isValidIndex(int idx) {
        return idx >= 0 && idx < tasks.size();
    }
}
