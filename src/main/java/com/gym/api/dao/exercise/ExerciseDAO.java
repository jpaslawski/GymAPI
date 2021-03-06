package com.gym.api.dao.exercise;

import com.gym.api.entity.*;
import java.util.List;
import java.util.Set;

public interface ExerciseDAO {

    List<Exercise> getExercises(User user);

    List<Exercise> getPublicExercises();

    List<Exercise> getUserPendingExercises(User user);

    List<Exercise> getAdminPendingExercises();

    Set<Exercise> getExercisesByWorkoutId(int workoutId);

    Set<Exercise> getExercisesByCategory(User user, String category);

    void saveExercise(User user, Exercise exercise);

    void updateExercise(Exercise exercise, String category);

    void addExistingExerciseToWorkout(Exercise exercise, Workout workout);

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

