package com.example.demo.service.exercise;

import com.example.demo.entity.Exercise;
import com.example.demo.entity.ExerciseCategory;
import com.example.demo.entity.ExerciseLog;
import com.example.demo.entity.User;
import com.example.demo.entity.request.ExerciseLogData;

import java.util.List;
import java.util.Set;

public interface ExerciseService {

    /********************* Exercise methods *********************/

    List<Exercise> getExercises();

    Set<Exercise> getExercisesByWorkoutId(int workoutId);

    Set<Exercise> getExercisesByCategory(String category);

    void saveExercise(User user, Exercise exercise, String category);

    void addExerciseToWorkout(int exerciseId, int workoutId);

    void addNewExerciseToWorkout(User user, int workoutId, Exercise exercise, String category);

    Exercise getExercise(int exerciseId);

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
