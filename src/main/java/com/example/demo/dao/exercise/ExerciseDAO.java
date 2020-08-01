package com.example.demo.dao.exercise;

import com.example.demo.entity.*;

import java.util.List;
import java.util.Set;

public interface ExerciseDAO {

    public List<Exercise> getExercises();

    public Set<Exercise> getExercisesByWorkoutId(int workoutId);

    public Set<Exercise> getExercisesByCategory(String category);

    public void saveExercise(User user, Exercise exercise, String category);

    public void addExerciseToWorkout(Exercise exercise, Workout workout);

    public void addNewExerciseToWorkout(User user, Workout workout, Exercise exercise, String category);

    public Exercise getExercise(int exerciseId);

    public Exercise getLastExercise(User user);

    public void deleteExercise(int exerciseId) ;

    public void deleteExerciseFromWorkout(int exerciseId, int workoutId);

    public ExerciseCategory getCategoryByName(String category);

    public List<ExerciseCategory> getCategories();

    public void addExerciseCategory(ExerciseCategory exerciseCategory);

    public List<ExerciseLog> getExerciseLogs(Exercise exercise, User user);

    public void addExerciseLog(User user, ExerciseLog exerciseLog);
}

