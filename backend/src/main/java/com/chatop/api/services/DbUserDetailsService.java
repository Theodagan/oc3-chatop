package com.chatop.api.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import com.chatop.api.model.DbUser;
import java.util.ArrayList;

@Service
public class DbUserDetailsService implements UserDetailsService {

    private final DbUserService dbUserService;

    public DbUserDetailsService(DbUserService dbUserService) {
        this.dbUserService = dbUserService;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        DbUser user = dbUserService.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), new ArrayList<>());
    }
}
