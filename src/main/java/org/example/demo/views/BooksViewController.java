package org.example.demo.views;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import org.example.demo.dao.BookDAO;
import org.example.demo.entity.Book;

public class BooksViewController {
    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextField descriptionField;

    @FXML
    private TextField isbnField;
    @FXML
    private TextField yearField;


    private BookDAO bookDAO = new BookDAO();

    @FXML
    public void handleAddBook() {
        try {
            String title = titleField.getText();
            String author = authorField.getText();
            String isbn = isbnField.getText();
            String description = descriptionField.getText();
            int year = Integer.parseInt(yearField.getText());
            Book book = new Book(0, title, author,description, isbn, year, true);
            bookDAO.addBook(book);
            showAlert("Book Added", "Book added successfully", AlertType.INFORMATION);
        } catch (Exception e) {
            showAlert("Error", "Error adding book", AlertType.ERROR);
        }
    }

    private void showAlert(String title, String message, AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
