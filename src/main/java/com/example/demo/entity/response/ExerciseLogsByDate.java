package com.example.demo.entity.response;

import com.example.demo.entity.ExerciseLog;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ExerciseLogsByDate {

    private LocalDate date;

    private Set<ExerciseLog> exerciseLogSet = new HashSet<>();

    public ExerciseLogsByDate() {

    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<ExerciseLog> getExerciseLogSet() {
        return exerciseLogSet;
    }

    public void setExerciseLogSet(Set<ExerciseLog> exerciseLogSet) {
        this.exerciseLogSet = exerciseLogSet;
    }
}
