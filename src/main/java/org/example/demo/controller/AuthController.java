package org.example.demo.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.demo.dao.UserDAO;
import org.example.demo.dao.interfaces.IUserDAO;
import org.example.demo.entity.User;
import org.example.demo.entity.Role;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;

public class AuthController {

    @FXML
    public TextField nameField;
    @FXML
    public TextField emailField;
    @FXML
    public TextField phoneField;
    @FXML
    public TextField addressField;
    @FXML
    public PasswordField passwordField;
    @FXML Button actionButton;

    IUserDAO userDAO = new UserDAO();

    @FXML
    VBox authBox;

    boolean isCreatingAccount = false;

    public void initialize() {
        if (isCreatingAccount) {
            actionButton.setText("Create Account");
            nameField.setVisible(true);
            phoneField.setVisible(true);
            addressField.setVisible(true);
        } else {
            actionButton.setText("Login");
            nameField.setVisible(false);
            phoneField.setVisible(false);
            addressField.setVisible(false);
        }
    }

    public void toggleCreateAccountMode() {
        isCreatingAccount = !isCreatingAccount;
        initialize();
    }

    public void handleAction() throws SQLException {
        if (isCreatingAccount) {
            createAccount();
        } else {
            loginUser();
        }
    }

    @FXML
    void loginUser() throws SQLException {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Validation Error", "Email and password cannot be empty.", AlertType.ERROR);
            return;
        }

        if (!email.contains("@")) {
            showAlert("Validation Error", "Please enter a valid email address.", AlertType.ERROR);
            return;
        }

        User user = userDAO.loginUser(email, password);
        if (user != null) {
            showAlert("Login Successful", "Welcome, " + user.getName(), AlertType.INFORMATION);
            switchToBookPage(user);
        } else {
            showAlert("Login Failed", "Invalid email or password.", AlertType.ERROR);
        }
    }

    @FXML
    public void createAccount() throws SQLException {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        String password = passwordField.getText();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty()) {
            showAlert("Validation Error", "All fields must be filled out.", AlertType.ERROR);
            return;
        }

        if (!email.contains("@")) {
            showAlert("Validation Error", "Please enter a valid email address.", AlertType.ERROR);
            return;
        }

        User newUser = new User(name, email, phone, address, Role.PATRON);
        newUser.setPassword(password);

        boolean isCreated = userDAO.createUser(newUser);
        if (isCreated) {
            showAlert("User Created", "Account has been successfully created.", AlertType.INFORMATION);
            toggleCreateAccountMode();
            clearInputFields();  // Clear input fields after successful account creation
        } else {
            showAlert("Error", "User creation failed.", AlertType.ERROR);
        }
    }

     void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void switchToBookPage(User user) {
        try {
            URL fxmlUrl = getClass().getResource("/org/example/demo/book-ui.fxml");
            if (fxmlUrl == null) {
                showAlert("Error", "FXML file not found.", AlertType.ERROR);
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Scene scene = new Scene(loader.load());

            BookController bookUIController = loader.getController();
            bookUIController.initializeUser(user);

            Stage stage = (Stage) authBox.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Library");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the book UI page.", AlertType.ERROR);
        }
    }

    // Clear input fields after account creation
     void clearInputFields() {
        nameField.clear();
        emailField.clear();
        phoneField.clear();
        addressField.clear();
        passwordField.clear();
    }

    public void setUserDAO(UserDAO mockUserDAO) {
        this.userDAO = mockUserDAO;
    }
}
