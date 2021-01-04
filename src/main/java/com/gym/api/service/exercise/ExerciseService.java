package com.gym.api.service.exercise;

import com.gym.api.entity.*;
import java.util.List;
import java.util.Set;

public interface ExerciseService {

    /********************* Exercise methods *********************/

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

    void deleteExercise(int exerciseId);


    /********************* Exercise Categories methods *********************/

    ExerciseCategory getCategoryByName(String category);

    List<ExerciseCategory> getCategories();

    void addExerciseCategory(ExerciseCategory exerciseCategory);

    void deleteExerciseFromWorkout(int exerciseId, int workoutId);

    /********************* Exercise Logs methods *********************/

    List<ExerciseLog> getExerciseLogs(Exercise exercise, User user);

    void addExerciseLog(User user, ExerciseLog exerciseLog);
}
