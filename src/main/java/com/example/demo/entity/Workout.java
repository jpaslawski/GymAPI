package com.example.demo.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE,
            CascadeType.DETACH, CascadeType.REFRESH})
    @JoinColumn(name = "workout_author")
    @JsonIgnore
    private User author;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "connectedWorkout",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Exercise> exercises;

    public Workout() {

    }

    public Workout(String name, String info, User author, boolean isPublic) {
        this.name = name;
        this.info = info;
        this.author = author;
        this.isPublic = isPublic;
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

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
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

    public void addExercise(Exercise exercise) {
        if (exercises == null) {
            exercises = new ArrayList<>();
        }

        exercises.add(exercise);
        exercise.setConnectedWorkout(this);
    }

    public void removeExercise(Exercise exercise) {
        if (exercises == null) {
            exercises = new ArrayList<>();
        }

        exercises.remove(exercise);
        exercise.setConnectedWorkout(null);
    }
}
