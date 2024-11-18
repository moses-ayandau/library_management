package org.example.demo.views;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.demo.dao.JournalDAO;
import org.example.demo.entity.Journal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class JournalController {

    private JournalDAO journalDAO;

    @FXML
    private TextField idField, titleField, publishedYearField, issnField, publisherField;
    @FXML
    private Button addButton, updateButton, deleteButton, clearButton;
    @FXML
    private TableView<Journal> journalTable;
    @FXML
    private TableColumn<Journal, Integer> idColumn;
    @FXML
    private TableColumn<Journal, String> titleColumn, issnColumn, publisherColumn;

    public void initialize() {
        try {
            // Setup database connection and DAO
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "password");
            journalDAO = new JournalDAO(connection);

            // Initialize table columns
            idColumn.setCellValueFactory(new PropertyValueFactory<>("ID"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            issnColumn.setCellValueFactory(new PropertyValueFactory<>("issn"));
            publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));

            loadJournals();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Load journals from the database into the TableView
    private void loadJournals() throws SQLException {
        List<Journal> journals = journalDAO.getAllJournals();
        ObservableList<Journal> journalList = FXCollections.observableArrayList(journals);
        journalTable.setItems(journalList);
    }

    // Add a journal
    @FXML
    private void addJournal() {
        try {
            int id = Integer.parseInt(idField.getText());
            String title = titleField.getText();
            int publishedYear = Integer.parseInt(publishedYearField.getText());
            String issn = issnField.getText();
            String publisher = publisherField.getText();

            Journal journal = new Journal();
            journal.setID(id);
            journal.setTitle(title);
            journal.setPublishedYear(publishedYear);
            journal.setIssn(issn);
            journal.setPublisher(publisher);

            journalDAO.addJournal(journal);
            loadJournals();
            clearFields();
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // Update an existing journal
    @FXML
    private void updateJournal() {
        try {
            int id = Integer.parseInt(idField.getText());
            String title = titleField.getText();
            int publishedYear = Integer.parseInt(publishedYearField.getText());
            String issn = issnField.getText();
            String publisher = publisherField.getText();

            Journal journal = new Journal();
            journal.setID(id);
            journal.setTitle(title);
            journal.setPublishedYear(publishedYear);
            journal.setIssn(issn);
            journal.setPublisher(publisher);

            journalDAO.updateJournal(journal);
            loadJournals();
            clearFields();
        } catch (SQLException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    // Delete a selected journal
    @FXML
    private void deleteJournal() {
        Journal selectedJournal = journalTable.getSelectionModel().getSelectedItem();
        if (selectedJournal != null) {
            try {
                journalDAO.deleteJournal(selectedJournal.getID());
                loadJournals();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    // Clear input fields
    @FXML
    private void clearFields() {
        idField.clear();
        titleField.clear();
        publishedYearField.clear();
        issnField.clear();
        publisherField.clear();
    }
}
