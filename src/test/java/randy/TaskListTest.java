package randy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;

public class TaskListTest {

    @Test
    public void testAddIncreasesSize() {
        TaskList list = new TaskList();
        assertEquals(0, list.size());
        list.add(new ToDo("read book"));
        assertEquals(1, list.size());
    }

    @Test
    public void testRemoveDecreasesSize() {
        TaskList list = new TaskList();
        list.add(new ToDo("read book"));
        list.add(new ToDo("sleep"));
        assertEquals(2, list.size());
        list.remove(0);
        assertEquals(1, list.size());
    }

    @Test
    public void testSetDoneMarksTask() {
        TaskList list = new TaskList();
        list.add(new ToDo("read book"));
        Task t = list.setDone(0);
        assertTrue(t.toString().contains("[X]"));
    }

    @Test
    public void testSetUndoneUnmarksTask() {
        TaskList list = new TaskList();
        list.add(new ToDo("read book"));
        list.setDone(0);
        Task t = list.setUndone(0);
        assertTrue(t.toString().contains("[ ]"));
    }

    @Test
    public void testFilterByDateFindsMatches() {
        TaskList list = new TaskList();
        list.add(new Deadline("task1", "2025-10-15"));
        list.add(new Deadline("task2", "2025-10-16"));
        list.add(new Deadline("task3", "2025-10-15"));
        
        TaskList results = list.filterByDate(LocalDate.of(2025, 10, 15));
        assertEquals(2, results.size());
    }
}
