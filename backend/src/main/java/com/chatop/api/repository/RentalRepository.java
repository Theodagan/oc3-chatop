package com.chatop.api.repository;

import com.chatop.api.model.Rental;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {

    Optional<Rental> findById(Long id);
}
