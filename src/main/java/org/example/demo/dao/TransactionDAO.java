package org.example.demo.dao;



import org.example.demo.entity.Transaction;
import org.example.demo.db.conn.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAO {

    public boolean createTransaction(Transaction transaction) {
        String sql = "INSERT INTO transaction (book_id, patron_id, borrowed_date, due_date) VALUES (?, ?, ?, ?)";

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Return a book
    public boolean returnBook(int transactionId, Date returnDate) {
        String sql = "UPDATE transaction SET returned_date = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setDate(1, new java.sql.Date(returnDate.getTime()));
            pstmt.setInt(2, transactionId);

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all active transactions (not returned)
    public List<Transaction> getActiveTransactions() {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM transaction WHERE returned_date IS NULL";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setID(rs.getInt("id"));
                transaction.setBookID(rs.getInt("book_id"));
                transaction.setPatronID(rs.getInt("patron_id"));
                transaction.setBorrowedDate(rs.getDate("borrowed_date"));
                transaction.setDueDate(rs.getDate("due_date"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
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
                transaction.setBookID(rs.getInt("book_id"));
                transaction.setPatronID(rs.getInt("patron_id"));
                transaction.setBorrowedDate(rs.getDate("borrowed_date"));
                transaction.setDueDate(rs.getDate("due_date"));
                transaction.setReturnedDate(rs.getDate("returned_date"));
                return transaction;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}