package com.gym.api.dao.user;

import com.gym.api.entity.Preference;
import com.gym.api.entity.User;
import com.gym.api.entity.UserDiet;
import com.gym.api.entity.WeightLog;

import java.util.List;

public interface UserDAO {

    List<User> getUsers();

    User getUser(int userId);

    User getUserFromToken(String header);

    User getUserByEmail(String userEmail);

    void saveUser(User user);

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
