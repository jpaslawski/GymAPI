package com.gym.api.service.workout;

import com.gym.api.entity.User;
import com.gym.api.entity.Workout;

import java.util.List;

public interface WorkoutService {

    List<Workout> getWorkouts(User user);

    List<Workout> getPublicWorkouts();

    List<Workout> getUserPendingWorkouts(User user);

    List<Workout> getAdminPendingWorkouts();

    void saveWorkout(User user, Workout workout);

    Workout getWorkout(int workoutId);

    void deleteWorkout(int workoutId);
}
