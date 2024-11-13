package org.example.demo.dao;

import org.example.demo.entity.Member;
import org.example.demo.javafx.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PatronDAO {

    public void addPatron(Member patron) throws SQLException {
        String query = "INSERT INTO Patrons (Name, Email, Phone, Address, MembershipDate) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, patron.getName());
            stmt.setString(2, patron.getEmail());
            stmt.setString(3, patron.getPhone());
            stmt.setString(4, patron.getAddress());
            stmt.executeUpdate();
        }
    }

    public Member getPatronById(int patronID) throws SQLException {
        String query = "SELECT * FROM Patrons WHERE PatronID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, patronID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Member patron = new Member();
                patron.setPatronID(rs.getInt("PatronID"));
                patron.setName(rs.getString("Name"));
                patron.setEmail(rs.getString("Email"));
                patron.setPhone(rs.getString("Phone"));
                patron.setAddress(rs.getString("Address"));

                return patron;
            }
        }
        return null;
    }

    public List<Member> getAllPatrons() throws SQLException {
        List<Member> patrons = new ArrayList<>();
        String query = "SELECT * FROM Patrons";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Member patron = new Member();
                patron.setPatronID(rs.getInt("PatronID"));
                patron.setName(rs.getString("Name"));
                patron.setEmail(rs.getString("Email"));
                patron.setPhone(rs.getString("Phone"));
                patron.setAddress(rs.getString("Address"));
                patrons.add(patron);
            }
        }
        return patrons;
    }

    public void updatePatron(Member patron) throws SQLException {
        String query = "UPDATE Patrons SET Name = ?, Email = ?, Phone = ?, Address = ? WHERE PatronID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, patron.getName());
            stmt.setString(2, patron.getEmail());
            stmt.setString(3, patron.getPhone());
            stmt.setString(4, patron.getAddress());
            stmt.setInt(5, patron.getPatronID());
            stmt.executeUpdate();
        }
    }

    public void deletePatron(int patronID) throws SQLException {
        String query = "DELETE FROM Patrons WHERE PatronID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, patronID);
            stmt.executeUpdate();
        }
    }
}
