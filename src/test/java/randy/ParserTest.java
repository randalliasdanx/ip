package randy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// tests for the parser - checks all commands and error cases
public class ParserTest {

    private TaskList tasks;

    @BeforeEach
    public void setUp() {
        tasks = new TaskList();
    }

    // ---- happy path tests ----

    @Test
    public void testByeReturnsGoodbye() {
        String res = Parser.processInput("bye", tasks);
        assertEquals("peace out! see ya later", res);
    }

    @Test
    public void testListWhenEmpty() {
        String res = Parser.processInput("list", tasks);
        assertTrue(res.contains("no tasks yet"));
    }

    @Test
    public void testListShowsTasks() {
        tasks.add(new ToDo("read book"));
        String res = Parser.processInput("list", tasks);
        assertTrue(res.contains("here's what you got:"));
        assertTrue(res.contains("read book"));
    }

    @Test
    public void testAddTodo() {
        String res = Parser.processInput("todo read book", tasks);
        assertTrue(res.contains("added:"));
        assertTrue(res.contains("[T][ ] read book"));
        assertEquals(1, tasks.size());
    }

    @Test
    public void testAddDeadline() {
        String res = Parser.processInput("deadline submit report /by 2025-10-15", tasks);
        assertTrue(res.contains("added:"));
        assertTrue(res.contains("[D]"));
        assertTrue(res.contains("submit report"));
        assertEquals(1, tasks.size());
    }

    @Test
    public void testAddEvent() {
        String res = Parser.processInput("event meeting /from 2025-10-15 /to 2025-10-16", tasks);
        assertTrue(res.contains("added:"));
        assertTrue(res.contains("[E]"));
        assertTrue(res.contains("meeting"));
        assertEquals(1, tasks.size());
    }

    @Test
    public void testMarkTask() {
        tasks.add(new ToDo("read book"));
        String res = Parser.processInput("mark 1", tasks);
        assertTrue(res.contains("marked as done"));
        assertTrue(res.contains("[X]"));
    }

    @Test
    public void testUnmarkTask() {
        tasks.add(new ToDo("read book"));
        tasks.setDone(0);
        String res = Parser.processInput("unmark 1", tasks);
        assertTrue(res.contains("unmarked"));
        assertTrue(res.contains("[ ]"));
    }

    @Test
    public void testDeleteTask() {
        tasks.add(new ToDo("read book"));
        String res = Parser.processInput("delete 1", tasks);
        assertTrue(res.contains("deleted:"));
        assertEquals(0, tasks.size());
    }

    @Test
    public void testFindWithResults() {
        tasks.add(new ToDo("read book"));
        tasks.add(new ToDo("sleep"));
        String res = Parser.processInput("find book", tasks);
        assertTrue(res.contains("found these:"));
        assertTrue(res.contains("read book"));
    }

    @Test
    public void testFindNoResults() {
        tasks.add(new ToDo("read book"));
        String res = Parser.processInput("find xyz", tasks);
        assertTrue(res.contains("no matches"));
    }

    @Test
    public void testOnCommand() {
        tasks.add(new Deadline("submit report", "2025-10-15"));
        String res = Parser.processInput("on 2025-10-15", tasks);
        assertTrue(res.contains("tasks on 2025-10-15:"));
        assertTrue(res.contains("submit report"));
    }

    // ---- error case tests ----

    @Test
    public void testUnknownCommand() {
        String res = Parser.processInput("blah", tasks);
        assertEquals("huh? idk what that means", res);
    }

    @Test
    public void testEmptyTodoDetails() {
        String res = Parser.processInput("todo", tasks);
        assertEquals("uh you forgot to add details", res);
    }

    @Test
    public void testMarkNotANumber() {
        tasks.add(new ToDo("read book"));
        String res = Parser.processInput("mark abc", tasks);
        assertEquals("thats not a valid task number", res);
    }

    @Test
    public void testMarkOutOfRange() {
        tasks.add(new ToDo("read book"));
        String res = Parser.processInput("mark 5", tasks);
        assertEquals("task number out of range", res);
    }

    @Test
    public void testDeleteOutOfRange() {
        tasks.add(new ToDo("read book"));
        String res = Parser.processInput("delete 99", tasks);
        assertEquals("task number out of range", res);
    }

    @Test
    public void testBadDeadlineFormat() {
        String res = Parser.processInput("deadline no date here", tasks);
        assertTrue(res.contains("wrong format"));
    }

    @Test
    public void testBadEventFormat() {
        String res = Parser.processInput("event no dates here", tasks);
        assertTrue(res.contains("wrong format"));
    }

    @Test
    public void testBadDateForOnCommand() {
        String res = Parser.processInput("on not-a-date", tasks);
        assertTrue(res.contains("format"));
    }

    @Test
    public void testNullInput() {
        String res = Parser.processInput(null, tasks);
        assertEquals("uh you forgot to add details", res);
    }

    @Test
    public void testBlankInput() {
        String res = Parser.processInput("   ", tasks);
        assertEquals("uh you forgot to add details", res);
    }

    @Test
    public void testMultipleAdds() {
        Parser.processInput("todo task one", tasks);
        Parser.processInput("todo task two", tasks);
        Parser.processInput("todo task three", tasks);
        assertEquals(3, tasks.size());
    }
}
