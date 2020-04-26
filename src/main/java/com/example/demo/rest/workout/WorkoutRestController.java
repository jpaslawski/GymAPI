package com.example.demo.rest.workout;

import com.example.demo.entity.Workout;
import com.example.demo.entity.request.WorkoutData;
import com.example.demo.rest.ObjectNotFoundException;
import com.example.demo.service.GymService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class WorkoutRestController {

    @Autowired
    private GymService gymService;

    @GetMapping("/workouts")
    public ResponseEntity<List<Workout>> getWorkouts() {

        List<Workout> workoutList = gymService.getWorkouts();

        if(workoutList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(workoutList);
    }

    @GetMapping("/workouts/{workoutId}")
    public ResponseEntity<Workout> getWorkout(@PathVariable int workoutId) {

        Workout workout = gymService.getWorkout(workoutId);

        if (workout == null) {
            throw new ObjectNotFoundException("User id not found - " + workoutId);
        }
        return ResponseEntity.ok(workout);
    }

    @GetMapping(value = "/workouts", params = "user", produces="application/json")
    public ResponseEntity<List<Workout>> getWorkoutsByUserId(@RequestParam("user") int userId) {
        List<Workout> userWorkoutList = gymService.getWorkoutsByUserId(userId);

        if(userWorkoutList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(userWorkoutList);
    }

    @PostMapping("/workouts")
    public Workout addWorkout(@RequestHeader (name="Authorization") String header, @RequestBody WorkoutData workoutData) {

        // Check if name not empty
        if (workoutData.getName().isEmpty() || workoutData.getName() == null) {
            throw new ObjectNotFoundException("Workout name cannot be empty, you have to provide one!");
        }

        String email = gymService.getEmailFromToken(header);

        Workout workout = new Workout(workoutData.getName(), workoutData.getInfo(), null, false, 0);

        workout.setId(0);
        gymService.saveWorkout(email, workout);

        return workout;
    }

    @PutMapping("/workouts")
    public Workout updateWorkout(@RequestHeader (name="Authorization") String header, @RequestBody Workout workout) {
        String email = gymService.getEmailFromToken(header);

        gymService.saveWorkout(email, workout);
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