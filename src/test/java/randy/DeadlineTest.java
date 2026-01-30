package randy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDate;

public class DeadlineTest {
    
    @Test
    public void toString_validDate_formatsCorrectly() {
        Deadline d = new Deadline("submit report", "2025-10-15");
        assertEquals("[D][ ] submit report (by: Oct 15 2025)", d.toString());
    }

    @Test
    public void toString_invalidDate_keepsOriginalString() {
        Deadline d = new Deadline("submit report", "next monday");
        assertEquals("[D][ ] submit report (by: next monday)", d.toString());
    }

    @Test
    public void occursOn_matchingDate_returnsTrue() {
        Deadline d = new Deadline("submit report", "2025-10-15");
        assertTrue(d.occursOn(LocalDate.of(2025, 10, 15)));
    }

    @Test
    public void occursOn_differentDate_returnsFalse() {
        Deadline d = new Deadline("submit report", "2025-10-15");
        assertFalse(d.occursOn(LocalDate.of(2025, 10, 16)));
    }

    @Test
    public void occursOn_invalidDateString_returnsFalse() {
        Deadline d = new Deadline("submit report", "next monday");
        assertFalse(d.occursOn(LocalDate.of(2025, 10, 15)));
    }
}
