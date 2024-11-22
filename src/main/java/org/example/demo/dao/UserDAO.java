package org.example.demo.dao;

import org.example.demo.dao.interfaces.IUserDAO;
import org.example.demo.db.conn.DatabaseConnection;
import org.example.demo.entity.User;
import org.example.demo.entity.Role;
import org.mindrot.jbcrypt.BCrypt;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {

    /**
     * Creates a new user in the database with a BCrypt hashed password.
     *
     * @param user the User object containing the user's details.
     * @return true if the user was successfully created, false otherwise.
     */
    public boolean createUser(User user) {
        String sql = "INSERT INTO user (name, email, phone, address, role, password) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getAddress());
            ps.setString(5, String.valueOf(Role.PATRON));
            ps.setString(6, BCrypt.hashpw(user.getPassword(), BCrypt.gensalt()));

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Authenticates a user by verifying their email and password using BCrypt.
     *
     * @param email the user's email address.
     * @param password the user's plain-text password.
     * @return a User object if authentication is successful, or null if failed.
     */
    public User loginUser(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String hashedPassword = rs.getString("password");
                // Verify the password with BCrypt
                if (BCrypt.checkpw(password, hashedPassword)) {
                    return mapResultSetToUser(rs); // Map the result set to a User object
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Retrieves all users from the database.
     *
     * @return a list of User objects representing all users in the database.
     */
    public List<User> getAllUsers() {
        String sql = "SELECT * FROM user";
        List<User> users = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = mapResultSetToUser(rs); // Map result set to User object
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    /**
     * Maps a ResultSet row to a User object.
     *
     * @param rs the ResultSet containing user data.
     * @return a User object populated with the data from the ResultSet.
     * @throws SQLException if a database access error occurs.
     */
    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setPatronID(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setAddress(rs.getString("address"));
        user.setRole(Role.valueOf(rs.getString("role"))); // Assuming Role is an enum
        return user;
    }
}
