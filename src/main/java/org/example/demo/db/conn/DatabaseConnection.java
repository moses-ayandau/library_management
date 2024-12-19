package org.example.demo.db.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection mockConnection;

    // Default production database
    static String url = "jdbc:mysql://localhost:3306/LibraryManagement";
    static String user = "root";
    static String password = "#moses21311 *";

    /**
     * Retrieves a connection to the database.
     * If a mock connection is set, it will return the mock connection.
     *
     * @return a Connection instance.
     * @throws SQLException if a database access error occurs.
     */
    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(url, user, password);
    }

    public static void configureH2ForTesting() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        url = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1";
        user = "sa";
        password = "";
    }

}
