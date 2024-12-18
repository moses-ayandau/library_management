package org.example.demo.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ComboBox;
import org.example.demo.dao.ReservationDAO;
import org.example.demo.dao.interfaces.IReservationDAO;
import org.example.demo.entity.Reservation;
import org.example.demo.entity.ReservationStatus;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Queue;

public class ReservationController {

    // UI Components
    public TableView<Reservation> reservationTable;
    public TextField patronIdField;
    public TextField bookIdField;
    public DatePicker dueDatePicker;
    public ComboBox<ReservationStatus> statusComboBox;

    public IReservationDAO reservationDAO;

    // Show alert method for displaying messages to the user
    public void showAlert(String title, String content, AlertType type) {
        Platform.runLater(() -> {
            Alert alert = new Alert(type);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    // Handle creating a reservation
    public void handleCreateReservation() {
        try {
            Reservation reservation = new Reservation();
            reservation.setPatronID(Integer.parseInt(patronIdField.getText()));
            reservation.setBookID(Integer.parseInt(bookIdField.getText()));
            reservation.setReservedDate(Date.valueOf(LocalDate.now()));
            reservation.setStatus(ReservationStatus.PENDING);
            reservation.setDueDate(Date.valueOf(dueDatePicker.getValue()));

            reservationDAO.addReservation(reservation);

            showAlert("Success", "Reservation created successfully!", AlertType.INFORMATION);
            loadReservations();
        } catch (Exception e) {
            showAlert("Error", "Invalid input. Please check your entries.", AlertType.ERROR);
        }
    }

    // Handle deleting a reservation
    public void handleDeleteReservation() {
        Reservation selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            try {
                reservationDAO.deleteReservation(selectedReservation.getID());
                showAlert("Success", "Reservation deleted successfully!", AlertType.INFORMATION);
                loadReservations();
            } catch (Exception e) {
                showAlert("Error", "Failed to delete reservation.", AlertType.ERROR);
            }
        } else {
            showAlert("Error", "Please select a reservation to delete.", AlertType.WARNING);
        }
    }

    // Load all reservations into the table
    public void loadReservations() {
        try {
            Queue<Reservation> reservations = reservationDAO.getAllReservations();
            reservationTable.setItems(FXCollections.observableArrayList(reservations));
        } catch (Exception e) {
            showAlert("Error", "Failed to load reservations.", AlertType.ERROR);
        }
    }
}
