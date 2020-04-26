package com.example.demo.dao;

import com.example.demo.entity.Exercise;
import com.example.demo.entity.ExerciseCategory;
import com.example.demo.entity.User;
import com.example.demo.entity.Workout;

import java.util.List;
import java.util.Set;

public interface GymDAO {

    String getEmailFromToken(String header);

    /********************* User methods *********************/

    List<User> getUsers();

    void saveUser(User user);

    User getUser(int userId);

    User getUserByEmail(String userEmail);

    void deleteUser(int userId);

    /********************* Workout methods *********************/

    List<Workout> getWorkouts();

    List<Workout> getWorkoutsByUserId(int userId);

    void saveWorkout(String email, Workout workout);

    Workout getWorkout(int workoutId);

    void deleteWorkout(int workoutId);

    /********************* Exercise methods *********************/

    List<Exercise> getExercises();

    Set<Exercise> getExercisesByWorkoutId(int workoutId);

    void saveExercise(String email, Exercise exercise, String category);

    void addExerciseToWorkout(int exerciseId, int workoutId);

    void addNewExerciseToWorkout(int userId, int workoutId, Exercise exercise);

    Exercise getExercise(int exerciseId);

    void deleteExercise(int exerciseId);

    void deleteExerciseFromWorkout(int exerciseId, int workoutId);

    /********************* Exercise Categories methods *********************/

    List<ExerciseCategory> getCategories();

    void addExerciseCategory(ExerciseCategory exerciseCategory);
}