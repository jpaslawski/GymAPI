package com.example.demo.dao.diet;

import com.example.demo.entity.Meal;
import com.example.demo.entity.MealLog;
import com.example.demo.entity.User;

import java.util.List;

public interface DietDAO {

    List<Meal> getMeals(User user);

    List<Meal> getPublicMeals();

    List<Meal> getPendingMeals();

    Meal getMeal(int mealId);

    void saveMeal(Meal meal, User user);

    void deleteMeal(int mealId);

    void saveMealLog(MealLog mealLog, User user);

    List<MealLog> getMealLogs(User user);

    List<MealLog> getTodayMealLogs(User user);
}
