package org.example.demo.dao;

import org.example.demo.dao.interfaces.IBookDAO;
import org.example.demo.entity.Book;
import org.example.demo.db.conn.DatabaseConnection;
import org.example.demo.entity.Reservation;
import org.example.demo.entity.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class BookDAO implements IBookDAO {

    /**
     * Retrieves all books from the database.
     *
     * @return a stack of Book objects representing all books in the database.
     * @throws SQLException if a database access error occurs.
     */
    public Stack<Book> getAllBooks() throws SQLException {
        Stack<Book> books = new Stack<>();
        String query = "SELECT * FROM Book";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Book book = new Book();
                book.setID(rs.getInt("ID"));
                book.setTitle(rs.getString("Title"));
                book.setDescription(rs.getString("Description"));
                book.setAuthor(rs.getString("Author"));
                book.setIsbn(rs.getString("ISBN"));
                book.setPublishedYear(rs.getInt("PublishedYear"));
                book.setQuantity(rs.getInt("Quantity"));
                books.push(book); // Add the book to the stack
            }
        } catch (Exception e) {
            System.out.println("An error occurred " + e);
        }
        return books;
    }

    /**
     * Adds a new book to the database.
     *
     * @param book the Book object to add to the database.
     * @throws SQLException if a database access error occurs.
     */
    public void addBook(Book book) throws SQLException {
        String query = "INSERT INTO Book (Title, Description, Author, ISBN, PublishedYear, Quantity) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getDescription());
            stmt.setString(3, book.getAuthor());
            stmt.setString(4, book.getIsbn());
            stmt.setInt(5, book.getPublishedYear());
            stmt.setInt(6, book.getQuantity());
            stmt.executeUpdate();
        }
    }

    /**
     * Retrieves a book from the database by its ID.
     *
     * @param bookId the ID of the book to retrieve.
     * @return the Book object, or null if no book is found with the given ID.
     * @throws SQLException if a database access error occurs.
     */
    public Book getBookById(int bookId) throws SQLException {
        String query = "SELECT * FROM Book WHERE ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Book book = new Book();
                book.setID(rs.getInt("ID"));
                book.setTitle(rs.getString("Title"));
                book.setDescription(rs.getString("Description"));
                book.setAuthor(rs.getString("Author"));
                book.setIsbn(rs.getString("ISBN"));
                book.setPublishedYear(rs.getInt("PublishedYear"));
                book.setQuantity(rs.getInt("quantity"));

                return book;
            }
        }
        return null;
    }

    /**
     * Borrows a book by creating a new transaction record in the database.
     *
     * @param transaction the Transaction object representing the borrow details.
     * @return true if the transaction was successfully created, false otherwise.
     */
    public boolean borrowABook(Transaction transaction) {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Adds a new reservation to the database.
     *
     * @param reservation the Reservation object representing the reservation details.
     * @throws SQLException if a database access error occurs.
     */
    public void addReservation(Reservation reservation) throws SQLException {
        String sql = "INSERT INTO reservation (userID, bookID, reservedDate, reservedStatus, dueDate) VALUES (?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, reservation.getPatronID());
            statement.setInt(2, reservation.getBookID());
            statement.setDate(3, reservation.getReservedDate());
            statement.setString(4, reservation.getStatus().toString());
            statement.setDate(5, reservation.getDueDate());

            statement.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Updates the quantity of a book in the database.
     *
     * @param book the Book object containing updated details.
     * @return true if the update was successful, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public boolean updateBook(Book book) throws SQLException {
        String query = "UPDATE Book SET Quantity = ? WHERE ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, book.getQuantity());
            stmt.setInt(2, book.getID());

            int rowsUpdated = stmt.executeUpdate();

            return rowsUpdated > 0;
        }
    }

    /**
     * Deletes a book from the database if it has no active transactions.
     *
     * @param bookID the ID of the book to delete.
     * @return true if the book was successfully deleted, false if it has active transactions.
     * @throws SQLException if a database access error occurs.
     */
    public boolean deleteBook(int bookID) throws SQLException {
        if (hasActiveTransactions(bookID)) {
            return false;
        }
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM book WHERE ID = ?")) {
            stmt.setInt(1, bookID);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Checks if a book has active transactions.
     *
     * @param bookID the ID of the book to check.
     * @return true if there are active transactions, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    private boolean hasActiveTransactions(int bookID) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) FROM transaction WHERE bookID = ?")) {
            stmt.setInt(1, bookID);
            try (ResultSet rs = stmt.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }




}
