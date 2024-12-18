package org.example.demo.dao;

import org.example.demo.db.conn.DatabaseConnection;
import org.example.demo.entity.Journal;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JournalDAOTest {

    private Connection connection;
    private JournalDAO journalDAO;


    @BeforeEach
    public void setUp() throws Exception {
        // Mock static method
        try (MockedStatic<DatabaseConnection> mockedDbConnection = mockStatic(DatabaseConnection.class)) {
            connection = DriverManager.getConnection("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;", "sa", "");
            Statement stmt = connection.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS Journal (" +
                    "ID INT AUTO_INCREMENT PRIMARY KEY, " +
                    "title VARCHAR(255), " +
                    "publishedYear INT, " +
                    "issn VARCHAR(20), " +
                    "publisher VARCHAR(255))");
            stmt.close();

            mockedDbConnection.when(DatabaseConnection::getConnection).thenReturn(connection);

            journalDAO = new JournalDAO();
        }
    }

    @AfterEach
    public void tearDown() throws Exception {
        if (connection != null) {
            Statement stmt = connection.createStatement();
            stmt.execute("DROP TABLE Journal"); // Clean up after each test
            stmt.close();
            connection.close();
        }
    }

//    @Test
//    public void testAddJournal_Success() throws Exception {
//        // Arrange
//        Journal journal = new Journal();
//        journal.setTitle("Journal Title");
//        journal.setPublishedYear(2024);
//        journal.setIssn("1234-5678");
//        journal.setPublisher("Publisher Name");
//
//        // Act
//        journalDAO.addJournal(journal);
//
//        // Assert
//        try (PreparedStatement stmt = connection.prepareStatement("SELECT title, publishedYear, issn, publisher FROM Journal WHERE title = ?")) {
//            stmt.setString(1, "Journal Title");
//            ResultSet rs = stmt.executeQuery();
//
//            assertTrue(rs.next()); // Ensure a record is returned
//            assertEquals("Journal Title", rs.getString("title"));
//            assertEquals(2024, rs.getInt("publishedYear"));
//            assertEquals("1234-5678", rs.getString("issn"));
//            assertEquals("Publisher Name", rs.getString("publisher"));
//        }
//    }


    @Test
    public void testGetJournalById_Success() throws Exception {
        Journal journal = new Journal();
        journal.setTitle("Journal Title");
        journal.setPublishedYear(2024);
        journal.setIssn("1234-5678");
        journal.setPublisher("Publisher Name");

        journalDAO.addJournal(journal);
        int generatedId = journal.getID(); // Get the auto-generated ID

        Journal retrievedJournal = journalDAO.getJournalById(generatedId);

        assertNotNull(retrievedJournal);
        assertEquals("Journal Title", retrievedJournal.getTitle());
        assertEquals(generatedId, retrievedJournal.getID());
    }

    @Test
    public void testGetJournalById_NotFound() throws Exception {
        Journal journal = journalDAO.getJournalById(999); // Non-existing ID
        assertNull(journal); // Should return null
    }

    @Test
    public void testDeleteJournal_Success() throws Exception {
        Journal journal = new Journal();
        journal.setTitle("Journal Title");
        journal.setPublishedYear(2024);
        journal.setIssn("1234-5678");
        journal.setPublisher("Publisher Name");

        journalDAO.addJournal(journal);
        int generatedId = journal.getID(); // Get the auto-generated ID

        boolean isDeleted = journalDAO.deleteJournal(generatedId);

        assertTrue(isDeleted); // Deletion should be successful

        PreparedStatement stmt = connection.prepareStatement("SELECT COUNT(*) FROM Journal WHERE ID = ?");
        stmt.setInt(1, generatedId);
        ResultSet rs = stmt.executeQuery();
        rs.next();
        int count = rs.getInt(1);
        assertEquals(0, count); // No records should exist after deletion
    }



    @Test
    public void testGetAllJournals_Success() throws Exception {
        Journal journal1 = new Journal();
        journal1.setTitle("Journal 1");
        journal1.setPublishedYear(2024);
        journal1.setIssn("1234-5678");
        journal1.setPublisher("Publisher 1");

        Journal journal2 = new Journal();
        journal2.setTitle("Journal 2");
        journal2.setPublishedYear(2023);
        journal2.setIssn("8765-4321");
        journal2.setPublisher("Publisher 2");

        journalDAO.addJournal(journal1);
        journalDAO.addJournal(journal2);

        List<Journal> journals = journalDAO.getAllJournals();

        assertNotNull(journals); // Ensure the result is not null

        // Validate the content of the first journal
        Journal retrievedJournal1 = journals.stream()
                .filter(j -> "Journal 1".equals(j.getTitle()))
                .findFirst()
                .orElse(null);
        assertNotNull(retrievedJournal1);
        assertEquals("Journal 1", retrievedJournal1.getTitle());
        assertEquals(2024, retrievedJournal1.getPublishedYear());
        assertEquals("1234-5678", retrievedJournal1.getIssn());
        assertEquals("Publisher 1", retrievedJournal1.getPublisher());

        // Validate the content of the second journal
        Journal retrievedJournal2 = journals.stream()
                .filter(j -> "Journal 2".equals(j.getTitle()))
                .findFirst()
                .orElse(null);
        assertNotNull(retrievedJournal2);
        assertEquals("Journal 2", retrievedJournal2.getTitle());
        assertEquals(2023, retrievedJournal2.getPublishedYear());
        assertEquals("8765-4321", retrievedJournal2.getIssn());
        assertEquals("Publisher 2", retrievedJournal2.getPublisher());
    }
//
//    @Test
//    public void testGetAllJournals_EmptyResultSet() throws Exception {
//        // Ensure the database is empty before running the test
//        PreparedStatement stmt = connection.prepareStatement("DELETE FROM Journal");
//        stmt.executeUpdate();
//
//        List<Journal> journals = journalDAO.getAllJournals();
//        assertNotNull(journals);
//        assertTrue(journals.isEmpty());
//    }

}
