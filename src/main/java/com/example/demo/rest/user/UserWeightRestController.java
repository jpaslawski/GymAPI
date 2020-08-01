package com.example.demo.rest.user;

import com.example.demo.entity.User;
import com.example.demo.entity.WeightLog;
import com.example.demo.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserWeightRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/weight")
    public ResponseEntity<List<WeightLog>> getWeightLogs(@RequestHeader(name="Authorization") String header) {
        User user = userService.getUserFromToken(header);
        List<WeightLog> weightLogs = userService.getWeightLogs(user);

        if(weightLogs.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(weightLogs);
    }

    @GetMapping("/users/currentWeight")
    public ResponseEntity<WeightLog> getCurrentWeight(@RequestHeader (name="Authorization") String header) {
        User user = userService.getUserFromToken(header);
        WeightLog currentWeight = userService.getCurrentWeight(user);

        if(currentWeight == null) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(currentWeight);
    }

    @GetMapping("/users/checkWeight")
    public ResponseEntity<Boolean> checkTodayWeightLog(@RequestHeader (name="Authorization") String header) {
        User user = userService.getUserFromToken(header);
        boolean response = userService.checkTodayWeightLog(user);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/users/weight")
    public ResponseEntity<?> addWeightLog(@RequestHeader (name="Authorization") String header, @RequestBody WeightLog weightLog) throws URISyntaxException {
        User user = userService.getUserFromToken(header);
        weightLog.setSubmitDate(LocalDate.now());
        userService.saveWeightLog(weightLog, user);

        return ResponseEntity.created(new URI("/users/weight")).build();
    }
}
