package com.example.demo.service;

import com.example.demo.entity.Exercise;
import com.example.demo.entity.User;
import com.example.demo.entity.Workout;

import java.util.List;

public interface GymService {

    /********************* User methods *********************/

    List<User> getUsers();

    void saveUser(User user);

    User getUser(int userId);

    User getUserByEmail(String userEmail);

    void deleteUser(int userId);


    /********************* Workout methods *********************/

    List<Workout> getWorkouts();

    List<Workout> getWorkoutsByUserId(int userId);

    void saveWorkout(int userId, Workout workout);

    Workout getWorkout(int workoutId);

    void deleteWorkout(int workoutId);

    /********************* Exercise methods *********************/

    List<Exercise> getExercises();

    void saveExercise(int userId, Exercise exercise);

    void addExerciseToWorkout(int exerciseId, int workoutId);

    void addNewExerciseToWorkout(int userId, int workoutId, Exercise exercise);

    Exercise getExercise(int exerciseId);

    void deleteExercise(int exerciseId);
}
