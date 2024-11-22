package org.example.demo.dao;

import org.example.demo.dao.interfaces.IBookDAO;
import org.example.demo.entity.Book;
import org.example.demo.db.conn.DatabaseConnection;

import java.sql.*;

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
        String query = "SELECT * FROM Book ";
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
                books.push(book);
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
