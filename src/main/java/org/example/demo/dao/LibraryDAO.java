package org.example.demo.dao;

import org.example.demo.entity.Book;
import org.example.demo.entity.Library;
import org.example.demo.javafx.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibraryDAO {

    private  final Library library;

    public LibraryDAO(){}

    public LibraryDAO(Library library) {
        this.library = library;
    }

    public void createLibrary(Library library) throws SQLException{
        String query = "INSERT INTO Library (name, location) VALUES (?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, library.getName());
            stmt.setString(2, library.getLocation());

            stmt.executeUpdate();
        }
    }



    }


