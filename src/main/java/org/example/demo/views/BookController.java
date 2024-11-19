package org.example.demo.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.demo.dao.BookDAO;
import org.example.demo.dao.ReservationDAO;
import org.example.demo.dao.TransactionDAO;
import org.example.demo.dao.interfaces.IBookDAO;
import org.example.demo.db.conn.DatabaseConnection;
import org.example.demo.dto.UserReservationDTO;
import org.example.demo.entity.*;

import java.sql.*;
import java.util.List;

public class BookController {

    private final IBookDAO bookDAO = new BookDAO();
    private final TransactionDAO transactionDAO = new TransactionDAO();
    private final ReservationDAO reservationDAO = new ReservationDAO();
    @FXML
    public TableColumn<Transaction, Integer> bookID;
    @FXML
    public TableView<Transaction> borrowedTable;
    @FXML
    public TableColumn<Transaction, Integer> patronID;
    @FXML
    public TableColumn<Transaction, Date> borrowedDate;
    @FXML
    public TableColumn<Transaction, Date> dueDate;
    @FXML
    public TableColumn<Transaction, Void> returnCol;

    @FXML
    public TableView<Reservation> reservedTable;
    @FXML
    public TableColumn<Reservation, Integer> reservedBookID;
    @FXML
    public TableColumn<Reservation, Integer> reservedPatronID;
    @FXML
    public TableColumn<Reservation, Date> reservedDate;
    @FXML
    public TableColumn<Reservation, String> status;
    @FXML
    public TableColumn<Reservation, Void> Clear;
    @FXML
    public TableColumn<Reservation, Date> reservedDueDate;

    @FXML
    public TableView<UserReservationDTO> userWithReservationTable;
    @FXML
    public TableColumn<UserReservationDTO, Integer> userID;
    @FXML
    public TableColumn<UserReservationDTO, String> userName;
    @FXML
    public TableColumn<UserReservationDTO, String> userEmail;
    @FXML
    public TableColumn<UserReservationDTO, String> userPhone;
    @FXML
    public TableColumn<UserReservationDTO, String> userAddress;
    @FXML
    public TableColumn<UserReservationDTO, Integer> userBookID;
    @FXML
    public TableColumn<UserReservationDTO, Date> reservedDateUser;


    @FXML
    private TextField bookIdField;

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
    private TableColumn<Book, Void> colActions;

    @FXML
    private TextField titleField, descriptionField, authorField, isbnField, yearField, quantityField;
    @FXML
    private CheckBox availableCheckBox;
    @FXML
    private TextField borrowPatronIdField, borrowDueDateField;
    @FXML
    private Label welcomeLabel;

    private ObservableList<Reservation> reservationsList;

    public void initializeUser(User user) {
        if (welcomeLabel != null) {
            welcomeLabel.setText("Welcome, " + user.getName());
        } else {
            System.err.println("welcomeLabel is null!");
        }
    }

    @FXML
    public void initialize() {
        setupTableColumns();
        setupReturnButton();
        loadBooks();
        loadBorrowedBooks();
        loadReservations();
        loadUsersWithReservations();
    }

    private void setupTableColumns() {
        // Book table
        colId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("publishedYear"));
        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        // Transaction table
        bookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        patronID.setCellValueFactory(new PropertyValueFactory<>("patronID"));
        borrowedDate.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        dueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        // Reservation table
        reservedBookID.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        reservedPatronID.setCellValueFactory(new PropertyValueFactory<>("patronID"));
        reservedDate.setCellValueFactory(new PropertyValueFactory<>("reservedDate"));

        // Users with reservation
        userID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        userName.setCellValueFactory(new PropertyValueFactory<>("name"));
        userEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        userPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        userAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        userBookID.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        reservedDateUser.setCellValueFactory(new PropertyValueFactory<>("reservedDate"));


