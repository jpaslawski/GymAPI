package com.gym.api.entity.response;

import com.gym.api.entity.MealLog;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class MealLogsByDate {

    private LocalDate date;

    private float caloriesSum;

    private float proteinSum;

    private float carbsSum;

    private float fatSum;

    private Set<MealLog> mealLogs = new HashSet<>();

    public MealLogsByDate() {
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public float getCaloriesSum() {
        return caloriesSum;
    }

    public void setCaloriesSum(float caloriesSum) {
        this.caloriesSum = caloriesSum;
    }

    public float getProteinSum() {
        return proteinSum;
    }

    public void setProteinSum(float proteinSum) {
        this.proteinSum = proteinSum;
    }

    public float getCarbsSum() {
        return carbsSum;
    }

    public void setCarbsSum(float carbsSum) {
        this.carbsSum = carbsSum;
    }

    public float getFatSum() {
        return fatSum;
    }

    public void setFatSum(float fatSum) {
        this.fatSum = fatSum;
    }

    public Set<MealLog> getMealLogs() {
        return mealLogs;
    }

    public void setMealLogs(Set<MealLog> mealLogs) {
        this.mealLogs = mealLogs;
    }
}
