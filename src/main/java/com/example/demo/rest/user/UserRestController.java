package com.example.demo.rest.user;

import com.example.demo.entity.User;
import com.example.demo.rest.ObjectNotFoundException;
import com.example.demo.service.GymService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserRestController {

    @Autowired
    private GymService gymService;

    @GetMapping("/users")
    public List<User> getUsers() {

        return gymService.getUsers();
    }

    // path = "/users?email=...
    @GetMapping(value = "/users", params = "email", produces="application/json")
    public User getUserByEmail(@RequestParam("email") String userEmail) {

        User user = gymService.getUserByEmail(userEmail);

        if (user == null) {
            return null;
        }

        return user;
    }

    @GetMapping("/users/{userId}")
    public User getUser(@PathVariable int userId) {

        User user = gymService.getUser(userId);

        if (user == null) {
            throw new ObjectNotFoundException("User id not found - " + userId);
        }
        return user;
    }

    @PostMapping("/users")
    public User addUser(@RequestBody User user) {

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
        User tempUser = getUserByEmail(user.getEmail());

        if (tempUser == null) {
            throw new ObjectNotFoundException("An account with this email already exists!");
        }

        user.setId(0);
        gymService.saveUser(user);

        return user;
    }

    @PostMapping("/users/validate")
    public boolean validateUser(@RequestBody JsonNode node) {

        String login = node.get("login").asText();

        String password = node.get("password").asText();

        if (login != null && password != null) {
            User user = gymService.getUserByEmail(login);

            return user.getPassword().equals(password);
        }
        return false;
    }

    @PutMapping("/users")
    public User updateUser(@RequestBody User user) {
        gymService.saveUser(user);
        return user;
    }

    @DeleteMapping("/users/{userId}")
    public Boolean deleteUser(@PathVariable int userId) {

        User tempUser = gymService.getUser(userId);

        if (tempUser == null) {
            throw new ObjectNotFoundException("User id not found - " + userId);
        }

        gymService.deleteUser(userId);

        return true;
    }
}
