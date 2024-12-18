package org.example.demo.dao.interfaces;

import org.example.demo.entity.User;

import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    boolean createUser(User user) throws SQLException;
    User loginUser(String email, String password) throws SQLException;
    List<User> getAllUsers() throws SQLException;
}
