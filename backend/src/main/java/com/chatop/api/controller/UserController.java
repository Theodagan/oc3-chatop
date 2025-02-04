package com.chatop.api.controller;

import com.chatop.api.model.User;
import com.chatop.api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import java.util.Optional;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public User getUser() {
        return userService.getConnectedUser();
    }


    @PostMapping("/auth/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        userService.saveUser(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<String> login(@RequestBody User user) {
        User foundUser = userService.findByEmail(user.getEmail());
        if (foundUser == null || !foundUser.getPassword().equals(user.getPassword())) {
            return new ResponseEntity<>("Unauthorized", HttpStatus.UNAUTHORIZED);
        }

        return new ResponseEntity<>("User logged in successfully", HttpStatus.OK);
    }




    // Add other endpoints (PUT, DELETE) as needed...
    //GET /user: Get the connected user's information.
    //POST /auth/register: Register a new user.
    //POST /auth/login: Authenticate a user (login).

}
