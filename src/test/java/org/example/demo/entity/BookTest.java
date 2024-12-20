package org.example.demo.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    private Book book;

    @BeforeEach
    public void setUp() {
        book = new Book();
    }

    @ParameterizedTest
    @CsvSource({
            "A novel set in the 1920s",
            "An epic tale of adventure",
            "A comprehensive programming guide"
    })
    public void shouldSetAndRetrieveDescriptionCorrectly(String description) {
        book.setDescription(description);
        assertEquals(description, book.getDescription(), "The description should be set and retrieved correctly.");
    }

    @ParameterizedTest
    @CsvSource({
            "F. Scott Fitzgerald",
            "J.K. Rowling",
            "George R.R. Martin"
    })
    public void shouldSetAndRetrieveAuthorCorrectly(String author) {
        book.setAuthor(author);
        assertEquals(author, book.getAuthor(), "The author should be set and retrieved correctly.");
    }

    @ParameterizedTest
    @CsvSource({
            "9780743273565",
            "9780439554930",
            "9780553103540"
    })
    public void shouldSetAndRetrieveIsbnCorrectly(String isbn) {
        book.setIsbn(isbn);
        assertEquals(isbn, book.getIsbn(), "The ISBN should be set and retrieved correctly.");
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 5, 10, 100})
    public void shouldSetAndRetrieveQuantityCorrectly(int quantity) {
        book.setQuantity(quantity);
        assertEquals(quantity, book.getQuantity(), "The quantity should be set and retrieved correctly.");
    }

    @ParameterizedTest
    @CsvSource({
            "The Great Gatsby, A novel set in the 1920s, F. Scott Fitzgerald, 9780743273565, 1925",
            "Harry Potter, A magical adventure, J.K. Rowling, 9780439554930, 1997",
            "Game of Thrones, A fantasy epic, George R.R. Martin, 9780553103540, 1996"
    })
    public void shouldConstructBookWithGivenAttributes(String title, String description, String author, String isbn, int publishedYear) {
        Book book = new Book(title, description, author, isbn, publishedYear, true);

        assertEquals(title, book.getTitle(), "The title should be correctly set.");
        assertEquals(description, book.getDescription(), "The description should be correctly set.");
        assertEquals(author, book.getAuthor(), "The author should be correctly set.");
        assertEquals(isbn, book.getIsbn(), "The ISBN should be correctly set.");
        assertEquals(publishedYear, book.getPublishedYear(), "The published year should be correctly set.");
    }
}
