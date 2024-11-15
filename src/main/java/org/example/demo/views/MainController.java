package org.example.demo.views;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class MainController {

    @FXML
    private TabPane tabPane;

    @FXML
    private Tab transactionsTab;

    @FXML
    private Tab booksTab;

    @FXML
    private Tab usersTab;

    @FXML
    public void initialize() {
        loadTabContent(transactionsTab, "transactions.fxml");
        loadTabContent(booksTab, "books.fxml");
        loadTabContent(usersTab, "users.fxml");

        tabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldTab, newTab) -> {
            if (newTab == transactionsTab) {
                loadTabContent(transactionsTab, "transactions.fxml");
            } else if (newTab == booksTab) {
                loadTabContent(booksTab, "books.fxml");
            } else if (newTab == usersTab) {
                loadTabContent(usersTab, "users.fxml");
            }
        });
    }

    private void loadTabContent(Tab tab, String fxmlFile) {
        try {
            VBox content = FXMLLoader.load(getClass().getResource("/org/example/demo/"+fxmlFile));
            tab.setContent(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
