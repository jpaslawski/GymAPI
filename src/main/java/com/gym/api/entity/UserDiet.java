package com.gym.api.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity(name = "UserDiet")
@Table(name="user_diet")
public class UserDiet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_diet_id")
    private int id;

    @Column(name = "user_diet_protein_percentage")
    private int proteinPercentage;

    @Column(name = "user_diet_carbs_percentage")
    private int carbsPercentage;

    @Column(name = "user_diet_fat_percentage")
    private int fatPercentage;

    @Column(name = "user_diet_calorie_diff")
    private int calorieDiff;

    @Column(name = "user_diet_total_calories")
    private double totalCalories;

    @JsonIgnore
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "user_diet_user", referencedColumnName = "user_id")
    private User user;

    public UserDiet() {
    }

    public UserDiet(int proteinPercentage, int carbsPercentage, int fatPercentage, int calorieDiff, double totalCalories, User user) {
        this.proteinPercentage = proteinPercentage;
        this.carbsPercentage = carbsPercentage;
        this.fatPercentage = fatPercentage;
        this.calorieDiff = calorieDiff;
        this.totalCalories = totalCalories;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProteinPercentage() {
        return proteinPercentage;
    }

    public void setProteinPercentage(int proteinPercentage) {
        this.proteinPercentage = proteinPercentage;
    }

    public int getCarbsPercentage() {
        return carbsPercentage;
    }

    public void setCarbsPercentage(int carbsPercentage) {
        this.carbsPercentage = carbsPercentage;
    }

    public int getFatPercentage() {
        return fatPercentage;
    }

    public void setFatPercentage(int fatPercentage) {
        this.fatPercentage = fatPercentage;
    }

    public int getCalorieDiff() {
        return calorieDiff;
    }

    public void setCalorieDiff(int calorieDiff) {
        this.calorieDiff = calorieDiff;
    }

    public double getTotalCalories() {
        return totalCalories;
    }

    public void setTotalCalories(double totalCalories) {
        this.totalCalories = totalCalories;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
