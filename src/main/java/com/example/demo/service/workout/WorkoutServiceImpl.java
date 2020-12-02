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
    public List<Workout> getWorkouts(User user) {
        return workoutDAO.getWorkouts(user);
    }

    @Override
    @Transactional
    public List<Workout> getPublicWorkouts() {
        return workoutDAO.getPublicWorkouts();
    }

    @Override
    @Transactional
    public List<Workout> getPendingWorkouts() {
        return workoutDAO.getPendingWorkouts();
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
