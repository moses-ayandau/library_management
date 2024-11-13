package org.example.demo.views;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class LibraryViewController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField locationField;
    @FXML
    private final LibraryDAO libraryDAO = new LibraryDAO();
    @FXML
    public void createLibrary(){
        try{
            String name = nameField.getText();
            String location = locationField.getText();
            Library library = new Library(name, location);
            libraryDAO.createLibrary(library);
        }
        catch (Exception e){

        }
    }
}
