package com.gym.api.service.user;

import com.gym.api.entity.Preference;
import com.gym.api.entity.User;
import com.gym.api.entity.UserDiet;
import com.gym.api.entity.WeightLog;

import java.util.List;

public interface UserService {

    User getUserFromToken(String header);

    List<User> getUsers();

    User getUser(int userId);

    void saveUser(User user);

    User getUserByEmail(String userEmail);

    void deleteUser(int userId);

    List<WeightLog> getWeightLogs(User user);

    WeightLog getCurrentWeight(User user);

    boolean isWeightCheckedToday(User user);

    void saveWeightLog(WeightLog weightLog, User user);

    UserDiet getUserDietDetails(User user);

    void saveUserDietDetails(UserDiet userDiet);

    Preference getUserPreferences(User user);

    void saveUserPreferences(Preference preference);
}
