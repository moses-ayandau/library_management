package org.example.demo.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Date;

public class ReservationTest {

    private Reservation reservation;

    @BeforeEach
    public void setUp() {
        reservation = new Reservation();
    }

    @Test
    public void testGetAndSetID() {
        int expectedID = 1;

        reservation.setID(expectedID);
        int actualID = reservation.getID();

        assertEquals(expectedID, actualID, "The reservation ID should be set and retrieved correctly.");
    }

    @Test
    public void testGetAndSetPatronID() {
        int expectedPatronID = 101;

        reservation.setPatronID(expectedPatronID);
        int actualPatronID = reservation.getPatronID();

        assertEquals(expectedPatronID, actualPatronID, "The patron ID should be set and retrieved correctly.");
    }

    @Test
    public void testGetAndSetBookID() {
        int expectedBookID = 202;

        reservation.setBookID(expectedBookID);
        int actualBookID = reservation.getBookID();

        assertEquals(expectedBookID, actualBookID, "The book ID should be set and retrieved correctly.");
    }

    @Test
    public void testGetAndSetReservedDate() {
        Date expectedReservedDate = Date.valueOf("2024-12-13");

        reservation.setReservedDate(expectedReservedDate);
        Date actualReservedDate = reservation.getReservedDate();

        assertEquals(expectedReservedDate, actualReservedDate, "The reserved date should be set and retrieved correctly.");
    }

    @Test
    public void testGetAndSetStatus() {
        ReservationStatus expectedStatus = ReservationStatus.PENDING;

        reservation.setStatus(expectedStatus);
        ReservationStatus actualStatus = reservation.getStatus();

        assertEquals(expectedStatus, actualStatus, "The reservation status should be set and retrieved correctly.");
    }

    @Test
    public void testGetAndSetDueDate() {
        Date expectedDueDate = Date.valueOf("2024-12-20");

        reservation.setDueDate(expectedDueDate);
        Date actualDueDate = reservation.getDueDate();

        assertEquals(expectedDueDate, actualDueDate, "The due date should be set and retrieved correctly.");
    }

    @Test
    public void testReservationConstructor() {
        int expectedID = 1;
        int expectedPatronID = 101;
        int expectedBookID = 202;
        Date expectedReservedDate = Date.valueOf("2024-12-13");
        ReservationStatus expectedStatus = ReservationStatus.PENDING;
        Date expectedDueDate = Date.valueOf("2024-12-20");

        reservation.setID(expectedID);
        reservation.setPatronID(expectedPatronID);
        reservation.setBookID(expectedBookID);
        reservation.setReservedDate(expectedReservedDate);
        reservation.setStatus(expectedStatus);
        reservation.setDueDate(expectedDueDate);

        assertEquals(expectedID, reservation.getID(), "The reservation ID should be correctly set.");
        assertEquals(expectedPatronID, reservation.getPatronID(), "The patron ID should be correctly set.");
        assertEquals(expectedBookID, reservation.getBookID(), "The book ID should be correctly set.");
        assertEquals(expectedReservedDate, reservation.getReservedDate(), "The reserved date should be correctly set.");
        assertEquals(expectedStatus, reservation.getStatus(), "The status should be correctly set.");
        assertEquals(expectedDueDate, reservation.getDueDate(), "The due date should be correctly set.");
    }
}
