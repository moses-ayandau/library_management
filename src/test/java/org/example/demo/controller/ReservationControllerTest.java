package org.example.demo.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.TableView;
import org.example.demo.dao.ReservationDAO;
import org.example.demo.dao.interfaces.IReservationDAO;
import org.example.demo.entity.Reservation;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class ReservationControllerTest {

    private ReservationController controller;

    @Mock
    private IReservationDAO mockReservationDAO;

    @BeforeAll
    static void initToolkit() {
        // Avoid calling Platform.startup if it is already initialized
//        if (!Platform.isFxApplicationThread()) {
//            Platform.startup(() -> {});
//        }
    }

    @BeforeEach
    void setUp() throws InterruptedException {
        controller = new ReservationController();

        mockReservationDAO = mock(ReservationDAO.class);
        controller.reservationDAO = mockReservationDAO;

        CountDownLatch latch = new CountDownLatch(1);
        Platform.runLater(() -> {
            controller.reservationTable = new TableView<>();
            controller.reservationTable.setItems(FXCollections.observableArrayList());

            controller.patronIdField = new javafx.scene.control.TextField();
            controller.bookIdField = new javafx.scene.control.TextField();
            controller.dueDatePicker = new javafx.scene.control.DatePicker();

            latch.countDown();
        });
        latch.await();
    }

    @Test
    public void testHandleCreateReservation_InvalidInput() throws SQLException {
        controller.patronIdField.setText("");
        controller.bookIdField.setText("");

        controller.handleCreateReservation();

        verify(mockReservationDAO, never()).addReservation(any(Reservation.class));
    }

    @Test
    public void testHandleDeleteReservation_Successful() throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setID(1);

        controller.reservationTable.getItems().add(reservation);
        controller.reservationTable.getSelectionModel().select(reservation);

        doNothing().when(mockReservationDAO).deleteReservation(1);

        controller.handleDeleteReservation();

        verify(mockReservationDAO, times(1)).deleteReservation(1);
    }

    @Test
    public void testHandleDeleteReservation_NoSelection() throws SQLException {
        controller.reservationTable.getSelectionModel().clearSelection();

        controller.handleDeleteReservation();

        verify(mockReservationDAO, never()).deleteReservation(anyInt());
    }

    @Test
    public void testLoadReservations_Successful() throws SQLException {
        Reservation reservation1 = new Reservation();
        reservation1.setID(1);
        reservation1.setPatronID(1);
        reservation1.setBookID(101);
        reservation1.setReservedDate(Date.valueOf(LocalDate.now()));
        reservation1.setDueDate(Date.valueOf(LocalDate.now().plusDays(7)));

        Reservation reservation2 = new Reservation();
        reservation2.setID(2);
        reservation2.setPatronID(2);
        reservation2.setBookID(102);
        reservation2.setReservedDate(Date.valueOf(LocalDate.now()));
        reservation2.setDueDate(Date.valueOf(LocalDate.now().plusDays(7)));

        Queue<Reservation> reservationsQueue = new LinkedList<>();
        reservationsQueue.add(reservation1);
        reservationsQueue.add(reservation2);

        when(mockReservationDAO.getAllReservations()).thenReturn(reservationsQueue);

        controller.loadReservations();

        assertEquals(2, controller.reservationTable.getItems().size());
        verify(mockReservationDAO, times(1)).getAllReservations();
    }
}
