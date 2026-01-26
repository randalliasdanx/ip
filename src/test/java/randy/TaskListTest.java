package randy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class TaskListTest {

    @Test
    public void add_task_increasesSize() {
        TaskList list = new TaskList();
        assertEquals(0, list.size());
        list.add(new ToDo("read book"));
        assertEquals(1, list.size());
    }

    @Test
    public void delete_task_decreasesSize() {
        TaskList list = new TaskList();
        list.add(new ToDo("read book"));
        list.add(new ToDo("sleep"));
        assertEquals(2, list.size());
        list.delete(0);
        assertEquals(1, list.size());
    }

    @Test
    public void markTask_marksTaskAsDone() {
        TaskList list = new TaskList();
        list.add(new ToDo("read book"));
        Task t = list.markTask(0);
        assertTrue(t.toString().contains("[X]"));
    }

    @Test
    public void unmarkTask_marksTaskAsNotDone() {
        TaskList list = new TaskList();
        list.add(new ToDo("read book"));
        list.markTask(0);
        Task t = list.unmarkTask(0);
        assertTrue(t.toString().contains("[ ]"));
    }

    @Test
    public void getTasksOn_returnsMatchingDeadlines() {
        TaskList list = new TaskList();
        list.add(new Deadline("task1", "2025-10-15"));
        list.add(new Deadline("task2", "2025-10-16"));
        list.add(new Deadline("task3", "2025-10-15"));
        
        TaskList results = list.getTasksOn(LocalDate.of(2025, 10, 15));
        assertEquals(2, results.size());
    }
}
