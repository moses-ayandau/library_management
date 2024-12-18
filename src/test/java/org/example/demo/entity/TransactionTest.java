package org.example.demo.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.sql.Date;

public class TransactionTest {

    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        transaction = new Transaction();
    }

    @Test
    public void testGetAndSetID() {
        int expectedID = 1;

        transaction.setID(expectedID);
        int actualID = transaction.getID();

        assertEquals(expectedID, actualID, "The transaction ID should be set and retrieved correctly.");
    }

    @Test
    public void testGetAndSetBookID() {
        int expectedBookID = 101;

        transaction.setBookID(expectedBookID);
        int actualBookID = transaction.getBookID();

        assertEquals(expectedBookID, actualBookID, "The book ID should be set and retrieved correctly.");
    }

    @Test
    public void testGetAndSetPatronID() {
        int expectedPatronID = 202;

        transaction.setPatronID(expectedPatronID);
        int actualPatronID = transaction.getPatronID();

        assertEquals(expectedPatronID, actualPatronID, "The patron ID should be set and retrieved correctly.");
    }

    @Test
    public void testGetAndSetBorrowedDate() {
        Date expectedBorrowedDate = Date.valueOf("2024-12-01");

        transaction.setBorrowedDate(expectedBorrowedDate);
        Date actualBorrowedDate = transaction.getBorrowedDate();

        assertEquals(expectedBorrowedDate, actualBorrowedDate, "The borrowed date should be set and retrieved correctly.");
    }

    @Test
    public void testGetAndSetReturnedDate() {
        Date expectedReturnedDate = Date.valueOf("2024-12-10");

        // When
        transaction.setReturnedDate(expectedReturnedDate);
        Date actualReturnedDate = transaction.getReturnedDate();

        // Then
        assertEquals(expectedReturnedDate, actualReturnedDate, "The returned date should be set and retrieved correctly.");
    }

    @Test
    public void testGetAndSetDueDate() {
        // Given
        Date expectedDueDate = Date.valueOf("2024-12-15");

        // When
        transaction.setDueDate(expectedDueDate);
        Date actualDueDate = transaction.getDueDate();

        // Then
        assertEquals(expectedDueDate, actualDueDate, "The due date should be set and retrieved correctly.");
    }

    @Test
    public void testTransactionConstructor() {
        // Given
        int expectedID = 1;
        int expectedBookID = 101;
        int expectedPatronID = 202;
        Date expectedBorrowedDate = Date.valueOf("2024-12-01");
        Date expectedReturnedDate = Date.valueOf("2024-12-10");
        Date expectedDueDate = Date.valueOf("2024-12-15");

        // When
        transaction.setID(expectedID);
        transaction.setBookID(expectedBookID);
        transaction.setPatronID(expectedPatronID);
        transaction.setBorrowedDate(expectedBorrowedDate);
        transaction.setReturnedDate(expectedReturnedDate);
        transaction.setDueDate(expectedDueDate);

        // Then
        assertEquals(expectedID, transaction.getID(), "The transaction ID should be correctly set.");
        assertEquals(expectedBookID, transaction.getBookID(), "The book ID should be correctly set.");
        assertEquals(expectedPatronID, transaction.getPatronID(), "The patron ID should be correctly set.");
        assertEquals(expectedBorrowedDate, transaction.getBorrowedDate(), "The borrowed date should be correctly set.");
        assertEquals(expectedReturnedDate, transaction.getReturnedDate(), "The returned date should be correctly set.");
        assertEquals(expectedDueDate, transaction.getDueDate(), "The due date should be correctly set.");
    }

    @Test
    public void testSetMemberID() {
        // Given
        int expectedMemberID = 303;

        // When
        transaction.setMemberID(expectedMemberID);
        int actualMemberID = transaction.getPatronID();  // patronID is used as memberID

        // Then
        assertEquals(expectedMemberID, actualMemberID, "The member ID should be set and retrieved correctly.");
    }
}
