package com.example.demo.rest.exercise;

import com.example.demo.entity.Exercise;
import com.example.demo.entity.ExerciseLog;
import com.example.demo.entity.User;
import com.example.demo.entity.request.ExerciseLogData;
import com.example.demo.entity.response.ExerciseLogsByDate;
import com.example.demo.service.exercise.ExerciseService;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashSet;
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
    public ResponseEntity<Set<ExerciseLogsByDate>> getExerciseLogs(@RequestHeader (name="Authorization") String header, @PathVariable int exerciseId) {
        User user = userService.getUserFromToken(header);
        Exercise exercise = exerciseService.getExercise(exerciseId);

        List<ExerciseLog> exerciseLogs = exerciseService.getExerciseLogs(exercise, user);

        if(exerciseLogs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        LocalDate currentDate = LocalDate.now();
        int currentDay = currentDate.getDayOfMonth();
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();

        // Grouped exercise set
        Set<ExerciseLogsByDate> exerciseLogsByDates = new HashSet<>();

        // Set of exercises submitted the same day
        ExerciseLogsByDate exerciseLogsByDate = new ExerciseLogsByDate();
        exerciseLogsByDate.setDate(currentDate);

        for (ExerciseLog exerciseLog : exerciseLogs) {
            int logDay = exerciseLog.getSubmitDate().getDayOfMonth();
            int logMonth= exerciseLog.getSubmitDate().getMonthValue();
            int logYear = exerciseLog.getSubmitDate().getYear();
            if(currentDay != logDay || currentMonth != logMonth || currentYear != logYear) {
                // save existing group to log set
                exerciseLogsByDates.add(exerciseLogsByDate);

                // create new group
                exerciseLogsByDate = new ExerciseLogsByDate();

                // set new date for the group
                exerciseLogsByDate.setDate(exerciseLog.getSubmitDate());

                // set new current Date
                currentDay = logDay;
                currentMonth = logMonth;
                currentYear = logYear;
            }

            exerciseLogsByDate.getExerciseLogSet().add(exerciseLog);
        }
        // add last group of logs
        exerciseLogsByDates.add(exerciseLogsByDate);

        return ResponseEntity.ok(exerciseLogsByDates);
    }

    @PostMapping("/logs")
    public ResponseEntity<?> addExerciseLog(@RequestHeader (name="Authorization") String header, @RequestBody ExerciseLogData exerciseLogData) {
        User user = userService.getUserFromToken(header);

        ExerciseLog exerciseLog = new ExerciseLog();
        exerciseLog.setId(0);
        exerciseLog.setReferredExercise(exerciseService.getExercise(exerciseLogData.getExerciseId()));
        exerciseLog.setTrainee(user);
        exerciseLog.setReps(exerciseLogData.getReps());
        exerciseLog.setWeight(exerciseLogData.getWeight());
        exerciseLog.setSubmitDate(LocalDate.now());

        exerciseService.addExerciseLog(user, exerciseLog);

        return ResponseEntity.ok(exerciseLog);
    }
}
