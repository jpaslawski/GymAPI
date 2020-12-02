package com.example.demo.rest.workout;

import com.example.demo.entity.Status;
import com.example.demo.entity.User;
import com.example.demo.entity.Workout;
import com.example.demo.entity.request.WorkoutData;
import com.example.demo.rest.ObjectNotFoundException;
import com.example.demo.service.user.UserService;
import com.example.demo.service.workout.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class WorkoutRestController {

    @Autowired
    private WorkoutService workoutService;

    @Autowired
    private UserService userService;

    @GetMapping("/workouts")
    public ResponseEntity<List<Workout>> getWorkouts(@RequestHeader (name="Authorization") String header) {
        User user = userService.getUserFromToken(header);
        List<Workout> workoutList = workoutService.getWorkouts(user);

        if(workoutList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(workoutList);
    }

    @GetMapping("/workouts/public")
    public ResponseEntity<List<Workout>> getPublicWorkouts() {
        List<Workout> workoutList = workoutService.getPublicWorkouts();

        if(workoutList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(workoutList);
    }

    @GetMapping("/admin/workouts/pending")
    public ResponseEntity<List<Workout>> getPendingWorkouts() {
        List<Workout> workoutList = workoutService.getPendingWorkouts();

        if(workoutList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(workoutList);
    }

    @GetMapping("/workouts/{workoutId}")
    public ResponseEntity<Workout> getWorkout(@PathVariable int workoutId) {

        Workout workout = workoutService.getWorkout(workoutId);

        if (workout == null) {
            throw new ObjectNotFoundException("User id not found - " + workoutId);
        }
        return ResponseEntity.ok(workout);
    }

    @PostMapping("/workouts")
    public Workout addWorkout(@RequestHeader (name="Authorization") String header, @RequestBody WorkoutData workoutData) {

        // Check if name not empty
        if (workoutData.getName().isEmpty() || workoutData.getName() == null) {
            throw new ObjectNotFoundException("Workout name cannot be empty, you have to provide one!");
        }

        User user = userService.getUserFromToken(header);

        Workout workout = new Workout(workoutData.getName(), workoutData.getInfo(), Status.PRIVATE, null,  0);

        workout.setId(0);
        workoutService.saveWorkout(user, workout);

        return workout;
    }

    @PutMapping("/workouts/{workoutId}")
    public ResponseEntity<Workout> updateWorkout(@RequestHeader (name="Authorization") String header, @RequestBody Workout workout, @PathVariable int workoutId) {
        User user = userService.getUserFromToken(header);
        Workout optionalWorkout = workoutService.getWorkout(workoutId);

        if(optionalWorkout == null) {
            return ResponseEntity.notFound().build();
        }

        workout.setId(workoutId);
        workout.setStatus(optionalWorkout.getStatus());
        workoutService.saveWorkout(user, workout);

        return ResponseEntity.ok(workout);
    }

    @PutMapping("/workouts/{workoutId}/share")
    public ResponseEntity<Workout> shareWorkout(@RequestHeader (name="Authorization") String header, @PathVariable int workoutId) {
        User user = userService.getUserFromToken(header);
        Workout optionalWorkout = workoutService.getWorkout(workoutId);
        if(optionalWorkout == null) {
            return ResponseEntity.notFound().build();
        }

        optionalWorkout.setStatus(Status.PENDING);
        workoutService.saveWorkout(user, optionalWorkout);

        return ResponseEntity.ok(optionalWorkout);
    }

    @DeleteMapping("/workouts/{workoutId}")
    public ResponseEntity<?> deleteWorkout(@PathVariable int workoutId) {

        Workout tempWorkout = workoutService.getWorkout(workoutId);

        if (tempWorkout == null) {
            return ResponseEntity.notFound().build();
        }
        workoutService.deleteWorkout(workoutId);

        return ResponseEntity.ok("Deleted");
    }
}