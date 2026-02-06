package randy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
}
