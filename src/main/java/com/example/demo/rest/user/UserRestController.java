package com.example.demo.rest.user;

import com.example.demo.entity.request.AuthenticationRequest;
import com.example.demo.entity.request.AuthenticationResponse;
import com.example.demo.entity.User;
import com.example.demo.entity.request.UserData;
import com.example.demo.rest.ObjectNotFoundException;
import com.example.demo.service.GymService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class UserRestController {

    @Autowired
    private GymService gymService;

    @GetMapping("/users")
    public ResponseEntity<List<User>> getUsers() {

        List<User> userList = gymService.getUsers();

        if(userList.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(userList);
    }

    // path = "/users?email=...
    @GetMapping(value = "/users", params = "email", produces="application/json")
    public ResponseEntity<User> getUserByEmail(@RequestParam("email") String userEmail) {

        User user = gymService.getUserByEmail(userEmail);

        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<User> getUser(@PathVariable int userId) {

        User user = gymService.getUser(userId);

        if (user == null) {
            throw new ObjectNotFoundException("User id not found - " + userId);
        }
        return ResponseEntity.ok(user);
    }

    @PostMapping("/create")
    public ResponseEntity<User> addUser(@RequestBody UserData newAccount) {

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
        User tempUser = gymService.getUserByEmail(newAccount.getEmail());

        if (tempUser != null) {
            throw new ObjectNotFoundException("An account with this email already exists!");
        }

        User user = new User(newAccount.getUsername(), newAccount.getEmail(), newAccount.getPassword(), 0.0f, 0.0f, 0, "ROLE_USER");

        user.setId(0);
        gymService.saveUser(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(uri).body(user);
    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> validateUser(@RequestBody AuthenticationRequest authenticationRequest) {

        if(authenticationRequest.getEmail() != null && authenticationRequest.getPassword() != null) {
            User user = gymService.getUserByEmail(authenticationRequest.getEmail());

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
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        gymService.saveUser(user);

        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable int userId) {

        User tempUser = gymService.getUser(userId);

        if (tempUser == null) {
            throw new ObjectNotFoundException("User id not found - " + userId);
        }

        gymService.deleteUser(userId);

        return ResponseEntity.noContent().build();
    }
}
