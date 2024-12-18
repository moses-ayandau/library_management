package org.example.demo.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    private Book book;

    @BeforeEach
    public void setUp() {
        book = new Book();
    }

    @Test
    public void testGetAndSetDescription() {
        String expectedDescription = "A novel set in the 1920s";

        book.setDescription(expectedDescription);
        String actualDescription = book.getDescription();

        assertEquals(expectedDescription, actualDescription, "The description should be set and retrieved correctly.");
    }

    @Test
    public void testGetAndSetAuthor() {
        String expectedAuthor = "F. Scott Fitzgerald";

        book.setAuthor(expectedAuthor);
        String actualAuthor = book.getAuthor();

        assertEquals(expectedAuthor, actualAuthor, "The author should be set and retrieved correctly.");
    }

    @Test
    public void testGetAndSetIsbn() {
        String expectedIsbn = "9780743273565";

        book.setIsbn(expectedIsbn);
        String actualIsbn = book.getIsbn();

        assertEquals(expectedIsbn, actualIsbn, "The ISBN should be set and retrieved correctly.");
    }

    @Test
    public void testGetAndSetQuantity() {
        int expectedQuantity = 5;

        book.setQuantity(expectedQuantity);
        int actualQuantity = book.getQuantity();

        assertEquals(expectedQuantity, actualQuantity, "The quantity should be set and retrieved correctly.");
    }

    @Test
    public void testConstructorWithTitleDescriptionAuthorIsbnPublishedYear() {
        String expectedTitle = "The Great Gatsby";
        String expectedDescription = "A novel set in the 1920s";
        String expectedAuthor = "F. Scott Fitzgerald";
        String expectedIsbn = "9780743273565";
        int expectedPublishedYear = 1925;

        Book book = new Book(expectedTitle, expectedDescription, expectedAuthor, expectedIsbn, expectedPublishedYear, true);

        assertEquals(expectedTitle, book.getTitle(), "The title should be correctly set.");
        assertEquals(expectedDescription, book.getDescription(), "The description should be correctly set.");
        assertEquals(expectedAuthor, book.getAuthor(), "The author should be correctly set.");
        assertEquals(expectedIsbn, book.getIsbn(), "The ISBN should be correctly set.");
        assertEquals(expectedPublishedYear, book.getPublishedYear(), "The published year should be correctly set.");
    }

    @Test
    public void testConstructorWithBookId() {
        int bookId = 1;

        Book book = new Book(bookId);

        assertEquals(bookId, book.getID(), "The book ID should be correctly set.");
    }

    @Test
    public void testToString() {
        String expectedTitle = "The Great Gatsby";
        String expectedAuthor = "F. Scott Fitzgerald";
        String expectedIsbn = "9780743273565";
        int expectedPublishedYear = 1925;
        book.setID(1);
        book.setTitle(expectedTitle);
        book.setAuthor(expectedAuthor);
        book.setIsbn(expectedIsbn);
        book.setPublishedYear(expectedPublishedYear);

        String result = book.toString();

        String expectedToString = "Book ID: 1\n" +
                "Title: The Great Gatsby\n" +
                "Author: F. Scott Fitzgerald\n" +
                "ISBN: 9780743273565\n" +
                "Published Date: 1925";

        assertEquals(expectedToString, result, "The toString method should return the correct string representation.");
    }
}
