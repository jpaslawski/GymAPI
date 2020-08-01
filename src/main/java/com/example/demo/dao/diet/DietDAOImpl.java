package com.example.demo.dao.diet;

import com.example.demo.entity.Meal;
import com.example.demo.entity.MealLog;
import com.example.demo.entity.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public class DietDAOImpl implements DietDAO {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public List<Meal> getMeals() {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<Meal> theQuery =
                currentSession.createQuery("FROM Meal", Meal.class);

        return theQuery.getResultList();
    }

    @Override
    public Meal getMeal(int mealId) {
        Meal meal = sessionFactory.getCurrentSession().find(Meal.class, mealId);
        return meal;
    }

    @Override
    public void saveMeal(Meal meal, User user) {
        Session currentSession = sessionFactory.getCurrentSession();

        user.addMeal(meal);
        currentSession.saveOrUpdate(meal);
    }

    @Override
    public void deleteMeal(int mealId) {
        Session currentSession = sessionFactory.getCurrentSession();

        Query theQuery =
                currentSession.createQuery("DELETE FROM Meal WHERE id=:mealId");
        theQuery.setParameter("mealId", mealId);

        theQuery.executeUpdate();
    }

    @Override
    public void saveMealLog(MealLog mealLog, User user) {
        Session currentSession = sessionFactory.getCurrentSession();

        user.addMealLog(mealLog);
        currentSession.saveOrUpdate(mealLog);
    }

    @Override
    public List<MealLog> getMealLogs(User user) {
        Session currentSession = sessionFactory.getCurrentSession();

        Query<MealLog> theQuery =
                currentSession.createQuery("FROM MealLog WHERE user=:user ORDER BY id DESC", MealLog.class);
        theQuery.setParameter("user", user);

        return theQuery.getResultList();
    }

    @Override
    public List<MealLog> getTodayMealLogs(User user) {
        Session currentSession = sessionFactory.getCurrentSession();
        LocalDate today = LocalDate.now();

        Query<MealLog> theQuery =
                currentSession.createQuery("FROM MealLog WHERE user=:user AND submitDate=:today ORDER BY id DESC", MealLog.class);
        theQuery.setParameter("user", user);
        theQuery.setParameter("today", today);

        return theQuery.getResultList();
    }
}
