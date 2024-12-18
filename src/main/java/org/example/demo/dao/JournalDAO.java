package org.example.demo.dao;

import org.example.demo.dao.interfaces.IJournalDAO;
import org.example.demo.db.conn.DatabaseConnection;
import org.example.demo.entity.Journal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JournalDAO implements IJournalDAO {

    /**
     * Adds a new journal to the database.
     *
     * @param journal the Journal object to be added.
     * @throws SQLException if a database access error occurs.
     */
    public void addJournal(Journal journal) throws SQLException {
        String query = "INSERT INTO journal (title, publishedYear, issn, publisher) VALUES (?, ?, ?, ?)";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, journal.getTitle());
            stmt.setInt(2, journal.getPublishedYear());
            stmt.setString(3, journal.getIssn());
            stmt.setString(4, journal.getPublisher());
            stmt.executeUpdate();

            // Fetch and set auto-generated ID
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                journal.setID(generatedKeys.getInt(1));
            }
        }
    }

    /**
     * Retrieves a journal from the database by its ID.
     *
     * @param id the ID of the journal to retrieve.
     * @return the Journal object if found, or null if no journal is found with the given ID.
     * @throws SQLException if a database access error occurs.
     */
    public Journal getJournalById(int id) throws SQLException {
        String query = "SELECT * FROM journal WHERE ID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Journal journal = new Journal();
                journal.setID(rs.getInt("ID"));
                journal.setTitle(rs.getString("title"));
                journal.setPublishedYear(rs.getInt("publishedYear"));
                journal.setIssn(rs.getString("issn"));
                journal.setPublisher(rs.getString("publisher"));
                return journal;
            }
        }
        return null;
    }

    /**
     * Deletes a journal from the database by its ID.
     *
     * @param id the ID of the journal to delete.
     * @return true if a journal was deleted, false otherwise.
     * @throws SQLException if a database access error occurs.
     */
    public boolean deleteJournal(int id) throws SQLException {
        String query = "DELETE FROM journal WHERE ID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        }
    }

    /**
     * Retrieves all journals from the database.
     *
     * @return a list of Journal objects representing all journals in the database.
     * @throws SQLException if a database access error occurs.
     */
    public List<Journal> getAllJournals() throws SQLException {
        List<Journal> journals = new ArrayList<>();
        String query = "SELECT * FROM journal";
        try (Connection connection = DatabaseConnection.getConnection();
             Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                Journal journal = new Journal();
                journal.setID(rs.getInt("ID"));
                journal.setTitle(rs.getString("title"));
                journal.setPublishedYear(rs.getInt("publishedYear"));
                journal.setIssn(rs.getString("issn"));
                journal.setPublisher(rs.getString("publisher"));
                journals.add(journal);
            }
        }
        return journals;
    }
}
