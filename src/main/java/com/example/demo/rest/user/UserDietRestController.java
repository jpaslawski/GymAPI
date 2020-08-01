package com.example.demo.rest.user;

import com.example.demo.HelperMethods;
import com.example.demo.entity.MealLog;
import com.example.demo.entity.User;
import com.example.demo.entity.UserDiet;
import com.example.demo.entity.response.DailyUserInfo;
import com.example.demo.entity.response.MealLogsByDate;
import com.example.demo.service.diet.DietService;
import com.example.demo.service.user.UserService;
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
            ResponseEntity.notFound();
        }

        List<MealLog> mealLogs = dietService.getTodayMealLogs(user);
        Set<MealLog> mealLogSet = new HashSet<>(mealLogs);
        UserDiet userDiet = userService.getUserDietDetails(user);

        MealLogsByDate mealLogsByDate = new MealLogsByDate();
        mealLogsByDate.setMealLogs(mealLogSet);
        new HelperMethods().calculateMacroSum(mealLogsByDate);

        int[] macrosGoal = new HelperMethods().countTodayMacroGoal(userDiet);

        DailyUserInfo dailyUserInfo = new DailyUserInfo(
                new HelperMethods().countTotalCalories(user) - mealLogsByDate.getCaloriesSum(),
                user.getWeight(),
                (int) (mealLogsByDate.getProteinSum() * 100 / macrosGoal[0]),
                (int) (mealLogsByDate.getCarbsSum() * 100 / macrosGoal[1]),
                (int) (mealLogsByDate.getFatSum() * 100 / macrosGoal[2]));
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
