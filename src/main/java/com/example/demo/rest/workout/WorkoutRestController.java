package com.example.demo.rest.workout;

import com.example.demo.entity.Workout;
import com.example.demo.rest.ObjectNotFoundException;
import com.example.demo.service.GymService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class WorkoutRestController {

    @Autowired
    private GymService gymService;

    @GetMapping("/workouts")
    public List<Workout> getWorkouts() {

        return gymService.getWorkouts();
    }

    @GetMapping("/workouts/{workoutId}")
    public Workout getWorkout(@PathVariable int workoutId) {

        Workout workout = gymService.getWorkout(workoutId);

        if (workout == null) {
            throw new ObjectNotFoundException("User id not found - " + workoutId);
        }
        return workout;
    }

    @GetMapping(value = "/workouts", params = "user", produces="application/json")
    public List<Workout> getWorkoutsByUserId(@RequestParam("user") int userId) {
        return gymService.getWorkoutsByUserId(userId);
    }

    @PostMapping("/workouts/{userId}")
    public Workout addWorkout(@PathVariable int userId, @RequestBody Workout workout) {

        // Check if username, email and password is not empty
        if (workout.getName().isEmpty() || workout.getName() == null) {
            throw new ObjectNotFoundException("Workout name cannot be empty, you have to provide one!");
        }

        workout.setId(0);
        gymService.saveWorkout(userId, workout);

        return workout;
    }

    @PutMapping("/workouts")
    public Workout updateWorkout(@RequestBody int userId, @RequestBody Workout workout) {
        gymService.saveWorkout(userId, workout);
        return workout;
    }

    @DeleteMapping("/workouts/{workoutId}")
    public String deleteWorkout(@PathVariable int workoutId) {

        Workout tempWorkout = gymService.getWorkout(workoutId);

        if (tempWorkout == null) {
            throw new ObjectNotFoundException("Workout id not found - " + workoutId);
        }

        gymService.deleteWorkout(workoutId);

        return "Deleted workout id - " + workoutId;
    }
}