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

    @GetMapping("/test")
    public String test() { 
        //return "coucou les patissiers";
        Iterable<User> allUsers = userService.getAllUsers();
        if(!allUsers.iterator().hasNext()){
            System.out.println("No users found in the database.");
            return "No users found in the database.";
        }
        for(User user : allUsers){
            System.out.println("coucou");
            System.out.println(user.toString());
        } return "testtttt";
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


    //GET /user: Get the connected user's information.
    //POST /auth/register: Register a new user.
    //POST /auth/login: Authenticate a user (login).

}
