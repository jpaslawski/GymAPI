package com.example.demo.service.workout;

import com.example.demo.dao.workout.WorkoutDAO;
import com.example.demo.entity.User;
import com.example.demo.entity.Workout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class WorkoutServiceImpl implements WorkoutService {

    @Autowired
    private WorkoutDAO workoutDAO;

    @Override
    @Transactional
    public List<Workout> getWorkouts() {
        return workoutDAO.getWorkouts();
    }

    @Override
    @Transactional
    public List<Workout> getWorkoutsByUserId(int userId) {
        return workoutDAO.getWorkoutsByUserId(userId);
    }

    @Override
    @Transactional
    public void saveWorkout(User user, Workout workout) {
        workoutDAO.saveWorkout(user, workout);
    }

    @Override
    @Transactional
    public Workout getWorkout(int workoutId) {
        return workoutDAO.getWorkout(workoutId);
    }

    @Override
    @Transactional
    public void deleteWorkout(int workoutId) {
        workoutDAO.deleteWorkout(workoutId);
    }

}
