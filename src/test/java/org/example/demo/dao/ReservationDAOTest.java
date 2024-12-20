package org.example.demo.dao;

import org.example.demo.dao.ReservationDAO;
import org.example.demo.db.conn.DatabaseConnection;
import org.example.demo.entity.Reservation;
import org.example.demo.entity.ReservationStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.Queue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReservationDAOTest {

    private ReservationDAO reservationDAO;

    @Mock
    private Connection mockConnection;

    @Mock
    private PreparedStatement mockPreparedStatement;

    @Mock
    private ResultSet mockResultSet;

    @Mock
    private Statement mockStatement;

    @BeforeEach
    public void setUp() throws SQLException {
        reservationDAO = new ReservationDAO();
    }

    @Test
    public void shouldAddReservationSuccessfully() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedStaticDB = mockStatic(DatabaseConnection.class)) {

            Reservation reservation = new Reservation();
            reservation.setBookID(1);
            reservation.setPatronID(100);
            reservation.setReservedDate(Date.valueOf("2024-01-01"));

            mockedStaticDB.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(1);

            boolean result = reservationDAO.addReservation(reservation);

            assertTrue(result);
            verify(mockPreparedStatement).setInt(1, reservation.getBookID());
            verify(mockPreparedStatement).setInt(2, reservation.getPatronID());
            verify(mockPreparedStatement).setDate(3, reservation.getReservedDate());
        }
    }

    @Test
    public void shouldFailToAddReservation() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedStaticDB = mockStatic(DatabaseConnection.class)) {
            Reservation reservation = new Reservation();
            reservation.setBookID(1);
            reservation.setPatronID(100);
            reservation.setReservedDate(Date.valueOf("2024-01-01"));

            mockedStaticDB.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeUpdate()).thenReturn(0);

            boolean result = reservationDAO.addReservation(reservation);

            assertFalse(result);
        }
    }


    @Test
    public void shouldGetReservationByIdWhenFound() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedStaticDB = mockStatic(DatabaseConnection.class)) {

            int reservationId = 1;

            mockedStaticDB.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("ID")).thenReturn(reservationId);
            when(mockResultSet.getInt("userID")).thenReturn(100);
            when(mockResultSet.getInt("bookID")).thenReturn(200);
            when(mockResultSet.getDate("reservedDate")).thenReturn(Date.valueOf("2024-01-01"));

            Reservation result = reservationDAO.getReservationById(reservationId);

            assertNotNull(result);
            assertEquals(reservationId, result.getID());
            assertEquals(100, result.getPatronID());
            assertEquals(200, result.getBookID());
        }
    }

    @Test
    public void shouldReturnNullWhenReservationByIdNotFound() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedStaticDB = mockStatic(DatabaseConnection.class)) {

            int reservationId = 1;

            mockedStaticDB.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

            when(mockResultSet.next()).thenReturn(false);

            Reservation result = reservationDAO.getReservationById(reservationId);

            assertNull(result);
        }
    }


    @Test
    public void shouldDeleteReservationSuccessfully() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedStaticDB = mockStatic(DatabaseConnection.class)) {

            int reservationId = 1;

            mockedStaticDB.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);


            reservationDAO.deleteReservation(reservationId);


            verify(mockPreparedStatement).setInt(1, reservationId);
            verify(mockPreparedStatement).executeUpdate();
        }
    }

    @Test
    public void shouldRetrieveAllReservationsSuccessfully() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedStaticDB = mockStatic(DatabaseConnection.class)) {

            mockedStaticDB.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

            when(mockResultSet.next())
                    .thenReturn(true)
                    .thenReturn(true)
                    .thenReturn(false);

            when(mockResultSet.getInt("ID")).thenReturn(1).thenReturn(2);
            when(mockResultSet.getInt("userID")).thenReturn(100).thenReturn(200);
            when(mockResultSet.getInt("bookID")).thenReturn(10).thenReturn(20);
            when(mockResultSet.getDate("reservedDate"))
                    .thenReturn(Date.valueOf("2024-01-01"))
                    .thenReturn(Date.valueOf("2024-02-01"));

            Queue<Reservation> reservations = reservationDAO.getAllReservations();

            assertNotNull(reservations);
            assertEquals(2, reservations.size());
        }
    }

    @Test
    public void shouldGetReservationByIdUsingAlternativeMethod() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedStaticDB = mockStatic(DatabaseConnection.class)) {

            int reservationId = 1;

            mockedStaticDB.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);
            when(mockPreparedStatement.executeQuery()).thenReturn(mockResultSet);

            when(mockResultSet.next()).thenReturn(true);
            when(mockResultSet.getInt("ID")).thenReturn(reservationId);
            when(mockResultSet.getInt("patronID")).thenReturn(100);
            when(mockResultSet.getInt("bookID")).thenReturn(200);
            when(mockResultSet.getDate("date")).thenReturn(Date.valueOf("2024-01-01"));
            when(mockResultSet.getDate("dueDate")).thenReturn(Date.valueOf("2024-02-01"));

            Reservation result = reservationDAO.getReservationByID(reservationId);

            assertNotNull(result);
            assertEquals(reservationId, result.getID());
            assertEquals(100, result.getPatronID());
            assertEquals(200, result.getBookID());
        }
    }

    @Test
    public void shouldRejectDuplicateReservation() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedStaticDB = mockStatic(DatabaseConnection.class)) {

            Reservation reservation = new Reservation();
            reservation.setBookID(1);
            reservation.setPatronID(100);
            reservation.setReservedDate(Date.valueOf("2024-01-01"));

            mockedStaticDB.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.prepareStatement(anyString())).thenReturn(mockPreparedStatement);

            when(mockPreparedStatement.executeUpdate()).thenReturn(0);

            boolean result = reservationDAO.addReservation(reservation);

            assertFalse(result, "The system should reject duplicate reservations.");
        }
    }

    @Test
    public void shouldHandleEmptyDatabaseWhenRetrievingAllReservations() throws SQLException {
        try (MockedStatic<DatabaseConnection> mockedStaticDB = mockStatic(DatabaseConnection.class)) {

            mockedStaticDB.when(DatabaseConnection::getConnection).thenReturn(mockConnection);
            when(mockConnection.createStatement()).thenReturn(mockStatement);
            when(mockStatement.executeQuery(anyString())).thenReturn(mockResultSet);

            when(mockResultSet.next()).thenReturn(false);

            Queue<Reservation> reservations = reservationDAO.getAllReservations();

            assertNotNull(reservations, "Result should not be null");
            assertTrue(reservations.isEmpty(), "The reservations queue should be empty.");
        }
    }

}