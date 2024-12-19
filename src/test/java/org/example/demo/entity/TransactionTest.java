package org.example.demo.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    private Transaction transaction;

    @BeforeEach
    public void setUp() {
        transaction = new Transaction();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 100})
    public void testGetAndSetID(int id) {
        transaction.setID(id);
        assertEquals(id, transaction.getID(), "The transaction ID should be set and retrieved correctly.");
    }

    @ParameterizedTest
    @ValueSource(ints = {101, 202, 303})
    public void testGetAndSetBookID(int bookID) {
        transaction.setBookID(bookID);
        assertEquals(bookID, transaction.getBookID(), "The book ID should be set and retrieved correctly.");
    }

    @ParameterizedTest
    @ValueSource(ints = {202, 404, 505})
    public void testGetAndSetPatronID(int patronID) {
        transaction.setPatronID(patronID);
        assertEquals(patronID, transaction.getPatronID(), "The patron ID should be set and retrieved correctly.");
    }

    @ParameterizedTest
    @CsvSource({
            "2024-12-01, 2024-12-10, 2024-12-15",
            "2024-01-01, 2024-01-05, 2024-01-10",
            "2023-11-25, 2023-12-01, 2023-12-05"
    })
    public void testDates(String borrowed, String returned, String due) {
        Date borrowedDate = Date.valueOf(borrowed);
        Date returnedDate = Date.valueOf(returned);
        Date dueDate = Date.valueOf(due);

        transaction.setBorrowedDate(borrowedDate);
        transaction.setReturnedDate(returnedDate);
        transaction.setDueDate(dueDate);

        assertEquals(borrowedDate, transaction.getBorrowedDate(), "The borrowed date should be correctly set and retrieved.");
        assertEquals(returnedDate, transaction.getReturnedDate(), "The returned date should be correctly set and retrieved.");
        assertEquals(dueDate, transaction.getDueDate(), "The due date should be correctly set and retrieved.");
    }

    @ParameterizedTest
    @CsvSource({
            "1, 101, 202, 2024-12-01, 2024-12-10, 2024-12-15",
            "2, 102, 203, 2024-01-01, 2024-01-05, 2024-01-10",
            "3, 103, 204, 2023-11-25, 2023-12-01, 2023-12-05"
    })
    public void testTransactionAttributes(int id, int bookID, int patronID, String borrowed, String returned, String due) {
        Date borrowedDate = Date.valueOf(borrowed);
        Date returnedDate = Date.valueOf(returned);
        Date dueDate = Date.valueOf(due);

        transaction.setID(id);
        transaction.setBookID(bookID);
        transaction.setPatronID(patronID);
        transaction.setBorrowedDate(borrowedDate);
        transaction.setReturnedDate(returnedDate);
        transaction.setDueDate(dueDate);

        assertEquals(id, transaction.getID(), "The transaction ID should be correctly set.");
        assertEquals(bookID, transaction.getBookID(), "The book ID should be correctly set.");
        assertEquals(patronID, transaction.getPatronID(), "The patron ID should be correctly set.");
        assertEquals(borrowedDate, transaction.getBorrowedDate(), "The borrowed date should be correctly set.");
        assertEquals(returnedDate, transaction.getReturnedDate(), "The returned date should be correctly set.");
        assertEquals(dueDate, transaction.getDueDate(), "The due date should be correctly set.");
    }

    @ParameterizedTest
    @ValueSource(ints = {303, 404, 505})
    public void testSetAndGetPatronID(int memberID) {
        transaction.setMemberID(memberID);
        assertEquals(memberID, transaction.getPatronID(), "The Patron ID should be set and retrieved correctly.");
    }
}
