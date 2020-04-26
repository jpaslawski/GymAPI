package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity(name = "Workout")
@Table(name="workout")
public class Workout {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "workout_id")
    private int id;

    @Column(name = "workout_name")
    private String name;

    @Column(name = "workout_info")
    private String info;

    @Column(name = "workout_isPublic")
    private boolean isPublic;

    @Column(name ="workout_exerciseAmount")
    private int exerciseAmount;

    @JsonIgnore
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "workout_author")
    private User author;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name="workout_exercise",
            joinColumns = { @JoinColumn(name="workout_id")},
            inverseJoinColumns = { @JoinColumn(name ="exercise_id")}
    )
    private Set<Exercise> exercises = new HashSet<>();

    public Workout() {

    }

    public Workout(String name, String info, User author, boolean isPublic, int exerciseAmount) {
        this.name = name;
        this.info = info;
        this.author = author;
        this.isPublic = isPublic;
        this.exerciseAmount = exerciseAmount;
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

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    public void setExercises(Set<Exercise> exercises) {
        this.exercises = exercises;
    }

    public int getExerciseAmount() {
        return exerciseAmount;
    }

    public void setExerciseAmount(int exerciseAmount) {
        this.exerciseAmount = exerciseAmount;
    }

    public void addExercise(Exercise exercise) {
        this.exercises.add(exercise);
        exercise.getWorkouts().add(this);
    }

    public void removeExercise(Exercise exercise) {
        this.exercises.remove(exercise);
        exercise.getWorkouts().remove(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Workout )) return false;
        return false;
    }

    @Override
    public int hashCode() {
        return 31;
    }

    public Set<Exercise> getExercises() {
        return exercises;
    }
}
