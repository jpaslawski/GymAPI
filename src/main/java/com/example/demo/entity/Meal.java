package com.example.demo.entity;

public class Meal {

    private int id;

    private String name;

    private float calories;

    private float protein;

    private float carbs;

    private float fat;

    private float portionWeight;

    private User user;

    public Meal() {
    }

    public Meal(String name, float calories, float protein, float carbs, float fat, float portionWeight) {
        this.name = name;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.portionWeight = portionWeight;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getCalories() {
        return calories;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public float getProtein() {
        return protein;
    }

    public void setProtein(float protein) {
        this.protein = protein;
    }

    public float getCarbs() {
        return carbs;
    }

    public void setCarbs(float carbs) {
        this.carbs = carbs;
    }

    public float getFat() {
        return fat;
    }

    public void setFat(float fat) {
        this.fat = fat;
    }

    public float getPortionWeight() {
        return portionWeight;
    }

    public void setPortionWeight(float portionWeight) {
        this.portionWeight = portionWeight;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
