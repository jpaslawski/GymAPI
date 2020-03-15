package com.example.demo.rest.exercise;

import com.example.demo.entity.Exercise;
import com.example.demo.rest.ObjectNotFoundException;
import com.example.demo.service.GymService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ExerciseRestController {

    @Autowired
    private GymService gymService;

    @GetMapping("/exercises")
    public List<Exercise> getExercises() {

        return gymService.getExercises();
    }

    @GetMapping("/exercises/{exerciseId}")
    public Exercise getWorkout(@PathVariable int exerciseId) {

        Exercise exercise = gymService.getExercise(exerciseId);

        if (exercise == null) {
            throw new ObjectNotFoundException("Exercise id not found - " + exerciseId);
        }
        return exercise;
    }

    @PostMapping("/exercises/{userId}")
    public Exercise addExercise(@PathVariable int userId, @RequestBody Exercise exercise) {

        if (exercise.getName().isEmpty() || exercise.getName() == null) {
            throw new ObjectNotFoundException("Exercise name cannot be empty, you have to provide one!");
        }

        exercise.setId(0);
        gymService.saveExercise(userId, exercise);

        return exercise;
    }

    @PostMapping("/exercises/{userId}/{workoutId}")
    public Exercise addNewExerciseToWorkout(@PathVariable int userId,@PathVariable int workoutId, @RequestBody Exercise exercise) {
        // Check if username, email and password is not empty
        if (exercise.getName().isEmpty() || exercise.getName() == null) {
            throw new ObjectNotFoundException("Exercise name cannot be empty, you have to provide one!");
        }

        gymService.addNewExerciseToWorkout(userId, workoutId, exercise);

        return exercise;
    }

    @PutMapping("/exercises")
    public Exercise updateExercise(@RequestBody int userId, @RequestBody Exercise exercise) {
        gymService.saveExercise(userId, exercise);
        return exercise;
    }

    @PutMapping("/exercises/{exerciseId}/{workoutId}")
    public String addExerciseToWorkout(@PathVariable int exerciseId,@PathVariable int workoutId) {

        gymService.addExerciseToWorkout(exerciseId, workoutId);

        return "Exercise " + exerciseId + " added to workout " + workoutId;
    }

    @DeleteMapping("/exercises/{exerciseId}")
    public String deleteExercise(@PathVariable int exerciseId) {

        Exercise tempExercise = gymService.getExercise(exerciseId);

        if (tempExercise == null) {
            throw new ObjectNotFoundException("Exercise id not found - " + exerciseId);
        }

        gymService.deleteExercise(exerciseId);

        return "Deleted exercise id - " + exerciseId;
    }
}
