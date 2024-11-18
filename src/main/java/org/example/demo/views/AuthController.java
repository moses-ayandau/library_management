package org.example.demo.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.demo.Application;
import org.example.demo.dao.UserDAOC;
import org.example.demo.entity.User;
import org.example.demo.entity.Role;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;

public class AuthController {

    @FXML private TextField nameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private PasswordField passwordField;
    @FXML private Button actionButton;

    private UserDAOC userDAO = new UserDAOC();

    @FXML
    private VBox authBox;

    private boolean isCreatingAccount = false;

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

    public void handleAction() {
        if (isCreatingAccount) {
            createAccount();
        } else {
            loginUser();
        }
    }

   @FXML
    private void loginUser() {
        String email = emailField.getText();
        String password = passwordField.getText();

        if (email.isEmpty() || password.isEmpty()) {
            showAlert("Validation Error", "Email and password cannot be empty.", AlertType.ERROR);
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
    private void createAccount() {
        String name = nameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();
        String address = addressField.getText();
        String password = passwordField.getText();

        if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || address.isEmpty() || password.isEmpty()) {
            showAlert("Validation Error", "All fields must be filled out.", AlertType.ERROR);
            return;
        }

        User newUser = new User(name, email, phone, address, Role.PATRON);
        newUser.setPassword(password);

        boolean isCreated = userDAO.createUser(newUser);
        if (isCreated) {
            showAlert("User Created", "Account has been successfully created.", AlertType.INFORMATION);
            toggleCreateAccountMode();
        } else {
            showAlert("Error", "User creation failed.", AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    @FXML
    private void switchToBookPage(User user) {
        try {
            URL fxmlUrl = getClass().getResource("/org/example/demo/book-ui.fxml");
            if (fxmlUrl == null) {
                System.err.println("FXML file not found at: /org/example/demo/book-ui.fxml");
                showAlert("Error", "FXML file not found.", AlertType.ERROR);
                return;
            }

            FXMLLoader loader = new FXMLLoader(fxmlUrl);
            Scene scene = new Scene(loader.load());

            BookController bookUIController = loader.getController();
            bookUIController.initializeUser(user);

            Stage stage = (Stage) authBox.getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle("Book UI");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            showAlert("Error", "Failed to load the book UI page.", AlertType.ERROR);
        }
    }

}
