package org.example.demo.db.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static Connection mockConnection;

    static String url =  System.getenv("DB_URL");
    static String user =  System.getenv("DB_USER");
    static String password = System.getenv("DB_PASSWORD");


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
