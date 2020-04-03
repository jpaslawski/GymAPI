package com.example.demo.rest.user;

import com.example.demo.entity.User;
import com.example.demo.rest.ObjectNotFoundException;
import com.example.demo.service.GymService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
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

    @PostMapping("/users")
    public ResponseEntity<User> addUser(@RequestBody User user) {

        // Check if username, email and password is not empty
        if (user.getUsername().isEmpty() || user.getUsername() == null) {
            throw new ObjectNotFoundException("Username cannot be empty, you have to provide one!");
        }

        if (user.getEmail().isEmpty() || user.getEmail() == null) {
            throw new ObjectNotFoundException("Email cannot be empty, you have to provide one!");
        }

        if (user.getPassword().isEmpty() || user.getPassword() == null) {
            throw new ObjectNotFoundException("Password cannot be empty, you have to provide one!");
        }

        // Check if email already in database
        User tempUser = gymService.getUserByEmail(user.getEmail());

        if (tempUser != null) {
            throw new ObjectNotFoundException("An account with this email already exists!");
        }

        user.setId(0);
        gymService.saveUser(user);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(user.getId())
                .toUri();

        return ResponseEntity.created(uri).body(user);
    }

    @PostMapping("/users/validate")
    public ResponseEntity<String> validateUser(@RequestBody JsonNode node) {

        String login = node.get("login").asText();

        String password = node.get("password").asText();

        if (login != null && password != null) {
            User user = gymService.getUserByEmail(login);

            if(user.getPassword().equals(password)) {
                return new ResponseEntity<String>("Authorized", HttpStatus.ACCEPTED);
            }
        }

        return new ResponseEntity<String>("Unauthorized", HttpStatus.FORBIDDEN);
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
