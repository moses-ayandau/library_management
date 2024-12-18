//package org.example.demo.controller;
//
//import javafx.application.Platform;
//import javafx.embed.swing.JFXPanel;
//import javafx.scene.control.TextField;
//import javafx.scene.control.PasswordField;
//import javafx.scene.control.Button;
//import javafx.scene.layout.VBox;
//
//import org.example.demo.dao.UserDAO;
//import org.example.demo.entity.User;
//import org.example.demo.entity.Role;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.sql.SQLException;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.TimeUnit;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class AuthControllerIntegrationTest {
//
//    private AuthController authController;
//
//    @Mock
//    private UserDAO mockUserDAO;
//
//    @BeforeAll
//    static void initJavaFX() throws InterruptedException {
//        // Initialize JavaFX environment
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.startup(() -> {
//            new JFXPanel();
//            latch.countDown();
//        });
//        latch.await(5, TimeUnit.SECONDS);
//    }
//
//    @BeforeEach
//    void setUp() throws InterruptedException {
//        CountDownLatch latch = new CountDownLatch(1);
//        Platform.runLater(() -> {
//            authController = new AuthController();
//            authController.setUserDAO(mockUserDAO);
//
//            // Setup FXML fields manually for testing
//            authController.nameField = new TextField();
//            authController.emailField = new TextField();
//            authController.phoneField = new TextField();
//            authController.addressField = new TextField();
//            authController.passwordField = new PasswordField();
//            authController.actionButton = new Button();
//            authController.authBox = new VBox();
//
//            latch.countDown();
//        });
//        latch.await(5, TimeUnit.SECONDS);
//    }
//
//    @Test
//    void testCreateAccountSuccess() throws SQLException, InterruptedException {
//        CountDownLatch latch = new CountDownLatch(1);
//
//        Platform.runLater(() -> {
//            try {
//                authController.isCreatingAccount = true;
//                authController.initialize();
//
//                authController.nameField.setText("John Doe");
//                authController.emailField.setText("john@example.com");
//                authController.phoneField.setText("1234567890");
//                authController.addressField.setText("123 Test St");
//                authController.passwordField.setText("password123");
//
//                User expectedUser = new User("John Doe", "john@example.com", "1234567890", "123 Test St", Role.PATRON);
//                expectedUser.setPassword("password123");
//
//                when(mockUserDAO.createUser(any(User.class))).thenReturn(true);
//
//                authController.createAccount();
//
//                verify(mockUserDAO).createUser(any(User.class));
//                assertTrue(authController.emailField.getText().isEmpty(), "Input fields should be cleared after account creation");
//            } catch (SQLException e) {
//                fail("Unexpected SQLException: " + e.getMessage());
//            } finally {
//                latch.countDown();
//            }
//        });
//
//        assertTrue(latch.await(10, TimeUnit.SECONDS), "Test timed out");
//    }
//
//    @Test
//    void testLoginSuccess() throws SQLException, InterruptedException {
//        CountDownLatch latch = new CountDownLatch(1);
//
//        Platform.runLater(() -> {
//            try {
//                authController.isCreatingAccount = false;
//                authController.initialize();
//
//                authController.emailField.setText("john@example.com");
//                authController.passwordField.setText("password123");
//
//                User expectedUser = new User("John Doe", "john@example.com", "1234567890", "123 Test St", Role.PATRON);
//                when(mockUserDAO.loginUser("john@example.com", "password123")).thenReturn(expectedUser);
//
//                authController.loginUser();
//
//                verify(mockUserDAO).loginUser("john@example.com", "password123");
//            } catch (SQLException e) {
//                fail("Unexpected SQLException: " + e.getMessage());
//            } finally {
//                latch.countDown();
//            }
//        });
//
//        assertTrue(latch.await(10, TimeUnit.SECONDS), "Test timed out");
//    }
//
//
//    @Test
//    void testToggleCreateAccountMode() throws InterruptedException {
//        CountDownLatch latch = new CountDownLatch(1);
//
//        Platform.runLater(() -> {
//            authController.isCreatingAccount = false;
//
//            authController.toggleCreateAccountMode();
//
//            // Assert
//            assertTrue(authController.isCreatingAccount, "Create account mode should be toggled");
//            latch.countDown();
//        });
//
//        // Wait for the JavaFX thread to complete
//        assertTrue(latch.await(10, TimeUnit.SECONDS), "Test timed out");
//    }
//}