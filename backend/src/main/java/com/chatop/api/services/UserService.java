package com.chatop.api.services;

import org.springframework.beans.factory.annotation.Autowired;

import com.chatop.api.model.User;
import com.chatop.api.repository.UserRepository;

public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getConnectedUser() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getConnectedUser'");
    }


    public void saveUser(User user) {
        if(user != null){
            userRepository.save(user);
        }
        throw new UnsupportedOperationException("User not found");
    }


    public User findByEmail(String email) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findByEmail'");
    }


}
