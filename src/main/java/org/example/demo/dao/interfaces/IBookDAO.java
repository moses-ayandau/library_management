package org.example.demo.dao.interfaces;

import org.example.demo.entity.Book;

import java.sql.SQLException;
import java.util.List;

public interface IBookDAO {
    List<Book> getAllBooks() throws SQLException;
    void addBook(Book book) throws SQLException;
    Book getBookById(int bookId) throws SQLException;
    boolean updateBook(Book book) throws SQLException;
    boolean deleteBook(int bookId) throws SQLException;

}
