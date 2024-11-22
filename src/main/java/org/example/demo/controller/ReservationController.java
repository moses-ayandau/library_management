package org.example.demo.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.demo.dao.ReservationDAO;
import org.example.demo.dao.interfaces.IReservationDAO;
import org.example.demo.entity.Reservation;
import org.example.demo.entity.ReservationStatus;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ReservationController implements Initializable {
    @FXML
    private TableView<Reservation> reservationTable;
    @FXML
    private TableColumn<Reservation, Integer> idColumn;
    @FXML
    private TableColumn<Reservation, Integer> patronIdColumn;
    @FXML
    private TableColumn<Reservation, Integer> bookIdColumn;
    @FXML
    private TableColumn<Reservation, Date> dateColumn;
    @FXML
    private TableColumn<Reservation, String> statusColumn;
    @FXML
    private TableColumn<Reservation, Date> dueDateColumn;

    @FXML
    private TextField patronIdField;
    @FXML
    private TextField bookIdField;
    @FXML
    private DatePicker dueDatePicker;
    @FXML
    private ComboBox<ReservationStatus> statusComboBox;
    @FXML
    private Label reservationLabel;

    private final IReservationDAO reservationDAO = new ReservationDAO();
    private ObservableList<Reservation> reservationList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {


        reservationList = FXCollections.observableArrayList();

        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        patronIdColumn.setCellValueFactory(new PropertyValueFactory<>("patronID"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("reservedDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));


        statusComboBox.setItems(FXCollections.observableArrayList(ReservationStatus.values()));

        loadReservations();
    }


    @FXML
    private void handleCreateReservation() {
        try {
            Reservation reservation = new Reservation();
            reservation.setPatronID(Integer.parseInt(patronIdField.getText()));
            reservation.setBookID(Integer.parseInt(bookIdField.getText()));
            reservation.setReservedDate(Date.valueOf(LocalDate.now()));
            reservation.setStatus(ReservationStatus.PENDING);
            reservation.setDueDate(Date.valueOf(dueDatePicker.getValue()));

            reservationDAO.addReservation(reservation);
            loadReservations();
            clearFields();
            showAlert("Success", "Reservation created successfully!", Alert.AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Error", "Invalid input. Please check your entries.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleUpdateReservationStatus() {
        Reservation selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
        if (selectedReservation != null && statusComboBox.getValue() != null) {
            selectedReservation.setStatus(statusComboBox.getValue());
            try {
                reservationDAO.updateReservationStatus(selectedReservation);
                loadReservations();
                showAlert("Success", "Reservation status updated successfully!", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                showAlert("Error", "Failed to update reservation status.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Error", "Please select a reservation and a status.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void handleDeleteReservation() {
        Reservation selectedReservation = reservationTable.getSelectionModel().getSelectedItem();
        if (selectedReservation != null) {
            try {
                reservationDAO.deleteReservation(selectedReservation.getID());
                loadReservations();
                showAlert("Success", "Reservation deleted successfully!", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                showAlert("Error", "Failed to delete reservation.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Error", "Please select a reservation to delete.", Alert.AlertType.WARNING);
        }
    }

    private void loadReservations() {
        reservationList.clear();
        try {
            reservationList.addAll(reservationDAO.getAllReservations());
            reservationTable.setItems(reservationList);
        } catch (Exception e) {
            showAlert("Error", "Failed to load reservations.", Alert.AlertType.ERROR);
        }
    }

    private void clearFields() {
        patronIdField.clear();
        bookIdField.clear();
        dueDatePicker.setValue(null);
        statusComboBox.setValue(null);
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}