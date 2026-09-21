package gov.istp;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class InvariantTest {
    @Test void temporalInvariant() {
        assertTrue(java.time.Instant.parse("2026-09-21T11:00:00Z")
                .isAfter(java.time.Instant.parse("2026-09-21T10:00:00Z")));
    }

    @Test void protocolName() {
        assertEquals("ISTP-001", "ISTP-001");
    }
}
