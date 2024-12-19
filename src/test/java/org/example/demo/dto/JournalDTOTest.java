package org.example.demo.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class JournalDTOTest {

    private JournalDTO journalDTO;

    @BeforeEach
    public void setUp() {
        journalDTO = new JournalDTO();
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 100})
    public void testSetAndGetID(int id) {
        journalDTO.setID(id);
        assertEquals(id, journalDTO.getID(), "ID should be set and retrieved correctly.");
    }

    @ParameterizedTest
    @CsvSource({
            "Test Journal",
            "Advanced Research",
            "Machine Learning Trends"
    })
    public void testSetAndGetTitle(String title) {
        journalDTO.setTitle(title);
        assertEquals(title, journalDTO.getTitle(), "Title should be set and retrieved correctly.");
    }

    @ParameterizedTest
    @ValueSource(ints = {2020, 2023, 2024})
    public void testSetAndGetPublishedYear(int publishedYear) {
        journalDTO.setPublishedYear(publishedYear);
        assertEquals(publishedYear, journalDTO.getPublishedYear(), "Published year should be set and retrieved correctly.");
    }

    @ParameterizedTest
    @CsvSource({
            "1234-5678",
            "9876-5432",
            "1122-3344"
    })
    public void testSetAndGetIssn(String issn) {
        journalDTO.setIssn(issn);
        assertEquals(issn, journalDTO.getIssn(), "ISSN should be set and retrieved correctly.");
    }

    @ParameterizedTest
    @CsvSource({
            "Test Publisher",
            "Academic Press",
            "Research International"
    })
    public void testSetAndGetPublisher(String publisher) {
        journalDTO.setPublisher(publisher);
        assertEquals(publisher, journalDTO.getPublisher(), "Publisher should be set and retrieved correctly.");
    }
}
