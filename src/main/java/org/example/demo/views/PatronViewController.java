package org.example.demo.views;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.example.demo.dao.PatronDAO;
import org.example.demo.entity.Member;

public class PatronViewController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;

    @FXML
    private TextField patronIDField;

    private final PatronDAO patronDAO = new PatronDAO();

    @FXML
    public void handleAddPatron() {
        try {
            Member patron = new Member();
            patron.setName(nameField.getText());
            patron.setEmail(emailField.getText());
            patron.setPhone(phoneField.getText());
            patron.setAddress(addressField.getText());

            patronDAO.addPatron(patron);
            showAlert("Success", "Patron added successfully.", AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Error", "Could not add patron. Check data and try again.", AlertType.ERROR);
        }
    }

    @FXML
    public void handleUpdatePatron() {
        try {
            int patronID = Integer.parseInt(patronIDField.getText());
            Member patron = patronDAO.getPatronById(patronID);
            if (patron != null) {
                patron.setName(nameField.getText());
                patron.setEmail(emailField.getText());
                patron.setPhone(phoneField.getText());
                patron.setAddress(addressField.getText());
                patronDAO.updatePatron(patron);
                showAlert("Success", "Patron updated successfully.", AlertType.INFORMATION);
            } else {
                showAlert("Error", "Patron not found.", AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Error", "Could not update patron.", AlertType.ERROR);
        }
    }

    @FXML
    public void handleDeletePatron() {
        try {
            int patronID = Integer.parseInt(patronIDField.getText());
            patronDAO.deletePatron(patronID);
            showAlert("Success", "Patron deleted successfully.", AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Error", "Could not delete patron.", AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
