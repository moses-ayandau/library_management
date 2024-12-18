package org.example.demo.dao;


import org.example.demo.dao.interfaces.IReservationDAO;
import org.example.demo.entity.Reservation;
import org.example.demo.entity.ReservationStatus;
import org.example.demo.db.conn.DatabaseConnection;

import java.sql.*;
import java.util.LinkedList;
import java.util.Queue;

public class ReservationDAO implements IReservationDAO {


    /**
     * Adds a new reservation to the database.
     *
     * @param reservation the Reservation object containing the details of the reservation.
     * @return true if the reservation was successfully added, false otherwise.
     * @throws SQLException if a database error occurs.
     */
    public boolean addReservation(Reservation reservation) throws SQLException {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO reservation (bookID, userID, reservedDate) VALUES (?, ?, ?)")) {

            stmt.setInt(1, reservation.getBookID());
            stmt.setInt(2, reservation.getPatronID());
            stmt.setDate(3, reservation.getReservedDate());

            return stmt.executeUpdate() > 0;
        }
    }


    /**
     * Retrieves a reservation by its ID.
     *
     * @param id the ID of the reservation.
     * @return the Reservation object if found, or null if not found.
     * @throws SQLException if a database error occurs.
     */
    public Reservation getReservationById(int id) throws SQLException {
        String sql = "SELECT * FROM reservation WHERE ID = ?";
        Reservation reservation = null;

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                reservation = mapResultSetToReservation(resultSet);
            }
        }
        return reservation;
    }

    public void deleteReservation(int id) throws SQLException {
        String sql = "DELETE FROM reservation WHERE ID = ?";

        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            statement.executeUpdate();
        }
    }


    /**
     * Retrieves all reservations from the database.
     *
     * @return a queue of Reservation objects representing all reservations in the database.
     * @throws SQLException if a database error occurs.
     */
    public Queue<Reservation> getAllReservations() throws SQLException {
        Queue<Reservation> reservations = new LinkedList<>();
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

    /**
     * Maps a ResultSet row to a Reservation object.
     *
     * @param resultSet the ResultSet containing reservation data.
     * @return a Reservation object populated with the data from the ResultSet.
     * @throws SQLException if a database access error occurs.
     */
    private Reservation mapResultSetToReservation(ResultSet resultSet) throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setID(resultSet.getInt("ID"));
        reservation.setPatronID(resultSet.getInt("userID"));
        reservation.setBookID(resultSet.getInt("bookID"));
        reservation.setReservedDate(resultSet.getDate("reservedDate"));
        return reservation;
    }

    /**
     * Retrieves a reservation by its ID (alternative implementation).
     *
     * @param id the ID of the reservation.
     * @return the Reservation object if found, or null if not found.
     * @throws SQLException if a database error occurs.
     */
    public Reservation getReservationByID(int id) throws SQLException {
        String sql = "SELECT * FROM reservation WHERE ID = ?";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                Reservation reservation = new Reservation();
                reservation.setID(resultSet.getInt("ID"));
                reservation.setPatronID(resultSet.getInt("patronID"));
                reservation.setBookID(resultSet.getInt("bookID"));
                reservation.setReservedDate(resultSet.getDate("date"));
                reservation.setDueDate(resultSet.getDate("dueDate"));
                return reservation;
            }
        }
        return null;
    }

}
