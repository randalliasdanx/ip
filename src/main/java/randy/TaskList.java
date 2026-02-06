package randy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
        this.tasks = (loadedTasks != null) ? loadedTasks : new ArrayList<>();
    }

    /**
     * Adds one or more tasks to the list.
     */
    public void add(Task... newTasks) {
        for (Task t : newTasks) {
            if (t != null) {
                tasks.add(t);
            }
        }
    }

    /**
     * Removes and returns the task at the given index.
     */
    public Task remove(int idx) {
        if (!isValidIndex(idx)) {
            return null;
        }
        return tasks.remove(idx);
    }

    /**
     * Returns the task at the given index.
     */
    public Task get(int idx) {
        if (!isValidIndex(idx)) {
            return null;
        }
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
        if (!isValidIndex(idx)) {
            return null;
        }
        Task t = tasks.get(idx);
        t.setDone();
        return t;
    }

    /**
     * Marks task at index as not done and returns it.
     */
    public Task setUndone(int idx) {
        if (!isValidIndex(idx)) {
            return null;
        }
        Task t = tasks.get(idx);
        t.setUndone();
        return t;
    }

    // filter by date using streams
    public TaskList filterByDate(LocalDate date) {
        List<Task> filtered = tasks.stream()
                .filter(t -> t.isOnDate(date))
                .collect(Collectors.toList());
        return new TaskList(new ArrayList<>(filtered));
    }

    // search by keyword using streams
    public TaskList search(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        List<Task> matches = tasks.stream()
                .filter(t -> t.toString().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
        return new TaskList(new ArrayList<>(matches));
    }

    // check if any task matches condition
    public boolean hasTasksDue(LocalDate date) {
        return tasks.stream().anyMatch(t -> t.isOnDate(date));
    }

    // count tasks matching keyword
    public long countMatches(String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return tasks.stream()
                .filter(t -> t.toString().toLowerCase().contains(lowerKeyword))
                .count();
    }

    /**
     * Checks if index is within valid range.
     */
    private boolean isValidIndex(int idx) {
        return idx >= 0 && idx < tasks.size();
    }
}
