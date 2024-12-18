package org.example.demo.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LibraryResourceTest {

    private LibraryResource libraryResource;

    @BeforeEach
    public void setUp() {
        libraryResource = new LibraryResource();
    }

    @Test
    public void testGetAndSetID() {
        int expectedID = 1;

        libraryResource.setID(expectedID);
        int actualID = libraryResource.getID();

        assertEquals(expectedID, actualID, "The ID should be set and retrieved correctly.");
    }

    @Test
    public void testGetAndSetTitle() {
        String expectedTitle = "The Great Gatsby";

        libraryResource.setTitle(expectedTitle);
        String actualTitle = libraryResource.getTitle();

        assertEquals(expectedTitle, actualTitle, "The title should be set and retrieved correctly.");
    }

    @Test
    public void testGetAndSetPublishedYear() {
        int expectedYear = 1925;

        libraryResource.setPublishedYear(expectedYear);
        int actualYear = libraryResource.getPublishedYear();

        assertEquals(expectedYear, actualYear, "The published year should be set and retrieved correctly.");
    }
}
