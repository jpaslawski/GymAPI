package com.example.demo.dao.exercise;

import com.example.demo.entity.*;

import java.util.List;
import java.util.Set;

public interface ExerciseDAO {

    List<Exercise> getExercises();

    Set<Exercise> getExercisesByWorkoutId(int workoutId);

    Set<Exercise> getExercisesByCategory(String category);

    void saveExercise(User user, Exercise exercise, String category);

    void updateExercise(Exercise exercise, String category);

    void addExerciseToWorkout(Exercise exercise, Workout workout);

    void addNewExerciseToWorkout(User user, Workout workout, Exercise exercise, String category);

    Exercise getExercise(int exerciseId);

    Exercise getLastExercise(User user);

    void deleteExercise(int exerciseId) ;

    void deleteExerciseFromWorkout(int exerciseId, int workoutId);

    ExerciseCategory getCategoryByName(String category);

    List<ExerciseCategory> getCategories();

    void addExerciseCategory(ExerciseCategory exerciseCategory);

    List<ExerciseLog> getExerciseLogs(Exercise exercise, User user);

    void addExerciseLog(User user, ExerciseLog exerciseLog);
}

