package com.example.demo.rest.exercise;

import com.example.demo.entity.Exercise;
import com.example.demo.entity.ExerciseLog;
import com.example.demo.entity.User;
import com.example.demo.service.exercise.ExerciseService;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ExerciseLogRestController {

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private UserService userService;

    @GetMapping("/logs/{exerciseId}")
    public List<ExerciseLog> getExerciseLogs(@RequestHeader (name="Authorization") String header, @PathVariable int exerciseId) {
        User user = userService.getUserFromToken(header);

        return exerciseService.getExerciseLogs(exerciseId);
    }

    @GetMapping(value = "/exercises", params = "workoutId", produces = "application/json")
    public ResponseEntity<Set<Exercise>> getExercisesByWorkoutId(@RequestParam("workoutId") int workoutId) {

        Set<Exercise> exercises = exerciseService.getExercisesByWorkoutId(workoutId);

        if (exercises.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(exercises);
    }
}
