package com.gym.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.List;

@Entity(name = "Meal")
@Table(name="meal")
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "meal_id")
    private int id;

    @Column(name = "meal_name")
    private String name;

    @Column(name = "meal_name_pl")
    private String namePL;

    @Column(name = "meal_calories")
    private float calories;

    @Column(name = "meal_protein")
    private float protein;

    @Column(name = "meal_carbs")
    private float carbs;

    @Column(name = "meal_fat")
    private float fat;

    @Column(name = "meal_portion_weight")
    private float portionWeight;

    @Column(name = "meal_status")
    private Status status;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "meal_user")
    private User user;

    @JsonIgnore
    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "referredMeal",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<MealLog> mealLogs;

    public Meal() {
    }

    public Meal(String name, String namePL, float calories, float protein, float carbs, float fat, float portionWeight, Status status) {
        this.name = name;
        this.namePL = namePL;
        this.calories = calories;
        this.protein = protein;
        this.carbs = carbs;
        this.fat = fat;
        this.portionWeight = portionWeight;
        this.status = status;
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

    public String getNamePL() {
        return namePL;
    }

    public void setNamePL(String namePL) {
        this.namePL = namePL;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<MealLog> getMealLogs() {
        return mealLogs;
    }

    public void setMealLogs(List<MealLog> mealLogs) {
        this.mealLogs = mealLogs;
    }
}
