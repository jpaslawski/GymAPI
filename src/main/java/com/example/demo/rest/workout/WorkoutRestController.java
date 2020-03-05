package com.example.demo.rest.workout;

import com.example.demo.entity.User;
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
/*
    // path = "/users?email=...
    @GetMapping(value = "/workouts", params = "email", produces="application/json")
    public boolean checkIfUserExists(@RequestParam("email") String userEmail) {
        User user = userService.getUserByEmail(userEmail);
        if (user == null) {
            return false;
        }
        return true;
    }*/

    @GetMapping("/workouts/{workoutId}")
    public Workout getWorkout(@PathVariable int workoutId) {

        Workout workout = gymService.getWorkout(workoutId);

        if (workout == null) {
            throw new ObjectNotFoundException("User id not found - " + workoutId);
        }
        return workout;
    }

    @PostMapping("/workouts/{userId}")
    public Workout addWorkout(@PathVariable int userId, @RequestBody Workout workout) {

        // Check if username, email and password is not empty
        if (workout.getName().isEmpty() || workout.getName() == null) {
            throw new ObjectNotFoundException("Username cannot be empty, you have to provide one!");
        }

        if (workout.getInfo().isEmpty() || workout.getInfo() == null) {
            throw new ObjectNotFoundException("Email cannot be empty, you have to provide one!");
        }

        workout.setId(0);
        gymService.saveWorkout(userId, workout);

        return workout;
    }
/*
    @PostMapping("/users/validate")
    public boolean validateUser(@RequestBody JsonNode node) {
        String login = node.get("login").asText();
        String password = node.get("password").asText();
        if (login != null && password != null) {
            User user = userService.getUserByEmail(login);
            return user.getPassword().equals(password);
        }
        return false;
    }*/

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