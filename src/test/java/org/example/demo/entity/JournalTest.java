package org.example.demo.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JournalTest {

    private Journal journal;

    @BeforeEach
    public void setUp() {
        journal = new Journal();
    }

    @Test
    public void testGetAndSetIssn() {
        String expectedIssn = "1234-5678";

        journal.setIssn(expectedIssn);
        String actualIssn = journal.getIssn();

        assertEquals(expectedIssn, actualIssn, "The ISSN should be set and retrieved correctly.");
    }

    @Test
    public void testGetAndSetPublisher() {
        String expectedPublisher = "Springer";

        journal.setPublisher(expectedPublisher);
        String actualPublisher = journal.getPublisher();

        assertEquals(expectedPublisher, actualPublisher, "The publisher should be set and retrieved correctly.");
    }

    @Test
    public void testConstructorAndSetters() {
        String expectedIssn = "9876-5432";
        String expectedPublisher = "Elsevier";

        journal.setIssn(expectedIssn);
        journal.setPublisher(expectedPublisher);

        // Then
        assertEquals(expectedIssn, journal.getIssn(), "The ISSN should be correctly set.");
        assertEquals(expectedPublisher, journal.getPublisher(), "The publisher should be correctly set.");
    }

}
