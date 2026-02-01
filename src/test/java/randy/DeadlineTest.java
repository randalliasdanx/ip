package randy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import java.time.LocalDate;

public class DeadlineTest {
    
    @Test
    public void testToStringWithValidDate() {
        Deadline d = new Deadline("submit report", "2025-10-15");
        assertEquals("[D][ ] submit report (by: Oct 15 2025)", d.toString());
    }

    @Test
    public void testToStringWithInvalidDate() {
        Deadline d = new Deadline("submit report", "next monday");
        assertEquals("[D][ ] submit report (by: next monday)", d.toString());
    }

    @Test
    public void testIsOnDateMatches() {
        Deadline d = new Deadline("submit report", "2025-10-15");
        assertTrue(d.isOnDate(LocalDate.of(2025, 10, 15)));
    }

    @Test
    public void testIsOnDateNoMatch() {
        Deadline d = new Deadline("submit report", "2025-10-15");
        assertFalse(d.isOnDate(LocalDate.of(2025, 10, 16)));
    }

    @Test
    public void testIsOnDateWithStringDate() {
        Deadline d = new Deadline("submit report", "next monday");
        assertFalse(d.isOnDate(LocalDate.of(2025, 10, 15)));
    }
}
