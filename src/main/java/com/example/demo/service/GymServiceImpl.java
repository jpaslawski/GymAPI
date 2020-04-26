package com.example.demo.service;

import com.example.demo.dao.GymDAO;
import com.example.demo.entity.Exercise;
import com.example.demo.entity.ExerciseCategory;
import com.example.demo.entity.User;
import com.example.demo.entity.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
public class GymServiceImpl implements GymService {

    @Autowired
    private GymDAO gymDAO;

    @Override
    public String getEmailFromToken(String header) {
        return gymDAO.getEmailFromToken(header);
    }

    @Override
    @Transactional
    public List<User> getUsers() {
        return gymDAO.getUsers();
    }

    @Override
    @Transactional
    public void saveUser(User user) {
        gymDAO.saveUser(user);
    }

    @Override
    @Transactional
    public User getUser(int userId) {
        return gymDAO.getUser(userId);
    }

    @Override
    @Transactional
    public User getUserByEmail(String userEmail) {
        return gymDAO.getUserByEmail(userEmail);
    }

    @Override
    @Transactional
    public void deleteUser(int userId) {
        gymDAO.deleteUser(userId);
    }

    @Override
    @Transactional
    public List<Workout> getWorkouts() {
        return gymDAO.getWorkouts();
    }

    @Override
    @Transactional
    public List<Workout> getWorkoutsByUserId(int userId) {
        return gymDAO.getWorkoutsByUserId(userId);
    }

    @Override
    @Transactional
    public void saveWorkout(String email, Workout workout) {
        gymDAO.saveWorkout(email, workout);
    }

    @Override
    @Transactional
    public Workout getWorkout(int workoutId) {
        return gymDAO.getWorkout(workoutId);
    }

    @Override
    @Transactional
    public void deleteWorkout(int workoutId) {
        gymDAO.deleteWorkout(workoutId);
    }

    @Override
    @Transactional
    public List<Exercise> getExercises() {
        return gymDAO.getExercises();
    }

    @Override
    @Transactional
    public Set<Exercise> getExercisesByWorkoutId(int workoutId) {
        return gymDAO.getExercisesByWorkoutId(workoutId);
    }

    @Override
    @Transactional
    public void saveExercise(String email, Exercise exercise, String category) {
        gymDAO.saveExercise(email, exercise, category);
    }

    @Override
    @Transactional
    public void addExerciseToWorkout(int exerciseId, int workoutId) {
        gymDAO.addExerciseToWorkout(exerciseId, workoutId);
    }

    @Override
    @Transactional
    public void addExerciseCategory(ExerciseCategory exerciseCategory) {
        gymDAO.addExerciseCategory(exerciseCategory);
    }

    @Override
    @Transactional
    public void deleteExerciseFromWorkout(int exerciseId, int workoutId) {
        gymDAO.deleteExerciseFromWorkout(exerciseId, workoutId);
    }

    @Override
    @Transactional
    public Exercise getExercise(int exerciseId) {
        return gymDAO.getExercise(exerciseId);
    }

    @Override
    @Transactional
    public void deleteExercise(int exerciseId) {
        gymDAO.deleteExercise(exerciseId);
    }

    @Override
    @Transactional
    public List<ExerciseCategory> getCategories() {
        return gymDAO.getCategories();
    }

    @Override
    @Transactional
    public void addNewExerciseToWorkout(int userId, int workoutId, Exercise exercise) {
        gymDAO.addNewExerciseToWorkout(userId, workoutId, exercise);
    }
}
