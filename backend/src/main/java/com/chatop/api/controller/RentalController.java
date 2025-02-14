package com.chatop.api.controller;

import com.chatop.api.model.Rental;
import com.chatop.api.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @GetMapping("/rentals")
    public ResponseEntity<List<Rental>> getAllRentals() {
        // List<Rental> rentals = rentalService.getAllRentals();
        // return new ResponseEntity<>(rentals, HttpStatus.OK);
        return null;
    }

    @GetMapping("/rentals/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Integer id) {
        Rental rental = rentalService.getRentalById(id);
        if (rental != null) {
            return new ResponseEntity<>(rental, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/rentals")
    public ResponseEntity<Rental> createRental(@RequestBody Rental rental) {
        Rental savedRental = rentalService.saveRental(rental);
        return new ResponseEntity<>(savedRental, HttpStatus.CREATED);
    }

    @PutMapping("/rentals/{id}")
    public ResponseEntity<Rental> updateRental(@PathVariable Integer id, @RequestBody Rental rental) {
        throw new UnsupportedOperationException("updateRental not implemented");
    }

    @DeleteMapping("/rentals/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Integer id) {
        throw new UnsupportedOperationException("deleteRental not implemented");
    }

}
