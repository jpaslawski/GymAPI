package com.gym.api.rest.diet;

import com.gym.api.HelperMethods;
import com.gym.api.entity.*;
import com.gym.api.entity.request.MealLogData;
import com.gym.api.entity.response.MealLogsByDate;
import com.gym.api.service.diet.DietService;
import com.gym.api.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class DietRestController {

    @Autowired
    private DietService dietService;

    @Autowired
    private UserService userService;

    @GetMapping("/meals")
    public ResponseEntity<List<Meal>> getMeals(@RequestHeader (name="Authorization") String header) {
        User user = userService.getUserFromToken(header);
        if(user == null) {
            ResponseEntity.badRequest().build();
        }

        List<Meal> mealList = dietService.getMeals(user);

        if(mealList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(mealList);
    }

    @GetMapping("/meals/public")
    public ResponseEntity<List<Meal>> getPublicMeals() {
        List<Meal> mealList = dietService.getPublicMeals();

        if(mealList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(mealList);
    }

    @GetMapping("/admin/meals/pending")
    public ResponseEntity<List<Meal>> getPendingMeals(@RequestHeader (name="Authorization") String header) {
        User user = userService.getUserFromToken(header);
        if(user == null) {
            ResponseEntity.badRequest().build();
        }
        List<Meal> mealList;
        if(user.getPermissions().equals("ROLE_ADMIN")) {
            mealList = dietService.getAdminPendingMeals();
        } else {
            mealList = dietService.getUserPendingMeals(user);
        }

        if(mealList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(mealList);
    }

    @GetMapping("/meals/available")
    public ResponseEntity<List<Meal>> getAvailableMeals(@RequestHeader (name="Authorization") String header) {
        User user = userService.getUserFromToken(header);
        if(user == null) {
            ResponseEntity.badRequest().build();
        }

        List<Meal> mealList = dietService.getMealsAvailableForDiet(user);

        if(mealList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(mealList);
    }

    @GetMapping("/meals/{mealId}")
    public ResponseEntity<Meal> getMeal(@PathVariable int mealId) {
        Meal meal = dietService.getMeal(mealId);

        if(meal == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(meal);
    }

    @PostMapping("/meals")
    public ResponseEntity<Void> addMeal(@RequestHeader (name="Authorization") String header, @RequestBody Meal meal) throws URISyntaxException {
        User user = userService.getUserFromToken(header);

        meal.setId(0);
        meal.setStatus(Status.PRIVATE);
        dietService.saveMeal(meal, user);
        return ResponseEntity.created(new URI("/meals/" + meal.getId())).build();
    }

    @PutMapping("/meals/{mealId}")
    public ResponseEntity<?> updateMeal(@RequestHeader (name="Authorization") String header, @RequestBody Meal meal, @PathVariable int mealId) {
        User user = userService.getUserFromToken(header);
        Meal tempMeal = dietService.getMeal(mealId);

        if (tempMeal == null) {
            return ResponseEntity.notFound().build();
        }
        meal.setId(tempMeal.getId());
        meal.setStatus(tempMeal.getStatus());
        dietService.saveMeal(meal, user);

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/meals/{mealId}/share")
    public ResponseEntity<Meal> shareMeal(@RequestHeader (name="Authorization") String header, @PathVariable int mealId) {
        User user = userService.getUserFromToken(header);
        Meal optionalMeal = dietService.getMeal(mealId);
        if(optionalMeal == null) {
            return ResponseEntity.notFound().build();
        }

        optionalMeal.setStatus(Status.PENDING);
        dietService.saveMeal(optionalMeal, user);

        return ResponseEntity.ok(optionalMeal);
    }

    @PutMapping("/admin/meals/{mealId}/{action}")
    public ResponseEntity<Meal> acceptOrDenySharedMeal(@RequestHeader (name="Authorization") String header, @PathVariable int mealId, @PathVariable String action) {
        User user = userService.getUserFromToken(header);
        Meal optionalMeal = dietService.getMeal(mealId);
        if(optionalMeal == null) {
            return ResponseEntity.notFound().build();
        }

        if(action.equals("accept")) {
            optionalMeal.setStatus(Status.PUBLIC);
            dietService.saveMeal(optionalMeal, user);
        }
        else if(action.equals("deny")) {
            optionalMeal.setStatus(Status.PRIVATE);
            dietService.saveMeal(optionalMeal, optionalMeal.getUser());
        }

        return ResponseEntity.ok(optionalMeal);
    }

    @DeleteMapping("/meals/{mealId}")
    public ResponseEntity<?> deleteMeal(@PathVariable int mealId) {

        Meal tempMeal = dietService.getMeal(mealId);

        if (tempMeal == null) {
            return ResponseEntity.notFound().build();
        }
        dietService.deleteMeal(mealId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/mealLog/all")
    public ResponseEntity<List<MealLogsByDate>> getMealLogs(@RequestHeader (name="Authorization") String header) {
        User user = userService.getUserFromToken(header);

        List<MealLog> mealLogs = dietService.getMealLogs(user);

        if(mealLogs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        LocalDate currentDate = LocalDate.now();
        int currentDay = currentDate.getDayOfMonth();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();

        // Grouped meal set
        List<MealLogsByDate> mealLogsByDateList = new ArrayList<>();

        // Set of meals submitted the same day
        MealLogsByDate mealLogsByDate = new MealLogsByDate();
        mealLogsByDate.setDate(currentDate);
        for (MealLog mealLog : mealLogs) {

            int logDay = mealLog.getSubmitDate().getDayOfMonth();
            int logMonth= mealLog.getSubmitDate().getMonthValue();
            int logYear = mealLog.getSubmitDate().getYear();

            if(currentDay != logDay || currentMonth != logMonth || currentYear != logYear) {

                // save existing group to log set
                if (mealLogsByDate.getMealLogs().size() > 0) {
                    new HelperMethods().calculateMacroSum(mealLogsByDate);
                    mealLogsByDateList.add(mealLogsByDate);
                }

                // create new group
                mealLogsByDate = new MealLogsByDate();

                // set new date for the group
                mealLogsByDate.setDate(mealLog.getSubmitDate());

                // set new current Date
                currentDay = logDay;
                currentMonth = logMonth;
                currentYear = logYear;
            }

            mealLogsByDate.getMealLogs().add(mealLog);
        }

        // add last group of logs
        new HelperMethods(). calculateMacroSum(mealLogsByDate);
        mealLogsByDateList.add(mealLogsByDate);

        return ResponseEntity.ok(mealLogsByDateList);
    }

    @PostMapping("/mealLog")
    public ResponseEntity<Void> addMealLog(@RequestHeader (name="Authorization") String header, @RequestBody MealLogData mealLogData) throws URISyntaxException {
        User user = userService.getUserFromToken(header);

        MealLog mealLog = new MealLog();
        mealLog.setId(0);
        mealLog.setPortionCount(mealLogData.getPortionCount());
        mealLog.setReferredMeal(dietService.getMeal(mealLogData.getMealId()));
        mealLog.setSubmitDate(LocalDate.now());

        dietService.saveMealLog(mealLog, user);
        return ResponseEntity.created(new URI("/mealLog/" + mealLog.getId())).build();
    }
}