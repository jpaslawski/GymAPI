package com.example.demo.service.user;

import com.example.demo.entity.User;
import com.example.demo.entity.UserDiet;
import com.example.demo.entity.WeightLog;

import java.util.List;

public interface UserService {

    User getUserFromToken(String header);

    List<User> getUsers();

    void saveUser(User user, UserDiet userDiet);

    User getUserByEmail(String userEmail);

    void deleteUser(int userId);

    List<WeightLog> getWeightLogs(User user);

    WeightLog getCurrentWeight(User user);

    boolean checkTodayWeightLog(User user);

    void saveWeightLog(WeightLog weightLog, User user);

    UserDiet getUserDietDetails(User user);

    void saveUserDietDetails(UserDiet userDiet);
}
