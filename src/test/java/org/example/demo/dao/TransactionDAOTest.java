package org.example.demo.dao;

import org.example.demo.db.conn.DatabaseConnection;
import org.example.demo.entity.Transaction;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class TransactionDAOTest {

    private TransactionDAO transactionDAO;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {

        DatabaseConnection.configureH2ForTesting();
        connection = DatabaseConnection.getConnection();
        transactionDAO = new TransactionDAO();

        try (Statement stmt = connection.createStatement()) {

            stmt.execute("DROP TABLE IF EXISTS transaction");
            stmt.execute("""
            CREATE TABLE IF NOT EXISTS transaction (
                ID INT PRIMARY KEY AUTO_INCREMENT,
                bookID INT,
                userID INT,
                borrowedDate DATE,
                dueDate DATE,
                returnedDate DATE
            );
        """);

            stmt.execute("""
            INSERT INTO transaction (bookID, userID, borrowedDate, dueDate)
            VALUES
            (1, 1, CURRENT_DATE, CURRENT_DATE + 7),
            (2, 2, CURRENT_DATE, CURRENT_DATE + 7);
        """);
        }
    }



    @AfterEach
    void tearDown() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM transaction");
        }
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    public void testCreateTransaction_Successful() throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setBookID(3);
        transaction.setPatronID(3);
        transaction.setBorrowedDate(new java.sql.Date(new Date().getTime()));
        transaction.setDueDate(new java.sql.Date(new Date().getTime() + 7 * 24 * 60 * 60 * 1000));

        boolean result = transactionDAO.createTransaction(transaction);

        assertTrue(result);
        assertTrue(transaction.getID() > 0);
    }

    @Test
    public void testReturnBook_Successful() throws SQLException {
        Transaction transaction = new Transaction();
        transaction.setBookID(1);
        transaction.setPatronID(1);
        transaction.setBorrowedDate(new java.sql.Date(new Date().getTime()));
        transaction.setDueDate(new java.sql.Date(new Date().getTime() + 7 * 24 * 60 * 60 * 1000));
        transactionDAO.createTransaction(transaction);

        Date returnDate = new Date();

        boolean result = transactionDAO.returnBook(transaction.getID(), new java.sql.Date(returnDate.getTime()));

        assertTrue(result);

        try (PreparedStatement stmt = connection.prepareStatement("SELECT returnedDate FROM transaction WHERE ID = ?")) {
            stmt.setInt(1, transaction.getID());
            try (ResultSet rs = stmt.executeQuery()) {
                assertTrue(rs.next());
                assertNotNull(rs.getDate("returnedDate"));
            }
        }
    }
    @Test
    public void testGetActiveTransactions_MultipleTransactions() throws SQLException {
        Transaction transaction1 = new Transaction();
        transaction1.setBookID(1);
        transaction1.setPatronID(1);
        transaction1.setBorrowedDate(new java.sql.Date(new Date().getTime()));
        transaction1.setDueDate(new java.sql.Date(new Date().getTime() + 7 * 24 * 60 * 60 * 1000));
        transactionDAO.createTransaction(transaction1);

        Transaction transaction2 = new Transaction();
        transaction2.setBookID(2);
        transaction2.setPatronID(2);
        transaction2.setBorrowedDate(new java.sql.Date(new Date().getTime()));
        transaction2.setDueDate(new java.sql.Date(new Date().getTime() + 7 * 24 * 60 * 60 * 1000));
        transactionDAO.createTransaction(transaction2);

        List<Transaction> transactions = transactionDAO.getActiveTransactions();

        assertNotNull(transactions);
        assertTrue(transactions.size() > 1);

        assertTrue(transactions.stream().anyMatch(t -> t.getID() == transaction1.getID()));
        assertTrue(transactions.stream().anyMatch(t -> t.getID() == transaction2.getID()));
    }


    @Test
    public void testGetTransactionById_NonExistentTransaction() throws SQLException {
        Transaction transaction = transactionDAO.getTransactionById(999);

        assertNull(transaction);
    }
    @Test
    public void testReturnNonExistentBook() throws SQLException {
        boolean result = transactionDAO.returnBook(999, new java.sql.Date(new Date().getTime()));

        assertFalse(result, "Returning a non-existent book should return false.");
    }
    @Test
    public void testGetActiveTransactions_NoActiveTransactions() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM transaction");
        }

        List<Transaction> transactions = transactionDAO.getActiveTransactions();

        assertNotNull(transactions, "The result should not be null.");
        assertTrue(transactions.isEmpty(), "There should be no active transactions.");
    }

}
