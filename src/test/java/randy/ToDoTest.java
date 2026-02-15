package randy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.time.LocalDate;

/**
 * Tests for the ToDo class covering formatting and status changes.
 */
public class ToDoTest {

    @Test
    public void testToStringFormat() {
        ToDo t = new ToDo("read book");
        assertEquals("[T][ ] read book", t.toString());
    }

    @Test
    public void testToStringAfterMarkDone() {
        ToDo t = new ToDo("read book");
        t.setDone();
        assertEquals("[T][X] read book", t.toString());
    }

    @Test
    public void testToStringAfterMarkDoneThenUndone() {
        ToDo t = new ToDo("read book");
        t.setDone();
        t.setUndone();
        assertEquals("[T][ ] read book", t.toString());
    }

    @Test
    public void testIsOnDateAlwaysFalse() {
        ToDo t = new ToDo("read book");
        assertFalse(t.isOnDate(LocalDate.of(2025, 10, 15)));
    }

    @Test
    public void testGetIconNotDone() {
        ToDo t = new ToDo("read book");
        assertEquals(" ", t.getIcon());
    }

    @Test
    public void testGetIconDone() {
        ToDo t = new ToDo("read book");
        t.setDone();
        assertEquals("X", t.getIcon());
    }
}
