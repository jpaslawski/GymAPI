package com.example.demo.entity.response;

import com.example.demo.entity.Exercise;

public class DailyUserInfo {
        private Double currentCalorieBalance;

    private Float currentWeight;

    private int proteinPercentage;

    private int carbsPercentage;

    private int fatPercentage;

    public DailyUserInfo() {
    }

    public DailyUserInfo(Double currentCalorieBalance, Float currentWeight, int proteinPercentage, int carbsPercentage, int fatPercentage) {
        this.currentCalorieBalance = currentCalorieBalance;
        this.currentWeight = currentWeight;
        this.proteinPercentage = proteinPercentage;
        this.carbsPercentage = carbsPercentage;
        this.fatPercentage = fatPercentage;
    }

    public Double getCurrentCalorieBalance() {
        return currentCalorieBalance;
    }

    public void setCurrentCalorieBalance(Double currentCalorieBalance) {
        this.currentCalorieBalance = currentCalorieBalance;
    }

    public Float getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(Float currentWeight) {
        this.currentWeight = currentWeight;
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
}
