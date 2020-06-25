package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "ExerciseLog")
@Table(name = "exercise_log")
public class ExerciseLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "exercise_log_id")
    private int id;

    @Column(name = "exercise_log_date")
    private LocalDate submitDate;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "exercise_log_trainee")
    private User trainee;

    @Column(name = "exercise_log_weight")
    private float weight;

    @Column(name = "exercise_log_reps")
    private int reps;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "exercise_log_reference")
    private Exercise referredExercise;

    public ExerciseLog() {

    }

    public ExerciseLog(float weight, int reps) {
        this.weight = weight;
        this.reps = reps;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getSubmitDate() {
        return submitDate;
    }

    public void setSubmitDate(LocalDate submitDate) {
        this.submitDate = submitDate;
    }

    public User getTrainee() {
        return trainee;
    }

    public void setTrainee(User trainee) {
        this.trainee = trainee;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public Exercise getReferredExercise() {
        return referredExercise;
    }

    public void setReferredExercise(Exercise referredExercise) {
        this.referredExercise = referredExercise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ExerciseLog )) return false;
        return false;
    }

    @Override
    public int hashCode() {
        return 31;
    }
}
