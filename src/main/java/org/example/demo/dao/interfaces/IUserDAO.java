package org.example.demo.dao.interfaces;

import org.example.demo.entity.User;

public interface IUserDAO {
    boolean createUser(User user);
    User loginUser(String email, String password);
}
