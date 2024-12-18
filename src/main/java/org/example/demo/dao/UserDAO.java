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

    public boolean createUser(User user) throws SQLException {
        String sql = "INSERT INTO Users (name, email, phone, address, role, password) VALUES (?, ?, ?, ?, ?, ?)";
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
        }
    }

    public User loginUser(String email, String password) throws SQLException {
        String sql = "SELECT * FROM Users WHERE email = ?";
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
        }
        return null;
    }

    public List<User> getAllUsers() throws SQLException {
        String sql = "SELECT * FROM Users";
        List<User> users = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                User user = mapResultSetToUser(rs); // Map result set to User object
                users.add(user);
            }
        }
        return users;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setID(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setAddress(rs.getString("address"));
        user.setRole(Role.valueOf(rs.getString("role"))); // Assuming Role is an enum
        return user;
    }
}
