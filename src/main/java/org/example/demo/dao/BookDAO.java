package org.example.demo.dao;
import org.example.demo.entity.Book;
import org.example.demo.javafx.DatabaseConnection;

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
                book.setBookID(rs.getInt("BookID"));
                book.setTitle(rs.getString("Title"));
                book.setAuthor(rs.getString("Author"));
                book.setDescription(rs.getString("Description"));
                book.setIsbn(rs.getString("ISBN"));
                book.setPublishedYear(rs.getInt("PublishedYear"));
                book.setAvailable(rs.getBoolean("Available"));
                books.add(book);
            }
        }
        return books;
    }

    public void addBook(Book book) throws SQLException {
        String query = "INSERT INTO Books (Title, Author,Description, ISBN, PublishedYear, Available) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, book.getTitle());
            stmt.setString(2, book.getAuthor());
            stmt.setString(3, book.getDescription());
            stmt.setString(4, book.getIsbn());
            stmt.setInt(5, book.getPublishedYear());
            stmt.setBoolean(6, book.isAvailable());
            stmt.executeUpdate();
        }
    }

    public Book getBookById(int bookId) throws Exception {
        String queryString = "SELECT * FROM Book WHERE BookID=?";

        try{
            Connection conn = DatabaseConnection.getConnection();
            Statement stmt = conn.createStatement();
            ResultSet resultSet = stmt.executeQuery(queryString);
            Book book = new Book();
            book.setID(resultSet.getInt("BookID"));
            book.setAuthor(resultSet.getString("Author"));
            book.setIsbn(resultSet.getString("ISBN"));
            book.setTitle(resultSet.getString("Title"));
            book.setPublishedYear(resultSet.getInt("PublishedYear"));
            book.setAvailable(resultSet.getBoolean("Available"));
            return book;

        }
        catch (Exception e){
            throw new Exception("Oops. Something happened");
        }
    }


}
