package org.example.demo.dao;

import org.example.demo.javafx.DatabaseConnection;

import java.sql.*;

public class BookingDAO {
    public void bookBook(int bookID, int memberID) throws SQLException {
        String updateBookAvailability = "UPDATE Books SET Available = FALSE WHERE BookID = ?";
        String createBooking = "INSERT INTO Bookings (BookID, MemberID, BorrowedDate) VALUES (?, ?, CURDATE())";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmt1 = conn.prepareStatement(updateBookAvailability);
                 PreparedStatement stmt2 = conn.prepareStatement(createBooking)) {

                stmt1.setInt(1, bookID);
                stmt1.executeUpdate();

                stmt2.setInt(1, bookID);
                stmt2.setInt(2, memberID);
                stmt2.executeUpdate();

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }
}
