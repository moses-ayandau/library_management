package org.example.demo.views;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.example.demo.dao.BookDAO;
import org.example.demo.entity.Book;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class BookUIController {
    private final BookDAO bookDAO = new BookDAO();

    @FXML
    private TextArea bookDisplayArea;

    @FXML
    private TextField titleField, descriptionField, authorField, isbnField, yearField, bookIdField, quantityField;

    @FXML
    private CheckBox availableCheckBox;

    @FXML
    private void viewAllBooks() {
        try {
            List<Book> books = bookDAO.getAllBooks();
            bookDisplayArea.clear();
            for (Book book : books) {
                bookDisplayArea.appendText(book.toString() + "\n");
            }
        } catch (SQLException e) {
            showError("Error fetching books: " + e.getMessage());
        }
    }

    @FXML
    private void addBook() {
        try {
            Book book = new Book();
            book.setTitle(titleField.getText());
            book.setDescription(descriptionField.getText());
            book.setAuthor(authorField.getText());
            book.setIsbn(isbnField.getText());
            book.setPublishedDate(new Date(Integer.parseInt(yearField.getText()) - 1900, 0, 1)); // Using a default date
            book.setQuantity(Integer.parseInt(quantityField.getText()));
            book.setAvailable(availableCheckBox.isSelected());

            bookDAO.addBook(book);
            showInfo("Book added successfully!");
            clearInputFields();
        } catch (SQLException e) {
            showError("Error adding book: " + e.getMessage());
        }
    }

    @FXML
    private void getBookById() {
        try {
            int bookId = Integer.parseInt(bookIdField.getText());
            Book book = bookDAO.getBookById(bookId);
            if (book != null) {
                bookDisplayArea.setText(book.toString());
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
