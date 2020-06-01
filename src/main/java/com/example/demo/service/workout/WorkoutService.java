package com.example.demo.service.workout;

import com.example.demo.entity.User;
import com.example.demo.entity.Workout;

import java.util.List;

public interface WorkoutService {

    List<Workout> getWorkouts();

    List<Workout> getWorkoutsByUserId(int userId);

    void saveWorkout(User user, Workout workout);

    Workout getWorkout(int workoutId);

    void deleteWorkout(int workoutId);
}
