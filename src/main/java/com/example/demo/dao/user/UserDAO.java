package com.example.demo.dao.user;

import com.example.demo.entity.User;
import com.example.demo.entity.UserDiet;
import com.example.demo.entity.WeightLog;

import java.util.List;

public interface UserDAO {

    List<User> getUsers();

    User getUserFromToken(String header);

    User getUserByEmail(String userEmail);

    void saveUser(User user, UserDiet userDiet);

    void deleteUser(int userId);

    List<WeightLog> getWeightLogs(User user);

    WeightLog getCurrentWeight(User user);

    boolean checkTodayWeightLog(User user);

    void saveWeightLog(WeightLog weightLog, User user);

    UserDiet getUserDietDetails(User user);

    void saveUserDietDetails(UserDiet userDiet);
}
