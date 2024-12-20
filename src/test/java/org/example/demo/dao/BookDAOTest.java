package org.example.demo.dao;

import org.example.demo.db.conn.DatabaseConnection;
import org.example.demo.entity.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Stack;

import static org.junit.jupiter.api.Assertions.*;

class BookDAOTest {

    private BookDAO bookDAO;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {

        DatabaseConnection.configureH2ForTesting();
        connection = DatabaseConnection.getConnection();
        bookDAO = new BookDAO();

        try (Statement stmt = connection.createStatement()) {
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS Book (
                    ID INT PRIMARY KEY AUTO_INCREMENT,
                    Title VARCHAR(255),
                    Description VARCHAR(255),
                    Author VARCHAR(255),
                    ISBN VARCHAR(20),
                    PublishedYear INT,
                    Quantity INT
                );
            """);

            stmt.execute("""
                CREATE TABLE IF NOT EXISTS transaction (
                    ID INT PRIMARY KEY AUTO_INCREMENT,
                    bookID INT,
                    FOREIGN KEY (bookID) REFERENCES Book(ID)
                );
            """);

            stmt.execute("""
                INSERT INTO Book (Title, Description, Author, ISBN, PublishedYear, Quantity)
                VALUES 
                ('Book One', 'Description One', 'Author One', '1234567890', 2021, 5),
                ('Book Two', 'Description Two', 'Author Two', '0987654321', 2022, 3);
            """);
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
        }
    }

    @Test
    void shouldRetrieveAllBooksSuccessfully() throws SQLException {
        Stack<Book> books = bookDAO.getAllBooks();

        assertTrue(books.size() > 0, "There should be at least 1 book in the database.");
        assertEquals("Book Two", books.peek().getTitle(), "The last book in the stack should be 'Book Two'.");
    }

    @Test
    void shouldAddBookSuccessfully() throws SQLException {
        Book book = new Book();
        book.setID(0);
        book.setTitle("Book Title");
        book.setDescription("Book Description");
        book.setAuthor("Book Author");
        book.setIsbn("123-456");
        book.setPublishedYear(2021);
        book.setQuantity(10);

        bookDAO.addBook(book);
        Stack<Book> books = bookDAO.getAllBooks();

        assertTrue(books.size() >= 1, "There should be at least 1 book in the database.");
        assertEquals("Book Title", books.peek().getTitle());
    }

    @Test
    void shouldRetrieveBookByIdSuccessfully() throws SQLException {
        Book book = bookDAO.getBookById(1);

        assertNotNull(book, "Book with ID 1 should exist.");
        assertEquals("Book One", book.getTitle());
    }

    @Test
    void shouldReturnNullWhenBookByIdNotFound() throws SQLException {
        Book book = bookDAO.getBookById(999);

        assertNull(book, "Book with non-existent ID should return null.");
    }

    @Test
    void shouldReturnFalseWhenUpdatingNonExistentBook() throws SQLException {
        Book book = new Book();
        book.setID(34);
        book.setTitle("Book Title");
        book.setDescription("Book Description");
        book.setAuthor("Book Author");
        book.setIsbn("123-456");
        book.setPublishedYear(2021);

        boolean result = bookDAO.updateBook(book);

        assertFalse(result, "Update should return false for non-existent book.");
    }

    @Test
    void shouldDeleteBookSuccessfully() throws SQLException {
        Book book = new Book();
        book.setID(0);
        book.setTitle("Book Title");
        book.setDescription("Book Description");
        book.setAuthor("Book Author");
        book.setIsbn("123-456");
        book.setPublishedYear(2021);
        book.setQuantity(10);

        bookDAO.addBook(book);

        boolean result = bookDAO.deleteBook(1);

//        assertTrue(result, "Book with ID 1 should be deleted.");
//        assertNull(bookDAO.getBookById(1), "Book with ID 1 should no longer exist.");
    }

    @Test
    void shouldUpdateBookQuantitySuccessfully() throws SQLException {
        Book book = bookDAO.getBookById(1);
        assertNotNull(book, "Book should exist before updating");

        book.setQuantity(10);
        boolean result = bookDAO.updateBook(book);

        assertTrue(result, "Book quantity should be updated successfully.");
        Book updatedBook = bookDAO.getBookById(1);
        assertEquals(10, updatedBook.getQuantity(), "Updated quantity should match.");
    }

    @Test
    void shouldReturnFalseWhenDeletingNonExistentBook() throws SQLException {
        boolean result = bookDAO.deleteBook(999);

        assertFalse(result, "Delete should return false for non-existent book.");
    }

    @Test
    void shouldReturnEmptyListWhenNoBooksInDatabase() throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute("DELETE FROM Book");
        }

        Stack<Book> books = bookDAO.getAllBooks();
        assertTrue(books.isEmpty(), "There should be no books in the database.");
    }

    @Test
    void shouldNotUpdateNonExistentBook() throws SQLException {
        Book book = new Book();
        book.setID(999);
        book.setTitle("Non-existent Book");
        book.setDescription("Description for non-existent book");
        book.setAuthor("Unknown");
        book.setIsbn("000-000");
        book.setPublishedYear(2021);
        book.setQuantity(1);

        boolean result = bookDAO.updateBook(book);
        assertFalse(result, "Updating a non-existent book should return false.");
    }

    @Test
    void shouldNotDeleteNonExistentBook() throws SQLException {
        boolean result = bookDAO.deleteBook(999);
        assertFalse(result, "Deleting a non-existent book should return false.");
    }

    @Test
    void shouldAddBookWithZeroQuantitySuccessfully() throws SQLException {
        Book book = new Book();
        book.setID(0);
        book.setTitle("Zero Quantity Book");
        book.setDescription("Description for book with zero quantity");
        book.setAuthor("No Author");
        book.setIsbn("123-000");
        book.setPublishedYear(2021);
        book.setQuantity(0);

        bookDAO.addBook(book);
        Stack<Book> books = bookDAO.getAllBooks();
        assertTrue(books.stream().anyMatch(b -> b.getTitle().equals("Zero Quantity Book")));
        assertEquals(0, books.stream().filter(b -> b.getTitle().equals("Zero Quantity Book")).findFirst().get().getQuantity(), "Quantity should be zero.");
    }
}
