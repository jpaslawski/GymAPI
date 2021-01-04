package com.gym.api.entity.request;

import java.time.LocalDate;

public class UserUpdateData {
    private String username;

    private LocalDate dateOfBirth;

    private Float weight;

    private Float height;

    private String gender;

    private Float exerciseLevel;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public Float getHeight() {
        return height;
    }

    public void setHeight(Float height) {
        this.height = height;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Float getExerciseLevel() {
        return exerciseLevel;
    }

    public void setExerciseLevel(Float exerciseLevel) {
        this.exerciseLevel = exerciseLevel;
    }
}
