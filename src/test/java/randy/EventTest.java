package randy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.time.LocalDate;

/**
 * Tests for the Event class covering date range logic and formatting.
 */
public class EventTest {

    @Test
    public void testToStringWithValidDates() {
        Event e = new Event("conference", "2025-10-15", "2025-10-17");
        assertEquals("[E][ ] conference (from: Oct 15 2025 to: Oct 17 2025)", e.toString());
    }

    @Test
    public void testToStringWithStringDates() {
        Event e = new Event("meeting", "monday", "friday");
        assertEquals("[E][ ] meeting (from: monday to: friday)", e.toString());
    }

    @Test
    public void testToStringWithMixedDates() {
        Event e = new Event("trip", "2025-10-15", "sometime later");
        assertEquals("[E][ ] trip (from: Oct 15 2025 to: sometime later)", e.toString());
    }

    @Test
    public void testIsOnDateWithinRange() {
        Event e = new Event("conference", "2025-10-15", "2025-10-17");
        assertTrue(e.isOnDate(LocalDate.of(2025, 10, 16)));
    }

    @Test
    public void testIsOnDateOnStartBoundary() {
        Event e = new Event("conference", "2025-10-15", "2025-10-17");
        assertTrue(e.isOnDate(LocalDate.of(2025, 10, 15)));
    }

    @Test
    public void testIsOnDateOnEndBoundary() {
        Event e = new Event("conference", "2025-10-15", "2025-10-17");
        assertTrue(e.isOnDate(LocalDate.of(2025, 10, 17)));
    }

    @Test
    public void testIsOnDateBeforeRange() {
        Event e = new Event("conference", "2025-10-15", "2025-10-17");
        assertFalse(e.isOnDate(LocalDate.of(2025, 10, 14)));
    }

    @Test
    public void testIsOnDateAfterRange() {
        Event e = new Event("conference", "2025-10-15", "2025-10-17");
        assertFalse(e.isOnDate(LocalDate.of(2025, 10, 18)));
    }

    @Test
    public void testIsOnDateWithStringDatesReturnsFalse() {
        Event e = new Event("meeting", "monday", "friday");
        assertFalse(e.isOnDate(LocalDate.of(2025, 10, 15)));
    }

    @Test
    public void testIsOnDateWithOnlyStartDateParsed() {
        Event e = new Event("trip", "2025-10-15", "sometime later");
        assertTrue(e.isOnDate(LocalDate.of(2025, 10, 15)));
        assertFalse(e.isOnDate(LocalDate.of(2025, 10, 16)));
    }

    @Test
    public void testMarkDoneThenToString() {
        Event e = new Event("conference", "2025-10-15", "2025-10-17");
        e.setDone();
        assertTrue(e.toString().contains("[X]"));
    }
}
