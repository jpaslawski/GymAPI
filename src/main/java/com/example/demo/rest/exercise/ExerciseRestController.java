package com.example.demo.rest.exercise;

import com.example.demo.entity.Exercise;
import com.example.demo.entity.ExerciseCategory;
import com.example.demo.entity.request.ExerciseData;
import com.example.demo.rest.ObjectNotFoundException;
import com.example.demo.service.GymService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ExerciseRestController {

    @Autowired
    private GymService gymService;

    @GetMapping("/exercises")
    public List<Exercise> getExercises() {

        return gymService.getExercises();
    }

    @GetMapping(value = "/exercises", params = "workoutId", produces="application/json")
    public ResponseEntity<Set<Exercise>> getExercisesByWorkoutId(@RequestParam("workoutId") int workoutId) {

        Set<Exercise> exercises = gymService.getExercisesByWorkoutId(workoutId);

        Set<ExerciseData> exerciseData;

        if (exercises.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/exercises/{exerciseId}")
    public Exercise getExercise(@PathVariable int exerciseId) {

        Exercise exercise = gymService.getExercise(exerciseId);

        if (exercise == null) {
            throw new ObjectNotFoundException("Exercise id not found - " + exerciseId);
        }
        return exercise;
    }

    @PostMapping("/exercises")
    public Exercise addExercise(@RequestHeader (name="Authorization") String header, @RequestBody ExerciseData exerciseData) {

        if (exerciseData.getName().isEmpty() || exerciseData.getName() == null) {
            throw new ObjectNotFoundException("Exercise name cannot be empty, you have to provide one!");
        }

        String email = gymService.getEmailFromToken(header);

        Exercise exercise = new Exercise(exerciseData.getName(), exerciseData.getInfo(), false);
        exercise.setId(0);

        gymService.saveExercise(email, exercise, exerciseData.getCategory());

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
    public Exercise updateExercise(@RequestHeader (name="Authorization") String header, @RequestBody ExerciseData exerciseData) {
        String email = gymService.getEmailFromToken(header);

        Exercise exercise = new Exercise(exerciseData.getName(), exerciseData.getInfo(), false);
        gymService.saveExercise(email, exercise, exerciseData.getCategory());
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

    @DeleteMapping("/exercises/{exerciseId}/{workoutId}")
    public String deleteExerciseFromWorkout(@PathVariable int exerciseId, @PathVariable int workoutId) {
        Exercise tempExercise = gymService.getExercise(exerciseId);

        if (tempExercise == null) {
            throw new ObjectNotFoundException("Exercise id not found - " + exerciseId);
        }

        gymService.deleteExerciseFromWorkout(exerciseId, workoutId);

        return "Deleted exercise id - " + exerciseId;
    }
}
