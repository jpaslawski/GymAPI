package com.gym.api.rest.exercise;

import com.gym.api.entity.Exercise;
import com.gym.api.entity.ExerciseLog;
import com.gym.api.entity.User;
import com.gym.api.entity.request.ExerciseLogData;
import com.gym.api.entity.response.ExerciseLogsByDate;
import com.gym.api.service.exercise.ExerciseService;
import com.gym.api.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ExerciseLogRestController {

    @Autowired
    private ExerciseService exerciseService;

    @Autowired
    private UserService userService;

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void calculateAverageWeight (ExerciseLogsByDate exerciseLogsByDate) {
        float weightSum = 0;
        int repsSum = 0;
        for (ExerciseLog exerciseLog : exerciseLogsByDate.getExerciseLogSet()) {
            weightSum += exerciseLog.getWeight() * exerciseLog.getReps();
            repsSum += exerciseLog.getReps();
        }

        DecimalFormat df = new DecimalFormat("#.##");
        exerciseLogsByDate.setAverageWeight(round(weightSum/repsSum, 2));
    }

    public void calculateOneRepMax(ExerciseLogsByDate exerciseLogsByDate) {
        double oneRepMax = 0.0, tempOneRepMax;
        for (ExerciseLog exerciseLog : exerciseLogsByDate.getExerciseLogSet()) {
            tempOneRepMax = exerciseLog.getWeight() * (1.0 + (exerciseLog.getReps() / 30.0));
            if (oneRepMax < tempOneRepMax) {
                oneRepMax = tempOneRepMax;
            }
        }

        DecimalFormat df = new DecimalFormat("#.##");
        exerciseLogsByDate.setOneRepMax(round(oneRepMax, 2));
    }

    @GetMapping("/logs/{exerciseId}")
    public ResponseEntity<List<ExerciseLogsByDate>> getExerciseLogs(@RequestHeader (name="Authorization") String header, @PathVariable int exerciseId) {
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
        List<ExerciseLogsByDate> exerciseLogsByDates = new ArrayList<>();

        // Set of exercises submitted the same day
        ExerciseLogsByDate exerciseLogsByDate = new ExerciseLogsByDate();
        exerciseLogsByDate.setDate(currentDate);

        for (ExerciseLog exerciseLog : exerciseLogs) {
            int logDay = exerciseLog.getSubmitDate().getDayOfMonth();
            int logMonth= exerciseLog.getSubmitDate().getMonthValue();
            int logYear = exerciseLog.getSubmitDate().getYear();
            if(currentDay != logDay || currentMonth != logMonth || currentYear != logYear) {
                // save existing group to log set
                if (exerciseLogsByDate.getExerciseLogSet().size() > 0) {
                    calculateAverageWeight(exerciseLogsByDate);
                    calculateOneRepMax(exerciseLogsByDate);
                    exerciseLogsByDates.add(exerciseLogsByDate);
                }

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
        calculateAverageWeight(exerciseLogsByDate);
        calculateOneRepMax(exerciseLogsByDate);
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
