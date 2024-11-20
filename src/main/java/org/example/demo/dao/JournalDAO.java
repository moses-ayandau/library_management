package org.example.demo.dao;

import org.example.demo.db.conn.DatabaseConnection;
import org.example.demo.entity.Journal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JournalDAO {


    // Create
    public void addJournal(Journal journal) throws SQLException {
        String query = "INSERT INTO journal (ID, title, publishedYear, issn, publisher) VALUES (?, ?, ?, ?, ?)";
        try (    Connection connection = DatabaseConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
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
        String query = "SELECT * FROM journal WHERE ID = ?";
        try ( Connection connection = DatabaseConnection.getConnection();
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


    // Delete
    public void deleteJournal(int id) throws SQLException {
        String query = "DELETE FROM journals WHERE ID = ?";
        try ( Connection connection = DatabaseConnection.getConnection();
                PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Get all journals
    public List<Journal> getAllJournals() throws SQLException {
        List<Journal> journals = new ArrayList<>();
        String query = "SELECT * FROM journal";
        try ( Connection connection = DatabaseConnection.getConnection();
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
