package org.example.demo.dao;

import org.example.demo.entity.Book;
import org.example.demo.db.conn.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {

    public List<Book> getAllBooks() throws SQLException {
        List<Book> books = new ArrayList<>();
        String query = "SELECT * FROM Books";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Book book = new Book();
                book.setID(rs.getInt("BookID"));
                book.setTitle(rs.getString("Title"));
                book.setDescription(rs.getString("Description"));
                book.setAuthor(rs.getString("Author"));
                book.setIsbn(rs.getString("ISBN"));
                book.setPublishedDate(rs.getDate("PublishedDate"));
                book.setAvailable(rs.getBoolean("Available"));
                book.setQuantity(rs.getInt("Quantity"));
                books.add(book);
            }
        }
        return books;
    }

    public void addBook(Book book) throws SQLException {
        String query = "INSERT INTO Books (Title, Description, Author, ISBN, PublishedDate, Available, Quantity) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getDescription());
            stmt.setString(3, book.getAuthor());
            stmt.setString(4, book.getIsbn());
            stmt.setDate(5, new java.sql.Date(book.getPublishedDate().getTime()));
            stmt.setBoolean(6, book.isAvailable());
            stmt.setInt(7, book.getQuantity());
            stmt.executeUpdate();
        }
    }

    public Book getBookById(int bookId) throws SQLException {
        String query = "SELECT * FROM Books WHERE BookID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Book book = new Book();
                book.setID(rs.getInt("BookID"));
                book.setTitle(rs.getString("Title"));
                book.setDescription(rs.getString("Description"));
                book.setAuthor(rs.getString("Author"));
                book.setIsbn(rs.getString("ISBN"));
                book.setPublishedDate(rs.getDate("PublishedDate"));
                book.setAvailable(rs.getBoolean("Available"));
                book.setQuantity(rs.getInt("Quantity"));
                return book;
            }
        }
        return null;
    }
}
