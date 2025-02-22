package com.chatop.api.repository;

import com.chatop.api.model.DbUser;

//import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DbUserRepository extends JpaRepository<DbUser, Integer> {
    DbUser findByEmail(String email);
    DbUser findById(int id);
}
