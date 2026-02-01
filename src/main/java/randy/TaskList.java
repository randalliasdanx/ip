package randy;

import java.util.ArrayList;
import java.time.LocalDate;

/**
 * Wrapper around ArrayList to manage tasks.
 */
public class TaskList {
    private ArrayList<Task> tasks;

    public TaskList() {
        tasks = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> loadedTasks) {
        this.tasks = loadedTasks;
    }

    public void add(Task t) {
        tasks.add(t);
    }

    public Task remove(int idx) {
        return tasks.remove(idx);
    }

    public Task get(int idx) {
        return tasks.get(idx);
    }

    public int size() {
        return tasks.size();
    }

    public Task setDone(int idx) {
        Task t = tasks.get(idx);
        t.setDone();
        return t;
    }

    public Task setUndone(int idx) {
        Task t = tasks.get(idx);
        t.setUndone();
        return t;
    }

    // filter by date
    public TaskList filterByDate(LocalDate date) {
        TaskList filtered = new TaskList();
        for (Task t : tasks) {
            if (t.isOnDate(date)) {
                filtered.add(t);
            }
        }
        return filtered;
    }

    // search by keyword
    public TaskList search(String keyword) {
        TaskList matches = new TaskList();
        for (Task t : tasks) {
            String lower = t.toString().toLowerCase();
            if (lower.contains(keyword.toLowerCase())) {
                matches.add(t);
            }
        }
        return matches;
    }
}
