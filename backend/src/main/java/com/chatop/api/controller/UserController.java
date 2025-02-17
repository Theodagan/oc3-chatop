package com.chatop.api.controller;

import com.chatop.api.model.User;
import com.chatop.api.services.UserService;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    //TEST --------------------------------------------------------------
    @GetMapping("/user/test")
    public String test() { 
        //return "coucou les patissiers";
        Iterable<User> allUsers = userService.getAllUsers();
        if(!allUsers.iterator().hasNext()){
            System.out.println("No users found in the database.");
            return "No users found in the database.";
        }
        for(User user : allUsers){
            System.out.println("coucou" + user.getName());
        } return "testtttt <br> test";
    }

    //TEST --------------------------------------------------------------
    @GetMapping("/user/lau") //listAllUsers
    public String lau() { 
        String returnString = "";
        Iterable<User> allUsers = userService.getAllUsers();
        if(!allUsers.iterator().hasNext()){
            return "No users found in the database.";
        }
        for(User user : allUsers){
            returnString += user.toString() + "<br><br>";
        } 
        return returnString;
    }

    @GetMapping("/user/{id}") 
    public Object getUserById(@PathVariable Integer id) { 
        return userService.findById(id);
    }

    @GetMapping("/auth/me")
    public ResponseEntity<User> me(@RequestHeader("Authorization") String authorizationHeader) {
        
        String token = authorizationHeader.substring(7); // Remove "Bearer "
        
        User user = validateToken(token);
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        return new ResponseEntity<>(user, HttpStatus.OK);
        
    }

    @PostMapping("/auth/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        if(userService.findByEmail(user.getEmail()) != null){
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }
        userService.saveUser(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody User user) {

        User foundUser = userService.findByEmail(user.getEmail());

        if (foundUser == null || !foundUser.getPassword().equals(user.getPassword())) {
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("message", "Unauthorized");
            return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("user", foundUser);
        response.put("token", generateToken(foundUser));

        return new ResponseEntity<>(response, HttpStatus.OK);
    }





    private String generateToken(User user){
        //TODO : implement real token logic

        return "token" + user.getId().toString();
    }

    private User validateToken(String token) {
        //TODO : implement real token logic

        int tokenId = Integer.parseInt(token.substring(5));
        return userService.findById(tokenId);
    }

}
