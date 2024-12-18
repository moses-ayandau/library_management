package org.example.demo.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JournalDTOTest {

    private JournalDTO journalDTO;

    @BeforeEach
    public void setUp() {
        journalDTO = new JournalDTO();
    }

    @Test
    public void testSetAndGetID() {
        journalDTO.setID(1);
        assertEquals(1, journalDTO.getID(), "ID should be 1");
    }

    @Test
    public void testSetAndGetTitle() {
        String title = "Test Journal";
        journalDTO.setTitle(title);
        assertEquals(title, journalDTO.getTitle(), "Title should be 'Test Journal'");
    }

    @Test
    public void testSetAndGetPublishedYear() {
        int publishedYear = 2024;
        journalDTO.setPublishedYear(publishedYear);
        assertEquals(publishedYear, journalDTO.getPublishedYear(), "Published year should be 2024");
    }

    @Test
    public void testSetAndGetIssn() {
        String issn = "1234-5678";
        journalDTO.setIssn(issn);
        assertEquals(issn, journalDTO.getIssn(), "ISSN should be '1234-5678'");
    }

    @Test
    public void testSetAndGetPublisher() {
        String publisher = "Test Publisher";
        journalDTO.setPublisher(publisher);
        assertEquals(publisher, journalDTO.getPublisher(), "Publisher should be 'Test Publisher'");
    }
}
