package com.chatop.api.controller;

import com.chatop.api.model.User;
import com.chatop.api.services.UserService;

import jakarta.servlet.http.HttpSession;

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

    @Autowired
    private HttpSession httpSession;

    public Integer getConnectedUserId() {
        Object userIdObject = httpSession.getAttribute("userId");
        return userIdObject != null ? Integer.parseInt(userIdObject.toString()) : null;
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

        httpSession.setAttribute("userId", foundUser.getId());

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
        //getUserByToken(token)

        int tokenId = Integer.parseInt(token.substring(5));

        return userService.findById(tokenId);
    }

}
