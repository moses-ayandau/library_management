package org.example.demo.dao;

import org.example.demo.dao.interfaces.IUserDAO;
import org.example.demo.db.conn.DatabaseConnection;
import org.example.demo.entity.Book;
import org.example.demo.entity.User;
import org.example.demo.entity.Role;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements IUserDAO {


    /**
     * Creates a new user in the database.
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
            ps.setString(5, user.getRole().name()); // Assuming Role is an enum
            ps.setString(6, user.getPassword());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Authenticates a user by verifying their email and password.
     *
     * @param email the user's email address.
     * @param password the user's password.
     * @return a User object if authentication is successful, or null if failed.
     */
    public User loginUser(String email, String password) {
        String sql = "SELECT * FROM user WHERE email = ? AND password = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setPatronID(rs.getInt("id"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setAddress(rs.getString("address"));
                user.setRole(Role.valueOf(rs.getString("role"))); // Assuming Role is an enum
                user.setPassword(rs.getString("password"));
                return user;
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
