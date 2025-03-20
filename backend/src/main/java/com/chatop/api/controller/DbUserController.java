package com.chatop.api.controller;

import com.chatop.api.model.DbUser;
import com.chatop.api.model.meDTO;
import com.chatop.api.services.DbUserDetailsService;
import com.chatop.api.services.DbUserService;
import com.chatop.api.utils.JwtUtils;

import jakarta.servlet.http.HttpSession;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/api")
public class DbUserController {

    @Autowired
    private DbUserService dbUserService;

    @Autowired
    private HttpSession httpSession;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private DbUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    

    public Integer getConnectedUserId() {
        Object userIdObject = httpSession.getAttribute("userId");
        return userIdObject != null ? Integer.parseInt(userIdObject.toString()) : null;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/user/{id}") 
    public Object getUserById(@PathVariable Integer id) { 
        return dbUserService.findById(id);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/auth/me")
    public ResponseEntity<meDTO> me(@RequestHeader("Authorization") String authorizationHeader) {
        
        // Check if the Authorization header is valid
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        String token = authorizationHeader.substring(7); // Remove "Bearer "
        
        // Extract username from token
        String username = jwtUtils.extractUsername(token);

        // Find the user by the extracted username
        DbUser user = dbUserService.findByEmail(username);        
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        meDTO dto = new meDTO(user.getId(), user.getEmail(), user.getName(), user.getCreatedAt(), user.getUpdatedAt());

        return new ResponseEntity<>(dto, HttpStatus.OK);
        
    }

    /* @SecurityRequirement(name = "Bearer Authentication")
    public Integer getCurrentUserId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            String currentUserName = authentication.getName();
            DbUser currentUser = dbUserService.findByEmail(currentUserName);
            if (currentUser != null) {
                return currentUser.getId();
            }
        }
        return null;
    } */


    @PostMapping("/auth/register")
    public ResponseEntity<String> registerUser(@RequestBody DbUser user) {
        if(dbUserService.findByEmail(user.getEmail()) != null){
            return new ResponseEntity<>("User already exists", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dbUserService.saveUser(user);
        return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody DbUser user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );

            // Set authentication in the context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

            final String jwt = jwtUtils.generateToken(userDetails);

            //response
            Map<String, Object> response = new HashMap<>();
            response.put("token", jwt);
            response.put("user", dbUserService.findByEmail(user.getEmail()).getId());

            return ResponseEntity.ok(response);
            
        } catch (BadCredentialsException e) {

            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "Invalid credentials");
            errorResponse.put("debugMessage", e.getMessage());
            errorResponse.put("extra1", "Bad credentials for user: " + user.getEmail());
            errorResponse.put("exceptionType", e.getClass().getName());

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
        } catch (Exception e){
            System.out.println("An error occurred during login for user: " + user.getEmail());
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("error", "A login error occurred");
            errorResponse.put("debugMessage", e.getMessage());
            errorResponse.put("exceptionType", e.getClass().getName());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
