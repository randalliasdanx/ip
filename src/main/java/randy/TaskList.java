package randy;

import java.util.ArrayList;
import java.time.LocalDate;

public class TaskList {
    private ArrayList<Task> arr;

    public TaskList() {
        arr = new ArrayList<>();
    }

    public TaskList(ArrayList<Task> arr) {
        this.arr = arr;
    }

    public void add(Task t) {
        arr.add(t);
    }

    public Task delete(int index) {
        return arr.remove(index);
    }

    public Task get(int index) {
        return arr.get(index);
    }

    public int size() {
        return arr.size();
    }

    public Task markTask(int index) {
        Task t = arr.get(index);
        t.mark();
        return t;
    }

    public Task unmarkTask(int index) {
        Task t = arr.get(index);
        t.unmark();
        return t;
    }

    public TaskList getTasksOn(LocalDate date) {
        TaskList results = new TaskList();
        for (Task t : arr) {
            if (t.occursOn(date)) {
                results.add(t);
            }
        }
        return results;
    }
}
