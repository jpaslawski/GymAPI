package com.example.demo.dao.workout;

import com.example.demo.entity.User;
import com.example.demo.entity.Workout;

import java.util.List;

public interface WorkoutDAO {

    List<Workout> getWorkouts();

    List<Workout> getWorkoutsByUserId(int userId);

    void saveWorkout(User user, Workout workout);

    Workout getWorkout(int workoutId);

    void deleteWorkout(int workoutId);

}
