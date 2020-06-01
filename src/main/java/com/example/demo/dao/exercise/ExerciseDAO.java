package com.example.demo.dao.exercise;

import com.example.demo.entity.Exercise;
import com.example.demo.entity.ExerciseCategory;
import com.example.demo.entity.ExerciseLog;
import com.example.demo.entity.User;

import java.util.List;
import java.util.Set;

public interface ExerciseDAO {

    /********************* Exercise methods *********************/

    List<Exercise> getExercises();

    Set<Exercise> getExercisesByWorkoutId(int workoutId);

    Set<Exercise> getExercisesByCategory(String category);

    void saveExercise(User user, Exercise exercise, String category);

    void addExerciseToWorkout(int exerciseId, int workoutId);

    void addNewExerciseToWorkout(User user, int workoutId, Exercise exercise, String category);

    Exercise getExercise(int exerciseId);

    void deleteExercise(int exerciseId);

    void deleteExerciseFromWorkout(int exerciseId, int workoutId);

    /********************* Exercise Categories methods *********************/

    ExerciseCategory getCategoryByName(String category);

    List<ExerciseCategory> getCategories();

    void addExerciseCategory(ExerciseCategory exerciseCategory);

    /********************* Exercise Logs methods *********************/

    List<ExerciseLog> getExerciseLogs(int exerciseId);
}
