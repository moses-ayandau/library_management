package org.example.demo.dao.interfaces;

import org.example.demo.entity.Reservation;

import java.sql.SQLException;
import java.util.Queue;

public interface IReservationDAO  {
    boolean addReservation(Reservation reservation) throws SQLException;
    Reservation getReservationById(int id) throws SQLException;
    void updateReservationStatus(Reservation reservation) throws SQLException;
    void deleteReservation(int id) throws SQLException;
    Queue<Reservation> getAllReservations() throws SQLException;
    Reservation getReservationByID(int id) throws SQLException;
}
