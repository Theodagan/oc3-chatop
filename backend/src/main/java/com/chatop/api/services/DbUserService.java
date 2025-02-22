package com.chatop.api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chatop.api.model.DbUser;
import com.chatop.api.repository.DbUserRepository;

@Service
public class DbUserService {

    @Autowired
    private DbUserRepository userRepository;

    public Iterable<DbUser> getAllDbs() {
        return userRepository.findAll();
    }
    
    public void saveUser(DbUser user) {
        if(user != null){
            userRepository.save(user);
            return;
        }
        throw new UnsupportedOperationException("User is null");
    }




    public DbUser findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public DbUser findById(int id) {
        return userRepository.findById(id);
    }


}
