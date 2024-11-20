package org.example.demo.dao.interfaces;

import org.example.demo.entity.User;

import java.util.List;

public interface IUserDAO {
    boolean createUser(User user);
    User loginUser(String email, String password);
    List<User> getAllUsers();
}
