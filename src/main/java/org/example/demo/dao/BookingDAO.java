package org.example.demo.dao;

import org.example.demo.entity.Transaction;
import org.example.demo.javafx.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public void addBooking(Transaction booking) throws SQLException {
        String query = "INSERT INTO Bookings (BookID, PatronID, BorrowedDate, ReturnDate) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, booking.getBookID());
            stmt.setInt(2, booking.getMemberID());
            stmt.setDate(3, new java.sql.Date(booking.getBorrowedDate().getTime()));
            stmt.setDate(4, booking.getReturnDate() != null ? new java.sql.Date(booking.getReturnDate().getTime()) : null);
            stmt.executeUpdate();
        }
    }

    // 2. Method to retrieve a booking by its ID
    public Transaction getBookingById(int bookingID) throws SQLException {
        String query = "SELECT * FROM Bookings WHERE BookingID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookingID);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Transaction booking = new Transaction();
                booking.setBookingID(rs.getInt("BookingID"));
                booking.setBookID(rs.getInt("BookID"));
                booking.setMemberID(rs.getInt("PatronID"));
                booking.setBorrowedDate(rs.getDate("BorrowedDate"));
                booking.setReturnDate(rs.getDate("ReturnDate"));
                return booking;
            }
        }
        return null;
    }

    // 3. Method to update the return date of a booking
    public void updateReturnDate(int bookingID, Date returnDate) throws SQLException {
        String query = "UPDATE Bookings SET ReturnDate = ? WHERE BookingID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, new java.sql.Date(returnDate.getTime()));
            stmt.setInt(2, bookingID);
            stmt.executeUpdate();
        }
    }

    // 4. Method to delete a booking by its ID
    public void deleteBooking(int bookingID) throws SQLException {
        String query = "DELETE FROM Bookings WHERE BookingID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, bookingID);
            stmt.executeUpdate();
        }
    }

    // 5. Method to get all bookings for a specific patron
    public List<Transaction> getBookingsByPatron(int patronID) throws SQLException {
        List<Transaction> bookings = new ArrayList<>();
        String query = "SELECT * FROM Bookings WHERE PatronID = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, patronID);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Transaction booking = new Transaction();
                booking.setBookingID(rs.getInt("BookingID"));
                booking.setBookID(rs.getInt("BookID"));
                booking.setMemberID(rs.getInt("PatronID"));
                booking.setBorrowedDate(rs.getDate("BorrowedDate"));
                booking.setReturnDate(rs.getDate("ReturnDate"));
                bookings.add(booking);
            }
        }
        return bookings;
    }
}
