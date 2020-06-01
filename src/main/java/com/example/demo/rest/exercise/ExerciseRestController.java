package com.example.demo.rest.exercise;

import com.example.demo.entity.Exercise;
import com.example.demo.entity.User;
import com.example.demo.entity.request.ExerciseData;
import com.example.demo.rest.ObjectNotFoundException;
import com.example.demo.service.exercise.ExerciseService;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ExerciseRestController {

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private UserService userService;

    @GetMapping("/exercises")
    public List<Exercise> getExercises() {

        return exerciseService.getExercises();
    }

    @GetMapping(value = "/exercises", params = "workoutId", produces = "application/json")
    public ResponseEntity<Set<Exercise>> getExercisesByWorkoutId(@RequestParam("workoutId") int workoutId) {

        Set<Exercise> exercises = exerciseService.getExercisesByWorkoutId(workoutId);

        if (exercises.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(exercises);
    }

    @GetMapping(value="/exercises", params = "category", produces = "application/json")
    public ResponseEntity<Set<Exercise>> getExercisesByCategory(@RequestParam("category") String category) {

        Set<Exercise> exercises = exerciseService.getExercisesByCategory(category);

        if(exercises.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(exercises);
    }

    @GetMapping("/exercises/{exerciseId}")
    public Exercise getExercise(@PathVariable int exerciseId) {

        Exercise exercise = exerciseService.getExercise(exerciseId);

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

        User user = userService.getUserFromToken(header);

        Exercise exercise = new Exercise(exerciseData.getName(), exerciseData.getInfo(), false);
        exercise.setId(0);

        exerciseService.saveExercise(user, exercise, exerciseData.getCategory());

        return exercise;
    }

    @PostMapping("/exercises/{workoutId}")
    public ResponseEntity<?> addNewExerciseToWorkout(@RequestHeader (name="Authorization") String header, @PathVariable("workoutId") int workoutId, @RequestBody ExerciseData exerciseData) {

        if (exerciseData.getName().isEmpty() || exerciseData.getName() == null) {
            return new ResponseEntity<>("The name field is empty!", HttpStatus.BAD_REQUEST);
        } else if (exerciseData.getCategory() == null) {
            return new ResponseEntity<>("The category field is empty!", HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUserFromToken(header);

        Exercise exercise = new Exercise(exerciseData.getName(), exerciseData.getInfo(), false);
        exerciseService.addNewExerciseToWorkout(user, workoutId, exercise, exerciseData.getCategory());

        return ResponseEntity.ok(exerciseService.getExercisesByWorkoutId(workoutId));
    }

    @PutMapping("/exercises")
    public Exercise updateExercise(@RequestHeader (name="Authorization") String header, @RequestBody ExerciseData exerciseData) {
        User user = userService.getUserFromToken(header);

        Exercise exercise = new Exercise(exerciseData.getName(), exerciseData.getInfo(), false);
        exerciseService.saveExercise(user, exercise, exerciseData.getCategory());
        return exercise;
    }

    @PutMapping("/exercises/{exerciseId}/{workoutId}")
    public String addExerciseToWorkout(@PathVariable int exerciseId,@PathVariable int workoutId) {

        exerciseService.addExerciseToWorkout(exerciseId, workoutId);

        return "Exercise " + exerciseId + " added to workout " + workoutId;
    }

    @DeleteMapping("/exercises/{exerciseId}")
    public String deleteExercise(@PathVariable int exerciseId) {

        Exercise tempExercise = exerciseService.getExercise(exerciseId);

        if (tempExercise == null) {
            throw new ObjectNotFoundException("Exercise id not found - " + exerciseId);
        }

        exerciseService.deleteExercise(exerciseId);

        return "Deleted exercise id - " + exerciseId;
    }

    @DeleteMapping("/exercises/{exerciseId}/{workoutId}")
    public String deleteExerciseFromWorkout(@PathVariable int exerciseId, @PathVariable int workoutId) {
        Exercise tempExercise = exerciseService.getExercise(exerciseId);

        if (tempExercise == null) {
            throw new ObjectNotFoundException("Exercise id not found - " + exerciseId);
        }

        exerciseService.deleteExerciseFromWorkout(exerciseId, workoutId);

        return "Deleted exercise id - " + exerciseId;
    }
}
