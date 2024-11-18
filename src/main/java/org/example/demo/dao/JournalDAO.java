package org.example.demo.dao;

import org.example.demo.entity.Journal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JournalDAO {

    private Connection connection;

    public JournalDAO(Connection connection) {
        this.connection = connection;
    }

    // Create
    public void addJournal(Journal journal) throws SQLException {
        String query = "INSERT INTO journals (ID, title, publishedYear, issn, publisher) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, journal.getID());
            stmt.setString(2, journal.getTitle());
            stmt.setInt(3, journal.getPublishedYear());
            stmt.setString(4, journal.getIssn());
            stmt.setString(5, journal.getPublisher());
            stmt.executeUpdate();
        }
    }

    // Read
    public Journal getJournalById(int id) throws SQLException {
        String query = "SELECT * FROM journals WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
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

    // Update
    public void updateJournal(Journal journal) throws SQLException {
        String query = "UPDATE journals SET title = ?, publishedYear = ?, issn = ?, publisher = ? WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, journal.getTitle());
            stmt.setInt(2, journal.getPublishedYear());
            stmt.setString(3, journal.getIssn());
            stmt.setString(4, journal.getPublisher());
            stmt.setInt(5, journal.getID());
            stmt.executeUpdate();
        }
    }

    // Delete
    public void deleteJournal(int id) throws SQLException {
        String query = "DELETE FROM journals WHERE ID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Get all journals
    public List<Journal> getAllJournals() throws SQLException {
        List<Journal> journals = new ArrayList<>();
        String query = "SELECT * FROM journals";
        try (Statement stmt = connection.createStatement()) {
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
