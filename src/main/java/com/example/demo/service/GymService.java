package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.entity.Workout;

import java.util.List;

public interface GymService {

    List<User> getUsers();

    void saveUser(User user);

    User getUser(int userId);

    User getUserByEmail(String userEmail);

    void deleteUser(int userId);

    List<Workout> getWorkouts();

    void saveWorkout(Workout workout);

    Workout getWorkout(int workoutId);

    //User getUserByEmail(String userEmail);

    void deleteWorkout(int workoutId);
}
