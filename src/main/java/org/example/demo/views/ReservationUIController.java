package org.example.demo.views;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.demo.dao.ReservationDAO;
import org.example.demo.entity.Reservation;
import org.example.demo.entity.ReservationStatus;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;

public class ReservationUIController {

    @FXML private TextField patronIdField;
    @FXML private TextField bookIdField;
    @FXML private DatePicker reservationDatePicker;
    @FXML private DatePicker dueDatePicker;
    @FXML private ChoiceBox<ReservationStatus> statusChoiceBox;
    @FXML private Button addReservationButton;
    @FXML private TextField reservationIdField;
    @FXML private Button getReservationButton;
    @FXML private TableView<Reservation> reservationTable;

    private ReservationDAO reservationDAO;

    public void setReservationDAO(ReservationDAO reservationDAO) {
        this.reservationDAO = reservationDAO;
        statusChoiceBox.getItems().setAll(ReservationStatus.values());
    }

    @FXML
    public void initialize() {
        TableColumn<Reservation, Integer> idCol = new TableColumn<>("ID");
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));

        TableColumn<Reservation, Integer> patronCol = new TableColumn<>("Patron ID");
        patronCol.setCellValueFactory(new PropertyValueFactory<>("patronID"));

        TableColumn<Reservation, Integer> bookCol = new TableColumn<>("Book ID");
        bookCol.setCellValueFactory(new PropertyValueFactory<>("bookID"));

        TableColumn<Reservation, Date> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        TableColumn<Reservation, ReservationStatus> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        reservationTable.getColumns().addAll(idCol, patronCol, bookCol, dateCol, statusCol);
    }

    @FXML
    private void addReservation() throws SQLException {
        Reservation reservation = new Reservation();
        reservation.setPatronID(Integer.parseInt(patronIdField.getText()));
        reservation.setBookID(Integer.parseInt(bookIdField.getText()));
        reservation.setDate(Date.valueOf(reservationDatePicker.getValue()));
        reservation.setDueDate(Date.valueOf(dueDatePicker.getValue()));
        reservation.setStatus(statusChoiceBox.getValue());

        reservationDAO.addReservation(reservation);
        refreshTable();
    }

    @FXML
    private void getReservationById() throws SQLException {
        int id = Integer.parseInt(reservationIdField.getText());
        Reservation reservation = reservationDAO.getReservationByID(id);
        if (reservation != null) {
            reservationTable.getItems().clear();
            reservationTable.getItems().add(reservation);
        }
    }

    private void refreshTable() throws SQLException {
        reservationTable.getItems().setAll(reservationDAO.getAllReservations());
    }
}
