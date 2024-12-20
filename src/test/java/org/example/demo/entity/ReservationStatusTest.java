package org.example.demo.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationStatusTest {

    @Test
    public void shouldContainAllDefinedEnumValues() {
        ReservationStatus[] statuses = ReservationStatus.values();

        assertEquals(3, statuses.length, "There should be exactly 3 reservation statuses.");
        assertTrue(statusContains(statuses, ReservationStatus.PENDING), "The enum should contain PENDING.");
        assertTrue(statusContains(statuses, ReservationStatus.FULFILLED), "The enum should contain FULFILLED.");
        assertTrue(statusContains(statuses, ReservationStatus.CANCELLED), "The enum should contain CANCELLED.");
    }

    @Test
    public void shouldReturnCorrectEnumNames() {
        assertEquals("PENDING", ReservationStatus.PENDING.name(), "The name of the PENDING status should be 'PENDING'.");
        assertEquals("FULFILLED", ReservationStatus.FULFILLED.name(), "The name of the FULFILLED status should be 'FULFILLED'.");
        assertEquals("CANCELLED", ReservationStatus.CANCELLED.name(), "The name of the CANCELLED status should be 'CANCELLED'.");
    }

    @Test
    public void shouldReturnCorrectEnumOrdinals() {
        assertEquals(0, ReservationStatus.PENDING.ordinal(), "The ordinal of PENDING should be 0.");
        assertEquals(1, ReservationStatus.FULFILLED.ordinal(), "The ordinal of FULFILLED should be 1.");
        assertEquals(2, ReservationStatus.CANCELLED.ordinal(), "The ordinal of CANCELLED should be 2.");
    }

    @Test
    public void shouldCompareEnumInstancesCorrectly() {
        assertSame(ReservationStatus.PENDING, ReservationStatus.PENDING, "The PENDING status should be the same instance.");
        assertNotSame(ReservationStatus.PENDING, ReservationStatus.FULFILLED, "The PENDING and FULFILLED statuses should be different instances.");
    }

    @Test
    public void shouldReturnEnumUsingValueOfOrThrowExceptionForInvalidInput() {
        assertEquals(ReservationStatus.PENDING, ReservationStatus.valueOf("PENDING"), "valueOf should return the correct enum constant.");
        assertThrows(IllegalArgumentException.class, () -> ReservationStatus.valueOf("INVALID"), "valueOf should throw IllegalArgumentException for invalid input.");
    }

    private boolean statusContains(ReservationStatus[] statuses, ReservationStatus status) {
        for (ReservationStatus s : statuses) {
            if (s == status) {
                return true;
            }
        }
        return false;
    }
}
