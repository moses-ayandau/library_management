package org.example.demo.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.example.demo.dao.BookDAO;
import org.example.demo.dao.ReservationDAO;
import org.example.demo.dao.TransactionDAO;
import org.example.demo.dao.interfaces.IBookDAO;
import org.example.demo.db.conn.DatabaseConnection;
import org.example.demo.entity.*;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookController {

    private final IBookDAO bookDAO = new BookDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();
    private final ReservationDAO reservationDAO = new ReservationDAO();

    @FXML
    private TableView<Book> bookTable;

    @FXML
    private TableColumn<Book, Integer> colId;
    @FXML
    private TableColumn<Book, String> colTitle;
    @FXML
    private TableColumn<Book, String> colDescription;
    @FXML
    private TableColumn<Book, String> colAuthor;
    @FXML
    private TableColumn<Book, String> colIsbn;
    @FXML
    private TableColumn<Book, Integer> colQuantity;
    @FXML
    private TableColumn<Book, Date> colYear;
    @FXML
    private TableColumn<Book, Void> colActions;  // Add an extra column for actions like Borrow and Reserve

    @FXML
    private TextField titleField, descriptionField, authorField, isbnField, yearField, reserveBookIdField, quantityField;
    @FXML
    private CheckBox availableCheckBox;
    @FXML
    private TextField borrowPatronIdField, borrowDueDateField;

    private ObservableList<Book> bookList;

    @FXML private Label welcomeLabel;

    public void initializeUser(User user) {
        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome, " + user.getName());
        } else {
            System.err.println("welcomeLabel is null!");
        }
    }

    @FXML
    public void initialize() {
        // Initialize the columns with the correct property value factories
        colId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("publishedYear"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        // Initialize the actions column (for buttons)
        colActions.setCellFactory(param -> new TableCell<Book, Void>() {
            private final Button borrowButton = new Button("Borrow");
            private final Button reserveButton = new Button("Reserve");

            {
                borrowButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
                reserveButton.setStyle("-fx-background-color: #FFC107; -fx-text-fill: white;");

                // Set actions for the buttons
                borrowButton.setOnAction(event -> {
                    try {
                        borrowBook(getTableRow().getItem());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
                reserveButton.setOnAction(event -> {
                    try {
                        reserveBook(getTableRow().getItem());
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox(10);
                    hBox.getChildren().addAll(borrowButton, reserveButton);
                    setGraphic(hBox);
                }
            }
        });

        loadBooks();  // Load books into the table
    }

    private void loadBooks() {
        try {
            List<Book> books = bookDAO.getAllBooks();
            bookList = FXCollections.observableArrayList(books);
            bookTable.setItems(bookList);
        } catch (SQLException e) {
            showError("Error loading books: " + e.getMessage());
        }
    }

    @FXML
    private void getBookById() {
        System.out.println("Fetching book by ID...");
    }
    @FXML
    private void loadBorrowedBooks(){}

    @FXML
    private void addBook() {
        try {
            Book newBook = new Book();
            newBook.setTitle(titleField.getText());
            newBook.setDescription(descriptionField.getText());
            newBook.setAuthor(authorField.getText());
            newBook.setIsbn(isbnField.getText());
            newBook.setPublishedYear(Integer.parseInt(yearField.getText()));
            newBook.setQuantity(Integer.parseInt(quantityField.getText()));  // Set quantity from the field

            bookDAO.addBook(newBook);
            loadBooks();
            showInfo("Book added successfully!");
        } catch (SQLException e) {
            showError("Error adding book: " + e.getMessage());
        }
    }

    private void borrowBook(Book book) throws SQLException {
        if (book != null && book.getQuantity() > 0) {
            // Step 1: Create a transaction (borrow action)
            int patronId = Integer.parseInt(borrowPatronIdField.getText());  // Assuming patron ID is entered in the field
            java.sql.Date borrowedDate = new java.sql.Date(System.currentTimeMillis());

            // Example: Set due date to 14 days from now
            java.sql.Date dueDate = new java.sql.Date(System.currentTimeMillis() + (14L * 24 * 60 * 60 * 1000)); // 14 days

            Transaction transaction = new Transaction();
            transaction.setBookID(book.getID());
            transaction.setPatronID(patronId);
            transaction.setBorrowedDate(borrowedDate);
            transaction.setDueDate(dueDate);

            // Step 2: Call TransactionDAO to create the transaction
            boolean transactionCreated = transactionDAO.createTransaction(transaction);

            if (transactionCreated) {
                book.setQuantity(book.getQuantity() - 1);
                boolean bookUpdated = bookDAO.updateBook(book);  // Update the book quantity in the database

                if (bookUpdated) {
                    // Step 4: Notify user and reload the book table
                    loadBooks();
                    showInfo("Book borrowed successfully: " + book.getTitle());
                } else {
                    // If the book update fails, revert the transaction creation
                    transactionDAO.returnBook(transaction.getID(), borrowedDate);  // Rollback the transaction
                    showError("Failed to update book availability. Please try again.");
                }
            } else {
                showError("Failed to borrow the book. Please try again.");
            }
        } else {
            showError("Book not available or already borrowed.");
        }
    }


    private void reserveBook(Book book) throws SQLException {
        if (book != null) {
            Reservation reservation = new Reservation();
            reservation.setBookID(book.getID());
            reservation.setPatronID(Integer.parseInt(borrowPatronIdField.getText()));
            reservation.setReservedDate(new java.sql.Date(System.currentTimeMillis()));
            reservation.setStatus(ReservationStatus.PENDING);

            reservationDAO.addReservation(reservation);
            book.setQuantity(book.getQuantity() - 1);
            bookDAO.updateBook(book);

            loadBooks();  // Reload the table with updated data
            showInfo("Book reserved successfully: " + book.getTitle());
        } else {
            showError("No book selected to reserve.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText(message);
        alert.show();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.show();
    }

    private void clearInputFields() {
        titleField.clear();
        descriptionField.clear();
        authorField.clear();
        isbnField.clear();
        yearField.clear();
        reserveBookIdField.clear();
        borrowPatronIdField.clear();
        borrowDueDateField.clear();
        quantityField.clear();  // Clear quantity as well
    }

    // Placeholder methods for UI navigation
    @FXML
    public void navigateToReservationScreen(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("reservation-ui.fxml"));
            Parent reservationRoot = loader.load();

            ReservationController reservationController = loader.getController();

            Scene reservationScene = new Scene(reservationRoot);

            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(reservationScene);
            stage.setTitle("Reservation Screen");

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to load the Reservation screen. " + e.getMessage());
        }
    }
}
