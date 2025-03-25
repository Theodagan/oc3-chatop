package com.chatop.api.controller;

import com.chatop.api.dto.TokenDTO;
import com.chatop.api.dto.UserDTO;
import com.chatop.api.model.DbUser;
import com.chatop.api.services.DbUserDetailsService;
import com.chatop.api.services.DbUserService;
import com.chatop.api.utils.JwtUtils;

import jakarta.servlet.http.HttpSession;

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
@SecurityRequirement(name = "Bearer Authentication")
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
    public ResponseEntity<UserDTO> getUserById(@PathVariable Integer id, @RequestHeader("Authorization") String authorizationHeader) { 
        // Check if the Authorization header is valid
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        
        // Find the user by the id 
        DbUser user = dbUserService.findById(id);        
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // should be a BAD_REQUEST but the 400 response is not part of the mockoon
        }

        UserDTO dto = new UserDTO(user.getId(), user.getEmail(), user.getName(), user.getCreatedAt(), user.getUpdatedAt());

        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @GetMapping("/auth/me")
    public ResponseEntity<UserDTO> me(@RequestHeader("Authorization") String authorizationHeader) {
        
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
        
        UserDTO dto = new UserDTO(user.getId(), user.getEmail(), user.getName(), user.getCreatedAt(), user.getUpdatedAt());

        return new ResponseEntity<>(dto, HttpStatus.OK);
        
    }

    @PostMapping("/auth/register")
    public ResponseEntity<TokenDTO> registerUser(@RequestBody DbUser user) {
        if(dbUserService.findByEmail(user.getEmail()) != null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dbUserService.saveUser(user);
        
        final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        final String jwt = jwtUtils.generateToken(userDetails);

        TokenDTO response = new TokenDTO(jwt);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/auth/login")
    public ResponseEntity<TokenDTO> login(@RequestBody DbUser user) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword())
            );

            // Set authentication in the context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            final UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());

            final String jwt = jwtUtils.generateToken(userDetails);

            //response
            TokenDTO response = new TokenDTO(jwt);

            return ResponseEntity.ok(response);
            
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }
}
