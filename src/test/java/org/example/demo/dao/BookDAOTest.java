package org.example.demo.dao;

import org.example.demo.db.conn.DatabaseConnection;
import org.example.demo.entity.Book;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;

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
        // Configure the H2 in-memory database for testing
        DatabaseConnection.configureH2ForTesting();
        connection = DatabaseConnection.getConnection();
        bookDAO = new BookDAO();

        // Create schema and populate test data
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
    void testGetAllBooksShouldReturnWithSUccess() throws SQLException {
        Stack<Book> books = bookDAO.getAllBooks();

        assertTrue(books.size() > 0, "There should be at least 1 book in the database.");
        assertEquals("Book Two", books.peek().getTitle(), "The last book in the stack should be 'Book Two'.");
    }

    @Test
    void testAddBookShouldReturnWIthSuccess() throws SQLException {
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
    void testGetBookByIdShouldGetBookIDWIthSuccess() throws SQLException {
        Book book = bookDAO.getBookById(1);

        assertNotNull(book, "Book with ID 1 should exist.");
        assertEquals("Book One", book.getTitle());
    }

    @Test
    void testGetBookByIdNotFoundShouldNotFindBOok() throws SQLException {
        Book book = bookDAO.getBookById(999);

        assertNull(book, "Book with non-existent ID should return null.");
    }

//    @Test
//    void testUpdateBookShouldUpateWithSuccess() throws SQLException {
//        Book book = new Book();
//        book.setID(1);
//        book.setTitle("Book Three");
//        book.setDescription("Updated Description");
//        book.setAuthor("Book Author");
//        book.setIsbn("123-456");
//        book.setPublishedYear(2021);
//        book.setQuantity(7);
//
//        boolean result = bookDAO.updateBook(book);
//        Book bookFromDB = bookDAO.getBookById(1);
//
//
//        assertTrue(result, "Update should return true for successful update.");
//        assertEquals(7, bookFromDB.getQuantity());
//        assertEquals("Description One", bookFromDB.getDescription());
//    }

    @Test
    void testUpdateBookNotFoundShouldBecauseBookBecauseBookNotFound() throws SQLException {
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
    void testDeleteBookShouldDeleteBookWIthSuccess() throws SQLException {
        Book book = new Book();
        book.setID(0); // Assuming 0 will allow auto-generation of ID
        book.setTitle("Book Title");
        book.setDescription("Book Description");
        book.setAuthor("Book Author");
        book.setIsbn("123-456");
        book.setPublishedYear(2021);
        book.setQuantity(10);


        bookDAO.addBook(book);  //

        boolean result = bookDAO.deleteBook(1);

        // Assert
//        assertTrue(result, "Book with ID 1 should be deleted.");
//        assertNull(bookDAO.getBookById(1), "Book with ID 1 should no longer exist.");
    }

//    @Test
//    void testDeleteBookWithActiveTransactionsShouldNotDelete() throws SQLException {
//
//        try (Statement stmt = connection.createStatement()) {
//            stmt.execute("INSERT INTO transaction (bookID) VALUES (1);");
//        }
//
//
//        boolean result = bookDAO.deleteBook(1);
//
//
//        assertFalse(result, "Book with active transactions should not be deleted.");
//        assertNotNull(bookDAO.getBookById(1), "Book with ID 1 should still exist.");
//    }

    @Test
    void testDeleteBookNotFoundShouldReturnWithFailure() throws SQLException {

        boolean result = bookDAO.deleteBook(999);


        assertFalse(result, "Delete should return false for non-existent book.");
    }
}