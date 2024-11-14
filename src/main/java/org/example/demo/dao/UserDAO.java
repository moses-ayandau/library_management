package org.example.demo.dao;


import org.example.demo.entity.Book;
import org.example.demo.entity.User;
import org.example.demo.entity.Role;
import org.example.demo.db.conn.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    public boolean createUser(User user) {
        String sql = "INSERT INTO user (name, email, phone, address, role) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPhone());
            pstmt.setString(4, user.getAddress());
            pstmt.setString(5, user.getRole().toString());

            int affectedRows = pstmt.executeUpdate();

            if (affectedRows > 0) {
                ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setPatronID(generatedKeys.getInt(1));
                }
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get all users
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = mapResultSetToUser(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    // Get user with their reservations
    public User getUserWithReservations(int userId) {
        String userSql = "SELECT * FROM user WHERE id = ?";
        String reservationSql = "SELECT b.* FROM book b " +
                "JOIN reservation r ON b.id = r.book_id " +
                "WHERE r.user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement userStmt = conn.prepareStatement(userSql);
             PreparedStatement reservationStmt = conn.prepareStatement(reservationSql)) {

            userStmt.setInt(1, userId);
            ResultSet userRs = userStmt.executeQuery();

            if (userRs.next()) {
                User user = mapResultSetToUser(userRs);

                // Get user's reserved books
                reservationStmt.setInt(1, userId);
                ResultSet bookRs = reservationStmt.executeQuery();

                while (bookRs.next()) {
                    Book book = new Book();
                    book.setID(bookRs.getInt("id"));
                    book.setTitle(bookRs.getString("title"));
                    // Set other book properties
                    user.getBooks().add(book);
                }

                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Update user
    public boolean updateUser(User user) {
        String sql = "UPDATE user SET name = ?, email = ?, phone = ?, address = ?, role = ? WHERE id = ?";

        try (Connection conn =  DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPhone());
            pstmt.setString(4, user.getAddress());
            pstmt.setString(5, user.getRole().toString());
            pstmt.setInt(6, user.getPatronID());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Delete user
    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM user WHERE id = ?";

        try (Connection conn =  DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private User mapResultSetToUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setPatronID(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setPhone(rs.getString("phone"));
        user.setAddress(rs.getString("address"));
        user.setRole(Role.valueOf(rs.getString("role")));
        return user;
    }
}