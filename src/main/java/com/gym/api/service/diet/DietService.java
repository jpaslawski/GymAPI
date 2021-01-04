package com.gym.api.service.diet;

import com.gym.api.entity.Meal;
import com.gym.api.entity.MealLog;
import com.gym.api.entity.User;

import java.util.List;

public interface DietService {

    List<Meal> getMeals(User user);

    List<Meal> getPublicMeals();

    List<Meal> getUserPendingMeals(User user);

    List<Meal> getAdminPendingMeals();

    List<Meal> getMealsAvailableForDiet(User user);

    Meal getMeal(int mealId);

    void saveMeal(Meal meal, User user);

    void deleteMeal(int mealId);

    void saveMealLog(MealLog mealLog, User user);

    List<MealLog> getMealLogs(User user);

    List<MealLog> getTodayMealLogs(User user);
}