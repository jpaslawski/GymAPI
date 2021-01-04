package com.gym.api.entity.response;

import com.gym.api.entity.ExerciseLog;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ExerciseLogsByDate {

    private LocalDate date;

    private double averageWeight;

    private double oneRepMax;

    private Set<ExerciseLog> exerciseLogSet = new HashSet<>();

    public ExerciseLogsByDate() {

    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getAverageWeight() {
        return averageWeight;
    }

    public void setAverageWeight(double averageWeight) {
        this.averageWeight = averageWeight;
    }

    public double getOneRepMax() {
        return oneRepMax;
    }

    public void setOneRepMax(double oneRepMax) {
        this.oneRepMax = oneRepMax;
    }

    public Set<ExerciseLog> getExerciseLogSet() {
        return exerciseLogSet;
    }

    public void setExerciseLogSet(Set<ExerciseLog> exerciseLogSet) {
        this.exerciseLogSet = exerciseLogSet;
    }
}
