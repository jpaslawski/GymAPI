package com.gym.api.rest.user;

import com.gym.api.HelperMethods;
import com.gym.api.entity.MealLog;
import com.gym.api.entity.User;
import com.gym.api.entity.UserDiet;
import com.gym.api.entity.response.DailyUserInfo;
import com.gym.api.entity.response.MealLogsByDate;
import com.gym.api.service.diet.DietService;
import com.gym.api.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserDietRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private DietService dietService;

    @GetMapping("/users/details/dailyInfo")
    public ResponseEntity<DailyUserInfo> getDailyUserInfo(@RequestHeader(name="Authorization") String header) {
        User user = userService.getUserFromToken(header);
        if (user == null) {
            ResponseEntity.notFound().build();
        }

        DailyUserInfo dailyUserInfo;
        List<MealLog> mealLogs = dietService.getTodayMealLogs(user);
        if(mealLogs != null) {
            Set<MealLog> mealLogSet = new HashSet<>(mealLogs);
            UserDiet userDiet = userService.getUserDietDetails(user);

            MealLogsByDate mealLogsByDate = new MealLogsByDate();
            mealLogsByDate.setMealLogs(mealLogSet);
            new HelperMethods().calculateMacroSum(mealLogsByDate);

            int[] macrosGoal = new HelperMethods().countTodayMacroGoal(userDiet);

            dailyUserInfo = new DailyUserInfo(
                    new HelperMethods().countTotalCalories(user) - mealLogsByDate.getCaloriesSum(),
                    user.getWeight(),
                    (int) (mealLogsByDate.getProteinSum() * 100 / macrosGoal[0]),
                    (int) (mealLogsByDate.getCarbsSum() * 100 / macrosGoal[1]),
                    (int) (mealLogsByDate.getFatSum() * 100 / macrosGoal[2]));
        } else {
            dailyUserInfo = new DailyUserInfo(new HelperMethods().countTotalCalories(user),
                    user.getWeight(),
                    0,
                    0,
                    0);
        }

        return ResponseEntity.ok(dailyUserInfo);
    }

    @GetMapping("/users/diet")
    public ResponseEntity<UserDiet> getUserDietDetails(@RequestHeader(name="Authorization") String header) {
        User user = userService.getUserFromToken(header);
        if (user == null) {
            ResponseEntity.notFound();
        }
        UserDiet userDiet = userService.getUserDietDetails(user);

        return ResponseEntity.ok(userDiet);
    }

    @PostMapping("/users/diet")
    public ResponseEntity<UserDiet> updateUserDietDetails(@RequestHeader(name="Authorization") String header, @RequestBody UserDiet userDiet) {
        User user = userService.getUserFromToken(header);
        if (user == null) {
            ResponseEntity.notFound();
        }
        userDiet.setUser(user);
        userService.saveUserDietDetails(userDiet);

        return ResponseEntity.ok(userDiet);
    }
}
