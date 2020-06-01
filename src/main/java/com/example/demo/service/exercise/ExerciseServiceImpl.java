package com.example.demo.service.exercise;

import com.example.demo.dao.exercise.ExerciseDAO;
import com.example.demo.entity.Exercise;
import com.example.demo.entity.ExerciseCategory;
import com.example.demo.entity.ExerciseLog;
import com.example.demo.entity.User;
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
    public List<Exercise> getExercises() {
        return exerciseDAO.getExercises();
    }

    @Override
    @Transactional
    public Set<Exercise> getExercisesByWorkoutId(int workoutId) {
        return exerciseDAO.getExercisesByWorkoutId(workoutId);
    }

    @Override
    @Transactional
    public Set<Exercise> getExercisesByCategory(String category) {
        return exerciseDAO.getExercisesByCategory(category);
    }

    @Override
    @Transactional
    public void saveExercise(User user, Exercise exercise, String category) {
        exerciseDAO.saveExercise(user, exercise, category);
    }

    @Override
    @Transactional
    public void addExerciseToWorkout(int exerciseId, int workoutId) {
        exerciseDAO.addExerciseToWorkout(exerciseId, workoutId);
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
    public List<ExerciseLog> getExerciseLogs(int exerciseId) {
        return exerciseDAO.getExerciseLogs(exerciseId);
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

    @Override
    @Transactional
    public void addNewExerciseToWorkout(User user, int workoutId, Exercise exercise, String category) {
        exerciseDAO.addNewExerciseToWorkout(user, workoutId, exercise, category);
    }
}
