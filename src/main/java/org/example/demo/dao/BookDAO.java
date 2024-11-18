package org.example.demo.dao;

import org.example.demo.dao.interfaces.IBookDAO;
import org.example.demo.entity.Book;
import org.example.demo.db.conn.DatabaseConnection;
import org.example.demo.entity.Reservation;
import org.example.demo.entity.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO implements IBookDAO {

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
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
                books.add(book);
            }
        } catch (Exception e) {
            System.out.println("An error occurred " + e);
        }
        return books;
    }

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
                book.setPublishedYear(Integer.parseInt("PublishedYear"));
                book.setQuantity(Integer.parseInt("quantity"));

                return book;
            }
        }
        return null;
    }

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
        }
        catch (Exception e){
            System.out.println(e);
        }
    }


    public boolean updateBook(Book book) throws SQLException {
        String query = "UPDATE Book SET Available = ? WHERE ID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, book.isAvailable());  // Set the availability (true = available, false = booked/reserved)
            stmt.setInt(2, book.getID());  // Use the book's ID to find the record
            stmt.executeUpdate();
        }
        return false;
    }

    @Override
    public List<Book> getBooksByAvailability(boolean b) {
        return List.of();
    }
}
