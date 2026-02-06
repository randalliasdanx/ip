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

    public Task remove(int idx) {
        assert idx >= 0 && idx < tasks.size() : "index out of bounds";
        return tasks.remove(idx);
    }

    public Task get(int idx) {
        assert idx >= 0 && idx < tasks.size() : "index out of bounds";
        return tasks.get(idx);
    }

    public int size() {
        return tasks.size();
    }

    public Task setDone(int idx) {
        assert idx >= 0 && idx < tasks.size() : "index out of bounds";
        Task t = tasks.get(idx);
        t.setDone();
        return t;
    }

    public Task setUndone(int idx) {
        assert idx >= 0 && idx < tasks.size() : "index out of bounds";
        Task t = tasks.get(idx);
        t.setUndone();
        return t;
    }

    // filter by date
    public TaskList filterByDate(LocalDate date) {
        assert date != null : "date should not be null";
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
        assert keyword != null : "keyword should not be null";
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
