package randy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;

/**
 * Tests for the Storage class covering load, save, and corruption handling.
 */
public class StorageTest {

    @TempDir
    Path tempDir;
    private String tempFilePath;
    private Storage storage;

    @BeforeEach
    public void setUp() {
        tempFilePath = tempDir.resolve("test_storage.txt").toString();
        storage = new Storage(tempFilePath);
        storage.init();
    }

    @Test
    public void testLoadFromNonExistentFile() throws IOException {
        ArrayList<Task> tasks = storage.loadTasks();
        assertEquals(0, tasks.size());
    }

    @Test
    public void testLoadFromEmptyFile() throws IOException {
        // create empty file
        new File(tempFilePath).createNewFile();
        ArrayList<Task> tasks = storage.loadTasks();
        assertEquals(0, tasks.size());
    }

    @Test
    public void testWriteAndReadToDo() throws IOException {
        TaskList tasks = new TaskList();
        tasks.add(new ToDo("read book"));
        storage.writeToFile(tasks);

        ArrayList<Task> loaded = storage.loadTasks();
        assertEquals(1, loaded.size());
        assertEquals("[T][ ] read book", loaded.get(0).toString());
    }

    @Test
    public void testWriteAndReadDeadline() throws IOException {
        TaskList tasks = new TaskList();
        tasks.add(new Deadline("submit report", "2025-10-15"));
        storage.writeToFile(tasks);

        ArrayList<Task> loaded = storage.loadTasks();
        assertEquals(1, loaded.size());
        assertTrue(loaded.get(0).toString().contains("[D]"));
        assertTrue(loaded.get(0).toString().contains("submit report"));
    }

    @Test
    public void testWriteAndReadEvent() throws IOException {
        TaskList tasks = new TaskList();
        tasks.add(new Event("meeting", "2025-10-15", "2025-10-16"));
        storage.writeToFile(tasks);

        ArrayList<Task> loaded = storage.loadTasks();
        assertEquals(1, loaded.size());
        assertTrue(loaded.get(0).toString().contains("[E]"));
        assertTrue(loaded.get(0).toString().contains("meeting"));
    }

    @Test
    public void testWriteAndReadDoneTask() throws IOException {
        TaskList tasks = new TaskList();
        ToDo todo = new ToDo("read book");
        todo.setDone();
        tasks.add(todo);
        storage.writeToFile(tasks);

        ArrayList<Task> loaded = storage.loadTasks();
        assertEquals(1, loaded.size());
        assertTrue(loaded.get(0).toString().contains("[X]"));
    }

    @Test
    public void testWriteAndReadMultipleTasks() throws IOException {
        TaskList tasks = new TaskList();
        tasks.add(new ToDo("task one"));
        tasks.add(new Deadline("task two", "2025-12-01"));
        tasks.add(new Event("task three", "2025-11-01", "2025-11-02"));
        storage.writeToFile(tasks);

        ArrayList<Task> loaded = storage.loadTasks();
        assertEquals(3, loaded.size());
    }

    @Test
    public void testCorruptedLineIsSkipped() throws IOException {
        // Write some corrupted content directly to file
        FileWriter writer = new FileWriter(tempFilePath);
        writer.write("[T][ ] valid task\n");
        writer.write("this is garbage data\n");
        writer.write("[T][ ] another valid task\n");
        writer.close();

        ArrayList<Task> loaded = storage.loadTasks();
        assertEquals(2, loaded.size());
        assertEquals("[T][ ] valid task", loaded.get(0).toString());
        assertEquals("[T][ ] another valid task", loaded.get(1).toString());
    }

    @Test
    public void testBlankLinesAreSkipped() throws IOException {
        FileWriter writer = new FileWriter(tempFilePath);
        writer.write("[T][ ] valid task\n");
        writer.write("\n");
        writer.write("   \n");
        writer.write("[T][ ] another task\n");
        writer.close();

        ArrayList<Task> loaded = storage.loadTasks();
        assertEquals(2, loaded.size());
    }
}
