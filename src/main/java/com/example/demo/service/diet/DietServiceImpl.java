package com.example.demo.service.diet;

import com.example.demo.dao.diet.DietDAO;
import com.example.demo.entity.Meal;
import com.example.demo.entity.MealLog;
import com.example.demo.entity.User;
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
    public List<Meal> getMeals() {
        return dietDAO.getMeals();
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
