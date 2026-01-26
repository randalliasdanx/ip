package randy;

import java.util.ArrayList;
import java.time.LocalDate;

/**
 * Manages a list of tasks with add, delete, and search operations.
 */
public class TaskList {
    private ArrayList<Task> arr;

    /**
     * Creates an empty TaskList.
     */
    public TaskList() {
        arr = new ArrayList<>();
    }

    /**
     * Creates a TaskList from an existing ArrayList.
     * @param arr The ArrayList of tasks.
     */
    public TaskList(ArrayList<Task> arr) {
        this.arr = arr;
    }

    /**
     * Adds a task to the list.
     * @param t The task to add.
     */
    public void add(Task t) {
        arr.add(t);
    }

    /**
     * Deletes and returns the task at the given index.
     * @param index The 0-based index.
     * @return The deleted task.
     */
    public Task delete(int index) {
        return arr.remove(index);
    }

    /**
     * Gets the task at the given index.
     * @param index The 0-based index.
     * @return The task at that index.
     */
    public Task get(int index) {
        return arr.get(index);
    }

    /**
     * Returns the number of tasks in the list.
     * @return The size of the list.
     */
    public int size() {
        return arr.size();
    }

    /**
     * Marks a task as done.
     * @param index The 0-based index.
     * @return The marked task.
     */
    public Task markTask(int index) {
        Task t = arr.get(index);
        t.mark();
        return t;
    }

    /**
     * Marks a task as not done.
     * @param index The 0-based index.
     * @return The unmarked task.
     */
    public Task unmarkTask(int index) {
        Task t = arr.get(index);
        t.unmark();
        return t;
    }

    /**
     * Finds all tasks that occur on the given date.
     * @param date The date to search for.
     * @return TaskList containing matching tasks.
     */
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
