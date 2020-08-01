package com.example.demo.service.user;

import com.example.demo.dao.user.UserDAO;
import com.example.demo.entity.User;
import com.example.demo.entity.UserDiet;
import com.example.demo.entity.WeightLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    @Transactional
    public User getUserFromToken(String header) {
        return userDAO.getUserFromToken(header);
    }

    @Override
    @Transactional
    public List<User> getUsers() {
        return userDAO.getUsers();
    }

    @Override
    @Transactional
    public void saveUser(User user, UserDiet userDiet) {
        userDAO.saveUser(user, userDiet);
    }

    @Override
    @Transactional
    public User getUserByEmail(String userEmail) {
        return userDAO.getUserByEmail(userEmail);
    }

    @Override
    @Transactional
    public void deleteUser(int userId) {
        userDAO.deleteUser(userId);
    }

    @Override
    @Transactional
    public List<WeightLog> getWeightLogs(User user) {
        return userDAO.getWeightLogs(user);
    }

    @Override
    @Transactional
    public WeightLog getCurrentWeight(User user) {
        return userDAO.getCurrentWeight(user);
    }

    @Override
    @Transactional
    public boolean checkTodayWeightLog(User user) {
        return userDAO.checkTodayWeightLog(user);
    }

    @Override
    @Transactional
    public void saveWeightLog(WeightLog weightLog, User user) {
        userDAO.saveWeightLog(weightLog, user);
    }

    @Override
    @Transactional
    public UserDiet getUserDietDetails(User user) {
        return userDAO.getUserDietDetails(user);
    }

    @Override
    @Transactional
    public void saveUserDietDetails(UserDiet userDiet) {
        userDAO.saveUserDietDetails(userDiet);
    }
}
