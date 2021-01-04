package com.gym.api;

import com.gym.api.entity.MealLog;
import com.gym.api.entity.User;
import com.gym.api.entity.UserDiet;
import com.gym.api.entity.response.MealLogsByDate;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class HelperMethods {

    public void calculateMacroSum(MealLogsByDate mealLogs) {
        float caloriesSum = 0, proteinSum = 0, carbsSum = 0, fatSum = 0;

        for(MealLog mealLog : mealLogs.getMealLogs()) {
            caloriesSum += mealLog.getReferredMeal().getCalories() * mealLog.getPortionCount();
            proteinSum += mealLog.getReferredMeal().getProtein() * mealLog.getPortionCount();
            carbsSum += mealLog.getReferredMeal().getCarbs() * mealLog.getPortionCount();
            fatSum += mealLog.getReferredMeal().getFat() * mealLog.getPortionCount();
        }

        mealLogs.setCaloriesSum(caloriesSum);
        mealLogs.setProteinSum(proteinSum);
        mealLogs.setCarbsSum(carbsSum);
        mealLogs.setFatSum(fatSum);
    }

    public Double countTotalCalories(User user) {
        LocalDate currentDate = LocalDate.now();
        long userAge = ChronoUnit.YEARS.between( user.getDateOfBirth(), currentDate);
        if(user.getWeight() == 0.0 || user.getHeight() == 0.0) {
            return 0.0;
        }
        double BMR = 66.4730 + (13.7516 * user.getWeight()) + (5.0033 * user.getHeight()) - (6.7550 * userAge);
        return (double) Math.round(BMR * user.getExerciseLevel() * 1.00);
    }

    public int[] countTodayMacroGoal(UserDiet userDiet) {
        int[] macros = new int[3];

        macros[0] = (int) (userDiet.getTotalCalories() * userDiet.getProteinPercentage() / 100) / 4;
        macros[1] = (int) (userDiet.getTotalCalories() * userDiet.getCarbsPercentage() / 100) / 4;
        macros[2] = (int) (userDiet.getTotalCalories() * userDiet.getFatPercentage() / 100) / 9;

        return macros;
    }
}
