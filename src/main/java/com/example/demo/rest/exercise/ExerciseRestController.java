package com.example.demo.rest.exercise;

import com.example.demo.entity.Exercise;
import com.example.demo.entity.Status;
import com.example.demo.entity.User;
import com.example.demo.entity.Workout;
import com.example.demo.entity.request.ExerciseData;
import com.example.demo.service.exercise.ExerciseService;
import com.example.demo.service.user.UserService;
import com.example.demo.service.workout.WorkoutService;
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

    @Autowired
    private WorkoutService workoutService;

    @GetMapping("/exercises")
    public ResponseEntity<List<Exercise>> getExercises(@RequestHeader (name="Authorization") String header) {
        User user = userService.getUserFromToken(header);
        List<Exercise> exerciseList = exerciseService.getExercises(user);

        if(exerciseList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(exerciseList);
    }

    @GetMapping("/exercises/public")
    public ResponseEntity<List<Exercise>> getPublicExercises() {
        List<Exercise> exerciseList = exerciseService.getPublicExercises();

        if(exerciseList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(exerciseList);
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
    public ResponseEntity<Exercise> getExercise(@PathVariable int exerciseId) {

        Exercise exercise = exerciseService.getExercise(exerciseId);

        if (exercise == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(exercise);
    }

    @GetMapping("/exercises/last")
    public ResponseEntity<Exercise> getLastExercise(@RequestHeader (name="Authorization") String header) {
        User user = userService.getUserFromToken(header);
        Exercise exercise = exerciseService.getLastExercise(user);

        if (exercise == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok(exercise);
    }

    @PostMapping("/exercises")
    public ResponseEntity<?> addExercise(@RequestHeader (name="Authorization") String header, @RequestBody ExerciseData exerciseData) {

        if (exerciseData.getName().isEmpty() || exerciseData.getName() == null) {
            return new ResponseEntity<>("The name field is empty!", HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUserFromToken(header);

        Exercise exercise = new Exercise(exerciseData.getName(), exerciseData.getInfo(), Status.PRIVATE);
        exercise.setId(0);

        exerciseService.saveExercise(user, exercise, exerciseData.getCategory());

        return ResponseEntity.ok(exercise);
    }

    @PostMapping("/exercises/{workoutId}")
    public ResponseEntity<?> addNewExerciseToWorkout(@RequestHeader (name="Authorization") String header, @PathVariable("workoutId") int workoutId, @RequestBody ExerciseData exerciseData) {

        if (exerciseData.getName().isEmpty() || exerciseData.getName() == null) {
            return new ResponseEntity<>("The name field is empty!", HttpStatus.BAD_REQUEST);
        } else if (exerciseData.getCategory() == null) {
            return new ResponseEntity<>("The category field is empty!", HttpStatus.BAD_REQUEST);
        }

        User user = userService.getUserFromToken(header);

        Exercise exercise = new Exercise(exerciseData.getName(), exerciseData.getInfo(), Status.PRIVATE);
        exercise.setId(0);

        Workout workout = workoutService.getWorkout(workoutId);

        exerciseService.addNewExerciseToWorkout(user, workout, exercise, exerciseData.getCategory());

        return ResponseEntity.ok(exerciseService.getExercisesByWorkoutId(workoutId));
    }

    @PutMapping("/exercises/{exerciseId}")
    public ResponseEntity<Exercise> updateExercise(@PathVariable int exerciseId, @RequestBody ExerciseData exerciseData) {
        Exercise exercise = exerciseService.getExercise(exerciseId);

        if(exercise == null) {
            return ResponseEntity.notFound().build();
        }

        exercise.setName(exerciseData.getName());
        exercise.setInfo(exerciseData.getInfo());

        exerciseService.updateExercise(exercise, exerciseData.getCategory());

        return ResponseEntity.ok(exercise);
    }

    @PutMapping("/exercises/{exerciseId}/{workoutId}")
    public ResponseEntity<String> addExistingExerciseToWorkout(@PathVariable int exerciseId, @PathVariable int workoutId) {

        Workout workout = workoutService.getWorkout(workoutId);
        Exercise exercise = exerciseService.getExercise(exerciseId);

        exerciseService.addExistingExerciseToWorkout(exercise, workout);

        return ResponseEntity.ok("Exercise " + exerciseId + " added to workout " + workoutId);
    }

    @DeleteMapping("/exercises/{exerciseId}")
    public ResponseEntity<String> deleteExercise(@PathVariable int exerciseId) {

        Exercise tempExercise = exerciseService.getExercise(exerciseId);

        if (tempExercise == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        exerciseService.deleteExercise(exerciseId);

        return ResponseEntity.ok("Deleted exercise id - " + exerciseId);
    }

    @DeleteMapping("/exercises/{exerciseId}/{workoutId}")
    public ResponseEntity<String> deleteExerciseFromWorkout(@PathVariable int exerciseId, @PathVariable int workoutId) {
        Exercise tempExercise = exerciseService.getExercise(exerciseId);
        if (tempExercise == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        exerciseService.deleteExerciseFromWorkout(exerciseId, workoutId);

        return ResponseEntity.ok("Exercise " + exerciseId + " deleted from workout " + workoutId);
    }
}
