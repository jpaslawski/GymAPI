package com.gym.api.service.user;

import com.gym.api.dao.user.UserDAO;
import com.gym.api.entity.Preference;
import com.gym.api.entity.User;
import com.gym.api.entity.UserDiet;
import com.gym.api.entity.WeightLog;
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
    public User getUser(int userId) {
        return userDAO.getUser(userId);
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        userDAO.saveUser(user);
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
    public boolean isWeightCheckedToday(User user) {
        return userDAO.isWeightCheckedToday(user);
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

    @Override
    @Transactional
    public Preference getUserPreferences(User user) {
        return userDAO.getUserPreferences(user);
    }

    @Override
    @Transactional
    public void saveUserPreferences(Preference preference) {
        userDAO.saveUserPreferences(preference);
    }
}