        setupActionButtons();
    }

    private void setupActionButtons() {
        colActions.setCellFactory(param -> new TableCell<Book, Void>() {
            private final Button borrowButton = createStyledButton("Borrow", "#4CAF50");
            private final Button reserveButton = createStyledButton("Reserve", "#FFC107");
            private final Button deleteButton = createStyledButton("Delete", "#F44336");

            {
                borrowButton.setOnAction(event -> openBorrowModal(getTableRow().getItem()));
                reserveButton.setOnAction(event -> {
                    try {
                        openReserveModal(getTableRow().getItem());
                    } catch (Exception e) {
                        showError("Error opening the reserve modal: " + e.getMessage());
                    }
                });
                deleteButton.setOnAction(event -> openDeleteModal(getTableRow().getItem()));
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    HBox hBox = new HBox(10);
                    hBox.getChildren().addAll(borrowButton, reserveButton, deleteButton);
                    setGraphic(hBox);
                }
            }
        });
    }
    private void openDeleteModal(Book book) {
        if (book == null) return;

        Stage deleteStage = new Stage();
        deleteStage.initModality(Modality.APPLICATION_MODAL);
        deleteStage.setTitle("Confirm Deletion");

        Label confirmationLabel = new Label("Are you sure you want to delete the book: " + book.getTitle() + "?");
        Button confirmButton = createStyledButton("Yes", "#4CAF50");
        Button cancelButton = createStyledButton("No", "#F44336");

        confirmButton.setOnAction(event -> {
            try {
                deleteBook(book);
                deleteStage.close();
            } catch (SQLException e) {
                showError("Error deleting book: " + e.getMessage());
            }
        });

        cancelButton.setOnAction(event -> deleteStage.close());

        HBox buttonBox = new HBox(10, confirmButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER);

        VBox vbox = new VBox(10, confirmationLabel, buttonBox);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20;");

        Scene scene = new Scene(vbox, 400, 150);
        deleteStage.setScene(scene);
        deleteStage.showAndWait();
    }
    private void deleteBook(Book book) throws SQLException {
        if (bookDAO.deleteBook(book.getID())) {
            loadBooks();
            showInfo("Book deleted successfully: " + book.getTitle());
        } else {
            showError("Failed to delete the book.");
        }
    }


    private Button createStyledButton(String text, String color) {
        Button button = new Button(text);
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white;");
        return button;
    }

    private void setupReturnButton() {
        returnCol.setCellFactory(param -> new TableCell<Transaction, Void>() {
            private final Button returnButton = createStyledButton("Return", "#4CAF50");

            {
                returnButton.setOnAction(event -> {
                    Transaction transaction = getTableRow().getItem();
                    if (transaction != null) {
                        try {
                            handleReturnBook(transaction);
                        } catch (SQLException e) {
                            showError("Error returning the book: " + e.getMessage());
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : returnButton);
            }
        });
    }

    private void loadBooks() {
        try {
            List<Book> books = bookDAO.getAllBooks();
            ObservableList<Book> bookList = FXCollections.observableArrayList(books);
            bookTable.setItems(bookList);
        } catch (SQLException e) {
            showError("Error loading books: " + e.getMessage());
        }
    }

    private void loadBorrowedBooks() {
        List<Transaction> transactions = transactionDAO.getActiveTransactions();
        ObservableList<Transaction> transactionsList = FXCollections.observableArrayList(transactions);
        borrowedTable.setItems(transactionsList);
    }

    private void loadReservations() {
        try {
            List<Reservation> reservations = reservationDAO.getAllReservations();
            reservationsList = FXCollections.observableArrayList(reservations);
            reservedTable.setItems(reservationsList);
        } catch (SQLException e) {
            showError("Error loading reservations: " + e.getMessage());
        }
    }

    private void loadUsersWithReservations() {
        ObservableList<UserReservationDTO> usersWithReservations = FXCollections.observableArrayList();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT u.id AS userID, u.name, u.email, u.phone, u.address, r.bookId, r.reservedDate " +
                             "FROM user u " +
                             "JOIN reservation r ON u.id = r.userId")) {

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                // Create a new DTO instance
                UserReservationDTO dto = new UserReservationDTO();
                dto.setID(rs.getInt("userID"));
                dto.setName(rs.getString("name"));
                dto.setEmail(rs.getString("email"));
                dto.setPhone(rs.getString("phone"));
                dto.setAddress(rs.getString("address"));
                dto.setBookId(rs.getInt("bookId"));
                dto.setReservedDate(rs.getDate("reservedDate"));

                // Add the DTO to the observable list
                usersWithReservations.add(dto);
            }

            // Set the items in the TableView
            userWithReservationTable.setItems(usersWithReservations);

        } catch (SQLException e) {
            showError("Error loading users with reservations: " + e.getMessage());
        }
    }


    @FXML
    private void addBook() {
        try {
            Book newBook = new Book();
            newBook.setTitle(titleField.getText());
            newBook.setDescription(descriptionField.getText());
            newBook.setAuthor(authorField.getText());
            newBook.setIsbn(isbnField.getText());
            newBook.setPublishedYear(Integer.parseInt(yearField.getText()));
            newBook.setQuantity(Integer.parseInt(quantityField.getText()));

            if (validateBook(newBook)) {
                bookDAO.addBook(newBook);
                loadBooks();
                clearInputFields();
                showInfo("Book added successfully!");
            }
        } catch (SQLException e) {
            showError("Error adding book: " + e.getMessage());
        } catch (NumberFormatException e) {
            showError("Please enter valid numbers for year and quantity.");
        }
    }



    @FXML
    public void getBookById(ActionEvent event) {
        try {
            String bookIdText = bookIdField.getText();
            if (bookIdText == null || bookIdText.trim().isEmpty()) {
                showError("Please enter a book ID");
                return;
            }

            int bookId = Integer.parseInt(bookIdText);
            Book book = bookDAO.getBookById(bookId);

            if (book != null) {
                ObservableList<Book> searchResult = FXCollections.observableArrayList(book);
                bookTable.setItems(searchResult);
            } else {
                showError("Book not found");
                bookTable.setItems(FXCollections.emptyObservableList());
            }
        } catch (NumberFormatException e) {
            showError("Please enter a valid book ID number");
        } catch (SQLException e) {
            showError("Error searching for book: " + e.getMessage());
        }
    }

    private boolean validateBook(Book book) {
        if (book.getTitle().isEmpty() || book.getAuthor().isEmpty() || book.getIsbn().isEmpty()) {
            showError("Please fill in all required fields.");
            return false;
        }
        if (book.getQuantity() < 0) {
            showError("Quantity cannot be negative.");
            return false;
        }
        return true;
    }

    private void borrowBook(Book book, Integer patronId) throws SQLException {
        if (book == null || book.getQuantity() <= 0) {
            showError("Book not available for borrowing.");
            return;
        }

        Transaction transaction = new Transaction();
        transaction.setBookID(book.getID());
        transaction.setPatronID(patronId);
        transaction.setBorrowedDate(new java.sql.Date(System.currentTimeMillis()));
        transaction.setDueDate(new java.sql.Date(System.currentTimeMillis() + (30L * 24 * 60 * 60 * 1000)));

        if (transactionDAO.createTransaction(transaction)) {
            book.setQuantity(book.getQuantity() - 1);
            if (bookDAO.updateBook(book)) {
                loadBooks();
                loadBorrowedBooks();
                showInfo("Book borrowed successfully: " + book.getTitle());
            } else {
                transactionDAO.returnBook(transaction.getID(), transaction.getBorrowedDate());
                showError("Failed to update book availability.");
            }
        } else {
            showError("Failed to create borrowing transaction.");
        }
    }

    private void handleReturnBook(Transaction transaction) throws SQLException {
        if (transaction == null) {
            showError("No transaction selected.");
            return;
        }

        if (transactionDAO.returnBook(transaction.getID(), new java.sql.Date(System.currentTimeMillis()))) {
            Book book = bookDAO.getBookById(transaction.getBookID());
            if (book != null) {
                book.setQuantity(book.getQuantity() + 1);
                bookDAO.updateBook(book);
            }
            loadBooks();
            loadBorrowedBooks();
            showInfo("Book returned successfully!");
        } else {
            showError("Failed to return the book.");
        }
    }

    private void reserveBook(Book book, Integer patronID) throws SQLException {
        if (book == null) {
            showError("No book selected to reserve.");
            return;
        }

        if (book.getQuantity() <= 0) {
            showError("Book not available for reservation.");
            return;
        }

        Reservation reservation = new Reservation();
        reservation.setBookID(book.getID());
        reservation.setPatronID(patronID);
        reservation.setReservedDate(new java.sql.Date(System.currentTimeMillis()));
        reservation.setStatus(ReservationStatus.PENDING);

        if (reservationDAO.addReservation(reservation)) {
            book.setQuantity(book.getQuantity() - 1);
            if (bookDAO.updateBook(book)) {
                loadBooks();
                loadReservations();
                loadUsersWithReservations();
                showInfo("Book reserved successfully: " + book.getTitle());
            } else {
//                reservationDAO.cancelReservation(reservation.getID());
                showError("Failed to update book availability.");
            }
        } else {
            showError("Failed to create reservation.");
        }
    }

    private void openBorrowModal(Book book) {
        Stage borrowStage = new Stage();
        borrowStage.initModality(Modality.APPLICATION_MODAL);
        borrowStage.setTitle("Borrow Book");

        VBox vbox = createBorrowModalContent(book, borrowStage);
        Scene scene = new Scene(vbox, 300, 200);
        borrowStage.setScene(scene);
        borrowStage.showAndWait();
    }

    private VBox createBorrowModalContent(Book book, Stage stage) {
        Label userIdLabel = new Label("User ID:");
        TextField userIdField = new TextField();
        userIdField.setPromptText("Enter User ID");

        Button borrowConfirmButton = createStyledButton("Confirm Borrow", "#4CAF50");
        borrowConfirmButton.setOnAction(event -> {
            try {
                int userId = Integer.parseInt(userIdField.getText());
                if (userId > 0) {
                    borrowBook(book, userId);
                    stage.close();
                } else {
                    showError("Please enter a valid User ID.");
                }
            } catch (NumberFormatException e) {
                showError("Invalid User ID format.");
            } catch (SQLException e) {
                showError("Error while borrowing: " + e.getMessage());
            }
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(userIdLabel, userIdField, borrowConfirmButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20;");
        return vbox;
    }

    private void openReserveModal(Book book) {
        Stage reserveStage = new Stage();
        reserveStage.initModality(Modality.APPLICATION_MODAL);
        reserveStage.setTitle("Reserve Book");

        VBox vbox = createReserveModalContent(book, reserveStage);
        Scene scene = new Scene(vbox, 300, 200);
        reserveStage.setScene(scene);
        reserveStage.showAndWait();
    }

    private VBox createReserveModalContent(Book book, Stage stage) {
        Label patronIdLabel = new Label("Patron ID:");
        TextField patronIdField = new TextField();
        patronIdField.setPromptText("Enter Patron ID");

        Button reserveConfirmButton = createStyledButton("Confirm Reserve", "#FFC107");
        reserveConfirmButton.setOnAction(event -> {
            try {
                int patronId = Integer.parseInt(patronIdField.getText());
                if (patronId > 0) {
                    reserveBook(book, patronId);
                    stage.close();
                } else {
                    showError("Please enter a valid Patron ID.");
                }
            } catch (NumberFormatException e) {
                showError("Invalid Patron ID format.");
            } catch (SQLException e) {
                showError("Error while reserving: " + e.getMessage());
            }
        });

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(patronIdLabel, patronIdField, reserveConfirmButton);
        vbox.setAlignment(Pos.CENTER);
        vbox.setStyle("-fx-padding: 20;");
        return vbox;
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
        borrowPatronIdField.clear();
        borrowDueDateField.clear();
        quantityField.clear();
    }
}