package com.example.demo.service;

import com.example.demo.dao.GymDAO;
import com.example.demo.entity.User;
import com.example.demo.entity.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class GymServiceImpl implements GymService {

    @Autowired
    private GymDAO gymDAO;

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
    public void saveWorkout(int userId, Workout workout) {
        gymDAO.saveWorkout(userId, workout);
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
}
