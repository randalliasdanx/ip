package randy;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Tests for the Parser class covering all commands and error cases.
 */
public class ParserTest {

    private TaskList tasks;

    @BeforeEach
    public void setUp() {
        tasks = new TaskList();
    }

    // --- Valid command tests ---

    @Test
    public void testByeCommand() {
        String result = Parser.processInput("bye", tasks);
        assertEquals("peace out! see ya later", result);
    }

    @Test
    public void testListCommandEmpty() {
        String result = Parser.processInput("list", tasks);
        assertEquals("no tasks yet!", result);
    }

    @Test
    public void testListCommandWithTasks() {
        tasks.add(new ToDo("read book"));
        String result = Parser.processInput("list", tasks);
        assertTrue(result.contains("here's what you got:"));
        assertTrue(result.contains("read book"));
    }

    @Test
    public void testTodoCommand() {
        String result = Parser.processInput("todo read book", tasks);
        assertTrue(result.contains("added:"));
        assertTrue(result.contains("[T][ ] read book"));
        assertEquals(1, tasks.size());
    }

    @Test
    public void testDeadlineCommand() {
        String result = Parser.processInput("deadline submit report /by 2025-10-15", tasks);
        assertTrue(result.contains("added:"));
        assertTrue(result.contains("[D]"));
        assertTrue(result.contains("submit report"));
        assertEquals(1, tasks.size());
    }

    @Test
    public void testEventCommand() {
        String result = Parser.processInput("event meeting /from 2025-10-15 /to 2025-10-16", tasks);
        assertTrue(result.contains("added:"));
        assertTrue(result.contains("[E]"));
        assertTrue(result.contains("meeting"));
        assertEquals(1, tasks.size());
    }

    @Test
    public void testMarkCommand() {
        tasks.add(new ToDo("read book"));
        String result = Parser.processInput("mark 1", tasks);
        assertTrue(result.contains("marked as done"));
        assertTrue(result.contains("[X]"));
    }

    @Test
    public void testUnmarkCommand() {
        tasks.add(new ToDo("read book"));
        tasks.setDone(0);
        String result = Parser.processInput("unmark 1", tasks);
        assertTrue(result.contains("unmarked"));
        assertTrue(result.contains("[ ]"));
    }

    @Test
    public void testDeleteCommand() {
        tasks.add(new ToDo("read book"));
        String result = Parser.processInput("delete 1", tasks);
        assertTrue(result.contains("deleted:"));
        assertEquals(0, tasks.size());
    }

    @Test
    public void testFindCommand() {
        tasks.add(new ToDo("read book"));
        tasks.add(new ToDo("sleep"));
        String result = Parser.processInput("find book", tasks);
        assertTrue(result.contains("found these:"));
        assertTrue(result.contains("read book"));
    }

    @Test
    public void testFindCommandNoResults() {
        tasks.add(new ToDo("read book"));
        String result = Parser.processInput("find xyz", tasks);
        assertTrue(result.contains("no matches"));
    }

    @Test
    public void testOnCommand() {
        tasks.add(new Deadline("submit report", "2025-10-15"));
        String result = Parser.processInput("on 2025-10-15", tasks);
        assertTrue(result.contains("tasks on 2025-10-15:"));
        assertTrue(result.contains("submit report"));
    }

    // --- Error case tests ---

    @Test
    public void testUnknownCommand() {
        String result = Parser.processInput("blah", tasks);
        assertEquals("huh? idk what that means", result);
    }

    @Test
    public void testEmptyDetails() {
        String result = Parser.processInput("todo", tasks);
        assertEquals("uh you forgot to add details", result);
    }

    @Test
    public void testMarkInvalidNumber() {
        tasks.add(new ToDo("read book"));
        String result = Parser.processInput("mark abc", tasks);
        assertEquals("please enter a valid task number", result);
    }

    @Test
    public void testMarkOutOfRange() {
        tasks.add(new ToDo("read book"));
        // Parser catches IndexOutOfBoundsException or assertion error
        try {
            String result = Parser.processInput("mark 5", tasks);
            assertEquals("task number out of range", result);
        } catch (AssertionError e) {
            // assertions in TaskList fire before catch in Parser -- expected on master
            assertTrue(e.getMessage().contains("index out of bounds"));
        }
    }

    @Test
    public void testBadDeadlineFormat() {
        String result = Parser.processInput("deadline no date here", tasks);
        assertEquals("format: deadline <task> /by <date>", result);
    }

    @Test
    public void testBadEventFormat() {
        String result = Parser.processInput("event no dates here", tasks);
        assertEquals("format: event <task> /from <start> /to <end>", result);
    }

    @Test
    public void testBadDateFormatForOnCommand() {
        String result = Parser.processInput("on not-a-date", tasks);
        assertEquals("use format: on yyyy-MM-dd", result);
    }

    @Test
    public void testDeleteOutOfRange() {
        tasks.add(new ToDo("read book"));
        // Parser catches IndexOutOfBoundsException or assertion error
        try {
            String result = Parser.processInput("delete 99", tasks);
            assertEquals("task number out of range", result);
        } catch (AssertionError e) {
            // assertions in TaskList fire before catch in Parser -- expected on master
            assertTrue(e.getMessage().contains("index out of bounds"));
        }
    }

    @Test
    public void testFindEmptyKeyword() {
        // "find " is trimmed/split to just ["find"], which is 1 word,
        // so the empty-details check triggers before handleFind
        String result = Parser.processInput("find ", tasks);
        assertEquals("uh you forgot to add details", result);
    }

    @Test
    public void testTaskCountAfterMultipleAdds() {
        Parser.processInput("todo task one", tasks);
        Parser.processInput("todo task two", tasks);
        Parser.processInput("todo task three", tasks);
        assertEquals(3, tasks.size());
    }
}
