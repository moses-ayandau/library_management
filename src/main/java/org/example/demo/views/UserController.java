package org.example.demo.views;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.demo.dao.UserDAO;
import org.example.demo.entity.User;
import org.example.demo.entity.Role;
import org.example.demo.entity.Book;

import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, Integer> idColumn;
    @FXML
    private TableColumn<User, String> nameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TableColumn<User, String> phoneColumn;

    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;
    @FXML
    private ComboBox<Role> roleComboBox;

    @FXML
    private ListView<Book> reservationList;

    private UserDAO userDAO;
    private ObservableList<User> userList;
    private ObservableList<Book> reservationsList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        userDAO = new UserDAO();
        userList = FXCollections.observableArrayList();
        reservationsList = FXCollections.observableArrayList();

        // Initialize table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("patronID"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));

        // Initialize role combo box
        roleComboBox.setItems(FXCollections.observableArrayList(Role.values()));

        // Add selection listener to user table
        userTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                loadUserReservations(newSelection.getPatronID());
            }
        });

        loadUsers();
    }

    @FXML
    private void handleCreateUser() {
        if (validateInput()) {
            User user = new User(
                    nameField.getText(),
                    emailField.getText(),
                    phoneField.getText(),
                    addressField.getText(),
                    roleComboBox.getValue()
            );

            if (userDAO.createUser(user)) {
                loadUsers();
                clearFields();
                showAlert("Success", "User created successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to create user.", Alert.AlertType.ERROR);
            }
        }
    }

    @FXML
    private void handleUpdateUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null && validateInput()) {
            selectedUser.setName(nameField.getText());
            selectedUser.setEmail(emailField.getText());
            selectedUser.setPhone(phoneField.getText());
            selectedUser.setAddress(addressField.getText());
            selectedUser.setRole(roleComboBox.getValue());

            if (userDAO.updateUser(selectedUser)) {
                loadUsers();
                clearFields();
                showAlert("Success", "User updated successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to update user.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Error", "Please select a user to update.", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void handleDeleteUser() {
        User selectedUser = userTable.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            if (userDAO.deleteUser(selectedUser.getPatronID())) {
                loadUsers();
                clearFields();
                showAlert("Success", "User deleted successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to delete user.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Error", "Please select a user to delete.", Alert.AlertType.WARNING);
        }
    }

    private void loadUsers() {
        userList.clear();
        userList.addAll(userDAO.getAllUsers());
        userTable.setItems(userList);
    }

    private void loadUserReservations(int userId) {
        User user = userDAO.getUserWithReservations(userId);
        if (user != null) {
            reservationsList.clear();
            reservationsList.addAll(user.getBooks());
            reservationList.setItems(reservationsList);
        }
    }

    private boolean validateInput() {
        if (nameField.getText().isEmpty() || emailField.getText().isEmpty() ||
                phoneField.getText().isEmpty() || addressField.getText().isEmpty() ||
                roleComboBox.getValue() == null) {
            showAlert("Error", "All fields are required.", Alert.AlertType.ERROR);
            return false;
        }
        return true;
    }

    private void clearFields() {
        nameField.clear();
        emailField.clear();
        phoneField.clear();
        addressField.clear();
        roleComboBox.setValue(null);
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}