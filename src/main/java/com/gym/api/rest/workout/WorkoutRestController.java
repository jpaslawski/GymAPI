package com.gym.api.rest.workout;

import com.gym.api.entity.Status;
import com.gym.api.entity.User;
import com.gym.api.entity.Workout;
import com.gym.api.entity.request.WorkoutData;
import com.gym.api.rest.ObjectNotFoundException;
import com.gym.api.service.user.UserService;
import com.gym.api.service.workout.WorkoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        if(user == null) {
            ResponseEntity.badRequest().build();
        }

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

    @GetMapping("/workouts/pending")
    public ResponseEntity<List<Workout>> getPendingWorkouts(@RequestHeader (name="Authorization") String header) {
        User user = userService.getUserFromToken(header);
        if(user == null) {
            ResponseEntity.status(HttpStatus.FORBIDDEN);
        }
        List<Workout> workoutList;
        if(user.getPermissions().equals("ROLE_ADMIN")) {
            workoutList = workoutService.getAdminPendingWorkouts();
        } else {
            workoutList = workoutService.getUserPendingWorkouts(user);
        }

        if(workoutList.isEmpty()) {
            ResponseEntity.status(HttpStatus.NO_CONTENT);
        }

        return ResponseEntity.ok(workoutList);
    }

    @GetMapping("/workouts/{workoutId}")
    public ResponseEntity<Workout> getWorkout(@PathVariable int workoutId) {

        Workout workout = workoutService.getWorkout(workoutId);

        if (workout == null) {
            throw new ObjectNotFoundException("Workout id not found - " + workoutId);
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

        Workout workout = new Workout(workoutData.getName(), workoutData.getNamePL(), workoutData.getInfo(), workoutData.getInfoPL(), Status.PRIVATE, null,  0);

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

    @PutMapping("/admin/workouts/{workoutId}/{action}")
    public ResponseEntity<Workout> acceptOrDenySharedWorkout(@RequestHeader (name="Authorization") String header, @PathVariable int workoutId, @PathVariable String action) {
        User user = userService.getUserFromToken(header);
        Workout optionalWorkout = workoutService.getWorkout(workoutId);
        if(optionalWorkout == null) {
            return ResponseEntity.notFound().build();
        }

        if(action.equals("accept")) {
            optionalWorkout.setStatus(Status.PUBLIC);
            workoutService.saveWorkout(user, optionalWorkout);
        }
        else if(action.equals("deny")) {
            optionalWorkout.setStatus(Status.PRIVATE);
            workoutService.saveWorkout(optionalWorkout.getAuthor(), optionalWorkout);
        }

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