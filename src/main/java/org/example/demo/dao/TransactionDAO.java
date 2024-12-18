package org.example.demo.dao;




import org.example.demo.dao.interfaces.ITransactionDAO;
import org.example.demo.entity.Transaction;
import org.example.demo.db.conn.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TransactionDAO implements ITransactionDAO {
    public boolean createTransaction(Transaction transaction) throws SQLException {
        String sql = "INSERT INTO transaction (bookID, userID, borrowedDate, dueDate) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setInt(1, transaction.getBookID());
            pstmt.setInt(2, transaction.getPatronID());
            pstmt.setDate(3, new java.sql.Date(transaction.getBorrowedDate().getTime()));
            pstmt.setDate(4, new java.sql.Date(transaction.getDueDate().getTime()));

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    transaction.setID(generatedKeys.getInt(1));
                }
                return true;
            }
        }
        return false;
    }

    // Return a book
    public boolean returnBook(int transactionId, Date returnDate) throws SQLException {
        String sql = "UPDATE transaction SET returnedDate = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, new java.sql.Date(returnDate.getTime()));
            pstmt.setInt(2, transactionId);

            return pstmt.executeUpdate() > 0;
        }
    }

    public List<Transaction> getActiveTransactions() throws SQLException {
        List<Transaction> transactions = new LinkedList<>();
        String sql = "SELECT * FROM transaction WHERE returnedDate IS NULL";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setID(rs.getInt("ID"));
                transaction.setBookID(rs.getInt("bookID"));
                transaction.setPatronID(rs.getInt("userID"));
                transaction.setBorrowedDate(rs.getDate("borrowedDate"));
                transaction.setDueDate(rs.getDate("dueDate"));

                transactions.add(transaction);
            }
        }
        return transactions;
    }



    // Get transaction by ID
    public Transaction getTransactionById(int id) {
        String sql = "SELECT * FROM transaction WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setID(rs.getInt("id"));
                transaction.setBookID(rs.getInt("bookID"));
                transaction.setPatronID(rs.getInt("userID"));
                transaction.setBorrowedDate(rs.getDate("borrowedDate"));
                transaction.setDueDate(rs.getDate("dueDate"));
                transaction.setReturnedDate(rs.getDate("returnedDate"));
                return transaction;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}