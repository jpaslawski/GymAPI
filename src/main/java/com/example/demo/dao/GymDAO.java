package com.example.demo.dao;

import com.example.demo.entity.User;
import com.example.demo.entity.Workout;

import java.util.List;

public interface GymDAO {

    List<User> getUsers();

    void saveUser(User user);

    User getUser(int userId);

    User getUserByEmail(String userEmail);

    void deleteUser(int userId);

    List<Workout> getWorkouts();

    void saveWorkout(int userId, Workout workout);

    Workout getWorkout(int workoutId);

    //Workout getUserByEmail(String userEmail);

    void deleteWorkout(int workoutId);
}