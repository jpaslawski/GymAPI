package com.example.demo.rest.user;

import com.example.demo.HelperMethods;
import com.example.demo.entity.UserDiet;
import com.example.demo.entity.WeightLog;
import com.example.demo.entity.request.AuthenticationRequest;
import com.example.demo.entity.request.UserUpdateData;
import com.example.demo.entity.response.AuthenticationResponse;
import com.example.demo.entity.User;
import com.example.demo.entity.request.NewUserData;
import com.example.demo.rest.ObjectNotFoundException;
import com.example.demo.service.exercise.ExerciseService;
import com.example.demo.service.user.UserService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExerciseService exerciseService;

    @GetMapping("/admin/users")
    public ResponseEntity<List<User>> getUsers() {

        List<User> userList = userService.getUsers();

        if(userList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(userList);
    }

    // path = "/users?email=...
    @GetMapping(value = "/users", params = "email", produces="application/json")
    public ResponseEntity<User> getUserByEmail(@RequestParam("email") String userEmail) {

        User user = userService.getUserByEmail(userEmail);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/details")
    public ResponseEntity<User> getUser(@RequestHeader (name="Authorization") String header) {

        User user = userService.getUserFromToken(header);

        if (user == null) {
            ResponseEntity.notFound();
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/create")
    public ResponseEntity<User> addUser(@RequestBody NewUserData newAccount) {

        // Check if username, email and password is not empty
        if (newAccount.getUsername().isEmpty() || newAccount.getUsername() == null) {
            throw new ObjectNotFoundException("Username cannot be empty, you have to provide one!");
        }

        if (newAccount.getEmail().isEmpty() || newAccount.getEmail() == null) {
            throw new ObjectNotFoundException("Email cannot be empty, you have to provide one!");
        }

        if (newAccount.getPassword().isEmpty() || newAccount.getPassword() == null) {
            throw new ObjectNotFoundException("Password cannot be empty, you have to provide one!");
        }

        // Check if email already in database
        User tempUser = userService.getUserByEmail(newAccount.getEmail());

        if (tempUser != null) {
            throw new ObjectNotFoundException("An account with this email already exists!");
        }

        User user = new User(newAccount.getUsername(), newAccount.getEmail(), newAccount.getPassword(), 0.0f, 0.0f, LocalDate.now(), "Undefined", 1.2f, "ROLE_USER");
        UserDiet userDiet = new UserDiet(30, 50, 20, 0,0, user);

        user.setId(0);
        userDiet.setId(0);
        userService.saveUser(user, userDiet);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(uri).body(user);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> validateUser(@RequestBody AuthenticationRequest authenticationRequest) {

        if(authenticationRequest.getEmail() != null && authenticationRequest.getPassword() != null) {
            User user = userService.getUserByEmail(authenticationRequest.getEmail());

            if(user.getPassword().equals(authenticationRequest.getPassword())) {

                AuthenticationResponse authenticationResponse = new AuthenticationResponse();
                String token = Jwts.builder()
                        .setSubject(authenticationRequest.getEmail())
                        .claim("email", user.getEmail())
                        .claim("permissions", user.getPermissions())
                        .setIssuedAt(new Date(System.currentTimeMillis()))
                        .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*10))
                        .signWith(SignatureAlgorithm.HS256, "iFuZc|_6D{UBn(A".getBytes(StandardCharsets.UTF_8))
                        .compact();

                authenticationResponse.setToken(token);

                return new ResponseEntity<>(authenticationResponse, HttpStatus.ACCEPTED);
            }
        }

        return new ResponseEntity<>("Unauthorized", HttpStatus.FORBIDDEN);
    }

    @PutMapping("/users")
    public ResponseEntity<User> updateUser(@RequestHeader (name="Authorization") String header, @RequestBody UserUpdateData updatedUser) {
        User oldUserData = userService.getUserFromToken(header);

        oldUserData.setUsername(updatedUser.getUsername());
        oldUserData.setDateOfBirth(updatedUser.getDateOfBirth());
        oldUserData.setHeight(updatedUser.getHeight());
        oldUserData.setWeight(updatedUser.getWeight());
        oldUserData.setGender(updatedUser.getGender());
        oldUserData.setExerciseLevel(updatedUser.getExerciseLevel());

        UserDiet userDiet = userService.getUserDietDetails(oldUserData);
        userDiet.setTotalCalories(new HelperMethods().countTotalCalories(oldUserData));

        userService.saveUser(oldUserData, userDiet);

        return ResponseEntity.ok(oldUserData);
    }

    @DeleteMapping("/users")
    public ResponseEntity<Object> deleteUser(@RequestHeader (name="Authorization") String header) {

        User tempUser = userService.getUserFromToken(header);

        if (tempUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        userService.deleteUser(tempUser.getId());

        return ResponseEntity.noContent().build();
    }
}
