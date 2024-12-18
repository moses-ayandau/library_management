package org.example.demo.controller;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.demo.dao.TransactionDAO;
import org.example.demo.entity.Transaction;

import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;

public class TransactionController implements Initializable {
    @FXML
    TableView<Transaction> transactionTable;
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
    TextField bookIdField;
    @FXML
    TextField patronIdField;
    @FXML
    DatePicker dueDatePicker;

    TransactionDAO transactionDAO;
    ObservableList<Transaction> transactionList;

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

        try {
            loadActiveTransactions();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    @FXML
    void handleCreateTransaction() {
        try {
            System.out.println("Creating transaction.......");
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
    void handleReturnBook() throws SQLException {
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

    void loadActiveTransactions() throws SQLException {
        transactionList.clear();
        transactionList.addAll(transactionDAO.getActiveTransactions());
        transactionTable.setItems(transactionList);
    }

    void clearFields() {
        bookIdField.clear();
        patronIdField.clear();
        dueDatePicker.setValue(null);
    }

    void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void setTransactionDAO(TransactionDAO mockTransactionDAO) {
        this.transactionDAO = mockTransactionDAO;
    }

}