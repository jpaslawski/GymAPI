package com.example.demo.dao.user;

import com.example.demo.entity.User;

import java.util.List;

public interface UserDAO {

    User getUserFromToken(String header);

    List<User> getUsers();

    void saveUser(User user);

    User getUser(int userId);

    User getUserByEmail(String userEmail);

    void deleteUser(int userId);
}
