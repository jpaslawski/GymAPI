package com.gym.api.service.exercise;

import com.gym.api.dao.exercise.ExerciseDAO;
import com.gym.api.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class ExerciseServiceImpl implements ExerciseService {

    @Autowired
    private ExerciseDAO exerciseDAO;

    @Override
    @Transactional
    public List<Exercise> getExercises(User user) {
        return exerciseDAO.getExercises(user);
    }

    @Override
    @Transactional
    public List<Exercise> getPublicExercises() {
        return exerciseDAO.getPublicExercises();
    }

    @Override
    @Transactional
    public List<Exercise> getUserPendingExercises(User user) {
        return exerciseDAO.getUserPendingExercises(user);
    }

    @Override
    @Transactional
    public List<Exercise> getAdminPendingExercises() {
        return exerciseDAO.getAdminPendingExercises();
    }

    @Override
    @Transactional
    public Set<Exercise> getExercisesByWorkoutId(int workoutId) {
        return exerciseDAO.getExercisesByWorkoutId(workoutId);
    }

    @Override
    @Transactional
    public Set<Exercise> getExercisesByCategory(User user, String category) {
        return exerciseDAO.getExercisesByCategory(user, category);
    }

    @Override
    @Transactional
    public void saveExercise(User user, Exercise exercise) {
        exerciseDAO.saveExercise(user, exercise);
    }

    @Override
    @Transactional
    public void updateExercise(Exercise exercise, String category) {
        exerciseDAO.updateExercise(exercise, category);
    }

    @Override
    @Transactional
    public void addExistingExerciseToWorkout(Exercise exercise, Workout workout) {
        exerciseDAO.addExistingExerciseToWorkout(exercise, workout);
    }

    @Override
    @Transactional
    public void addNewExerciseToWorkout(User user, Workout workout, Exercise exercise, String category) {
        exerciseDAO.addNewExerciseToWorkout(user, workout, exercise, category);
    }

    @Override
    @Transactional
    public void addExerciseCategory(ExerciseCategory exerciseCategory) {
        exerciseDAO.addExerciseCategory(exerciseCategory);
    }

    @Override
    @Transactional
    public void deleteExerciseFromWorkout(int exerciseId, int workoutId) {
        exerciseDAO.deleteExerciseFromWorkout(exerciseId, workoutId);
    }

    @Override
    @Transactional
    public List<ExerciseLog> getExerciseLogs(Exercise exercise, User user) {
        return exerciseDAO.getExerciseLogs(exercise, user);
    }

    @Override
    @Transactional
    public Exercise getLastExercise(User user) {
        return exerciseDAO.getLastExercise(user);
    }

    @Override
    @Transactional
    public void addExerciseLog(User user, ExerciseLog exerciseLog) {
        exerciseDAO.addExerciseLog(user, exerciseLog);
    }

    @Override
    @Transactional
    public Exercise getExercise(int exerciseId) {
        return exerciseDAO.getExercise(exerciseId);
    }

    @Override
    @Transactional
    public void deleteExercise(int exerciseId) {
        exerciseDAO.deleteExercise(exerciseId);
    }

    @Override
    @Transactional
    public ExerciseCategory getCategoryByName(String category) {
        return exerciseDAO.getCategoryByName(category);
    }

    @Override
    @Transactional
    public List<ExerciseCategory> getCategories() {
        return exerciseDAO.getCategories();
    }
}
