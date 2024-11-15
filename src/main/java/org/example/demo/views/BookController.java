package org.example.demo.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.demo.dao.BookDAO;
import org.example.demo.entity.Book;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class BookController {
    private final BookDAO bookDAO = new BookDAO();

    // TableView and Columns
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
    private TableColumn<Book, Date> colYear;

//    @FXML
//    private TableColumn<Book, Integer> colQuantity;

    @FXML
    private TableColumn<Book, Boolean> colAvailable;

    // Other UI components
    @FXML
    private TextField titleField, descriptionField, authorField, isbnField, yearField, bookIdField, quantityField;

    @FXML
    private CheckBox availableCheckBox;

    // ObservableList to hold Book data
    private ObservableList<Book> bookList;

    @FXML
    public void initialize() {
        // Initialize TableView columns
        colId.setCellValueFactory(new PropertyValueFactory<>("ID"));
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colAuthor.setCellValueFactory(new PropertyValueFactory<>("author"));
        colIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("publishedYear"));
//        colQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        colAvailable.setCellValueFactory(new PropertyValueFactory<>("available"));

        // Load initial data
        loadBooks();
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
    private void viewAllBooks() {
        loadBooks();
    }

    @FXML
    private void addBook() {
        try {
            Book book = new Book();
            book.setTitle(titleField.getText());
            book.setDescription(descriptionField.getText());
            book.setAuthor(authorField.getText());
            book.setIsbn(isbnField.getText());
            book.setPublishedYear(new Date(Integer.parseInt(yearField.getText()) - 1900, 0, 1));
//            book.setQuantity(Integer.parseInt(quantityField.getText()));
            book.setAvailable(availableCheckBox.isSelected());

            bookDAO.addBook(book);
            showInfo("Book added successfully!");
            clearInputFields();

            // Refresh the TableView
            loadBooks();
        } catch (SQLException e) {
            showError("Error adding book: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void getBookById() {
        try {
            int bookId = Integer.parseInt(bookIdField.getText());
            Book book = bookDAO.getBookById(bookId);
            if (book != null) {
                bookList.clear();
                bookList.add(book);
            } else {
                showError("Book not found!");
            }
        } catch (SQLException e) {
            showError("Error retrieving book: " + e.getMessage());
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
        quantityField.clear();
        bookIdField.clear();
        availableCheckBox.setSelected(false);
    }
}
