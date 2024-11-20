package org.example.demo.views;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.demo.dao.TransactionDAO;
import org.example.demo.entity.Transaction;

import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {
    @FXML
    private TableView<Transaction> transactionTable;
    @FXML
    private TableColumn<Transaction, Integer> idColumn;
    @FXML
    private TableColumn<Transaction, Integer> bookIdColumn;
    @FXML
    private TableColumn<Transaction, Integer> patronIdColumn;
    @FXML
    private TableColumn<Transaction, Date> borrowedDateColumn;
    @FXML
    private TableColumn<Transaction, Date> dueDateColumn;

    @FXML
    private TextField bookIdField;
    @FXML
    private TextField patronIdField;
    @FXML
    private DatePicker dueDatePicker;

    private TransactionDAO transactionDAO;
    private ObservableList<Transaction> transactionList;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        transactionDAO = new TransactionDAO();
        transactionList = FXCollections.observableArrayList();

        // Initialize table columns
        idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookID"));
        patronIdColumn.setCellValueFactory(new PropertyValueFactory<>("patronID"));
        borrowedDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedDate"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));

        loadActiveTransactions();
    }

    @FXML
    private void handleCreateTransaction() {
        try {
            Transaction transaction = new Transaction();
            transaction.setBookID(Integer.parseInt(bookIdField.getText()));
            transaction.setPatronID(Integer.parseInt(patronIdField.getText()));
            transaction.setBorrowedDate((java.sql.Date) new Date());
            transaction.setDueDate(java.sql.Date.valueOf(dueDatePicker.getValue()));

            if (transactionDAO.createTransaction(transaction)) {
                loadActiveTransactions();
                clearFields();
                showAlert("Success", "Transaction created successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to create transaction.", Alert.AlertType.ERROR);
            }
        } catch (Exception e) {
            showAlert("Error", "Invalid input. Please check your entries.", Alert.AlertType.ERROR);
        }
    }

    @FXML
    private void handleReturnBook() {
        Transaction selectedTransaction = transactionTable.getSelectionModel().getSelectedItem();
        if (selectedTransaction != null) {
            if (transactionDAO.returnBook(selectedTransaction.getID(), (java.sql.Date) new Date())) {
                loadActiveTransactions();
                showAlert("Success", "Book returned successfully!", Alert.AlertType.INFORMATION);
            } else {
                showAlert("Error", "Failed to return book.", Alert.AlertType.ERROR);
            }
        } else {
            showAlert("Error", "Please select a transaction.", Alert.AlertType.WARNING);
        }
    }

    private void loadActiveTransactions() {
        transactionList.clear();
        transactionList.addAll(transactionDAO.getActiveTransactions());
        transactionTable.setItems(transactionList);
    }

    private void clearFields() {
        bookIdField.clear();
        patronIdField.clear();
        dueDatePicker.setValue(null);
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}