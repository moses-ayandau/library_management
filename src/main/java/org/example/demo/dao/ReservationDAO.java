package org.example.demo.dao;


import org.example.demo.dao.interfaces.IReservationDAO;
import org.example.demo.entity.Reservation;
import org.example.demo.entity.ReservationStatus;
import org.example.demo.db.conn.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationDAO implements IReservationDAO {


    public boolean addReservation(Reservation reservation) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO reservation (bookID, userID, reservedDate) VALUES (?, ?, ?)")) {

            stmt.setInt(1, reservation.getBookID());
            stmt.setInt(2, reservation.getPatronID());
            stmt.setDate(3, reservation.getReservedDate());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("SQL Exception in addReservation: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }


    public Reservation getReservationById(int id) throws SQLException {
        String sql = "SELECT * FROM reservation WHERE ID = ?";
        Reservation reservation = null;

        try (Connection connection =DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                reservation = mapResultSetToReservation(resultSet);
            }
        }
        return reservation;
    }

    public void updateReservationStatus(Reservation reservation) throws SQLException {
        String sql = "UPDATE reservation SET  reservedStatus = ? WHERE ID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, reservation.getStatus().toString());

            statement.executeUpdate();
        }
    }

    public void deleteReservation(int id) throws SQLException {
        String sql = "DELETE FROM reservation WHERE ID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }

    public List<Reservation> getAllReservations() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        String sql = "SELECT * FROM reservation";

        try (Connection connection = DatabaseConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                Reservation reservation = mapResultSetToReservation(resultSet);
                reservations.add(reservation);
            }
        }
        return reservations;
    }

    private Reservation mapResultSetToReservation(ResultSet resultSet) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setID(resultSet.getInt("ID"));
        reservation.setPatronID(resultSet.getInt("userID"));
        reservation.setBookID(resultSet.getInt("bookID"));
        reservation.setReservedDate(resultSet.getDate("reservedDate"));
        return reservation;
    }

    public Reservation getReservationByID(int id) throws SQLException {
        String sql = "SELECT * FROM reservation WHERE ID = ?";
        try (   Connection connection = DatabaseConnection.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Reservation reservation = new Reservation();
                reservation.setID(resultSet.getInt("ID"));
                reservation.setPatronID(resultSet.getInt("patronID"));
                reservation.setBookID(resultSet.getInt("bookID"));
                reservation.setReservedDate(resultSet.getDate("date"));
                reservation.setStatus(ReservationStatus.valueOf(resultSet.getString("status")));
                reservation.setDueDate(resultSet.getDate("dueDate"));
                return reservation;
            }
        }
        return null;
    }
}
