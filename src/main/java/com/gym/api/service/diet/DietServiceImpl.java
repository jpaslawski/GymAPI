package com.gym.api.service.diet;

import com.gym.api.dao.diet.DietDAO;
import com.gym.api.entity.Meal;
import com.gym.api.entity.MealLog;
import com.gym.api.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DietServiceImpl implements DietService {

    @Autowired
    private DietDAO dietDAO;

    @Override
    @Transactional
    public List<Meal> getMeals(User user) {
        return dietDAO.getMeals(user);
    }

    @Override
    @Transactional
    public List<Meal> getPublicMeals() {
        return dietDAO.getPublicMeals();
    }

    @Override
    @Transactional
    public List<Meal> getUserPendingMeals(User user) {
        return dietDAO.getUserPendingMeals(user);
    }

    @Override
    @Transactional
    public List<Meal> getAdminPendingMeals() {
        return dietDAO.getAdminPendingMeals();
    }

    @Override
    @Transactional
    public List<Meal> getMealsAvailableForDiet(User user) {
        return dietDAO.getMealsAvailableForDiet(user);
    }

    @Override
    @Transactional
    public Meal getMeal(int mealId) {
        return dietDAO.getMeal(mealId);
    }

    @Override
    @Transactional
    public void saveMeal(Meal meal, User user) {
        dietDAO.saveMeal(meal, user);
    }

    @Override
    @Transactional
    public void deleteMeal(int mealId) {
        dietDAO.deleteMeal(mealId);
    }

    @Override
    @Transactional
    public void saveMealLog(MealLog mealLog, User user) {
        dietDAO.saveMealLog(mealLog, user);
    }

    @Override
    @Transactional
    public List<MealLog> getMealLogs(User user) {
        return dietDAO.getMealLogs(user);
    }

    @Override
    @Transactional
    public List<MealLog> getTodayMealLogs(User user) {
        return dietDAO.getTodayMealLogs(user);
    }
}