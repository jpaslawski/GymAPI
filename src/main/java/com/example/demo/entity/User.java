package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "User")
@Table(name="user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int id;

    @Column(name = "user_name")
    private String username;

    @Column(name ="user_email")
    private String email;

    @Column(name = "user_password")
    private String password;

    @Column(name = "user_weight")
    private Float weight;

    @Column(name = "user_height")
    private Float height;

    @Column(name = "user_age")
    private Integer age;

    @Column(name = "user_permissions")
    private String permissions;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "author",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    private List<Workout> workouts;

    @OneToMany(fetch = FetchType.EAGER,
            mappedBy = "author",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH})
    @Fetch(value = FetchMode.SUBSELECT)
    private List<Exercise> exercises;

    public User() {

    }

    public User(String username, String email, String password, Float weight, Float height, Integer age, String permissions) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.weight = weight;
        this.height = height;
        this.age = age;
        this.permissions = permissions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public boolean isValid() {
        return username != null && email != null && password != null && weight != null && height != null && age != null && permissions != null;
    }

    public List<Workout> getWorkouts() {
        return workouts;
    }

    public void setWorkouts(List<Workout> workouts) {
        this.workouts = workouts;
    }

    public List<Exercise> getExercises() {
        return exercises;
    }

    public void setExercises(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    public void addWorkout(Workout workout) {
        if (workouts == null) {
            workouts = new ArrayList<>();
        }

        workouts.add(workout);
        workout.setAuthor(this);
    }

    public void removeWorkout(Workout workout) {
        if (workouts == null) {
            workouts = new ArrayList<>();
        }

        workouts.remove(workout);
        workout.setAuthor(null);
    }

    public void addExercise(Exercise exercise) {
        if (exercises == null) {
            exercises = new ArrayList<>();
        }

        exercises.add(exercise);
        exercise.setAuthor(this);
    }

    public void removeExercise(Exercise exercise) {
        if (exercises == null) {
            exercises = new ArrayList<>();
        }

        exercises.remove(exercise);
        exercise.setAuthor(null);
    }
}