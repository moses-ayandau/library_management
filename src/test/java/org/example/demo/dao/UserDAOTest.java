package org.example.demo.dao;

import org.example.demo.dao.UserDAO;
import org.example.demo.entity.Role;
import org.example.demo.entity.User;
import org.example.demo.db.conn.DatabaseConnection;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mindrot.jbcrypt.BCrypt;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UserDAOTest {


    private UserDAO userDAO;
    private Connection mockConnection;

    @BeforeEach
    void setUp() throws SQLException {
        DatabaseConnection.configureH2ForTesting();
        mockConnection = DatabaseConnection.getConnection();

        userDAO = new UserDAO();

        try (Statement stmt = mockConnection.createStatement()) {
            stmt.execute("DROP TABLE IF EXISTS Users;");
            stmt.execute("""
                CREATE TABLE IF NOT EXISTS Users (
                    ID INT PRIMARY KEY AUTO_INCREMENT,
                    Name VARCHAR(255),
                    Email VARCHAR(255),
                    Phone VARCHAR(255),
                    Address VARCHAR(255),
                    Password VARCHAR(255),
                    Role VARCHAR(255)
                );
            """);

            stmt.execute("""
                INSERT INTO Users (Name, Email, Phone, Address, Password, Role)
                VALUES 
                ('John Doe', 'john.doe@example.com', '1234567890', '123 Main St', '$2a$10$7H7/1t5q.uDae3KqeA2BxOdDGhjJWo4hBdVOiZ1RB/7P2mYK4yAxu', 'PATRON'),
                ('Jane Doe', 'jane.doe@example.com', '0987654321', '456 Elm St', '$2a$10$7H7/1t5q.uDae3KqeA2BxOdDGhjJWo4hBdVOiZ1RB/7P2mYK4yAxu', 'ADMIN');
            """);
        }
    }

    @AfterEach
    void tearDown() throws SQLException {
        try (Statement stmt = mockConnection.createStatement()) {
            stmt.execute("DELETE FROM Users;");
        }
    }
    @Test
    public void testCreateUser_Success() throws Exception {
        User user = new User("John Smith", "john.smith@example.com", "1122334455", "789 Oak St", "password123", Role.PATRON);

        boolean result = userDAO.createUser(user);

        assertTrue(result);
    }


    @Test
    public void testLoginUser_Failure_InvalidPassword() throws Exception {
        User user = userDAO.loginUser("john.doe@example.com", "wrongpassword");

        assertNull(user);
    }

    @Test
    public void testLoginUser_Failure_UserNotFound() throws Exception {
        User user = userDAO.loginUser("nonexistent@example.com", "password123");

        assertNull(user);
    }

    @Test
    public void testGetAllUsers_EmptyResultSet() throws Exception {
        try (PreparedStatement stmt = mockConnection.prepareStatement("SELECT * FROM Users WHERE Email = 'nonexistent@example.com'")) {
            ResultSet rs = stmt.executeQuery();

            assertFalse(rs.next(), "Expected no users to be returned");
        }
    }

    @Test
    public void testGetAllUsers_Success() throws Exception {
        List<User> users = userDAO.getAllUsers();

        assertNotNull(users);
        assertEquals(2, users.size());
    }

}
