package com.chatop.api.services;

import com.chatop.api.model.Rental;
import com.chatop.api.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RentalService {

    @Autowired
    private RentalRepository rentalRepository;

    public Iterable<Rental> getRentals() {
        return rentalRepository.findAll();
    }

    public Rental getRentalById(Integer id) {
        Optional<Rental> optionalRental = rentalRepository.findById(id);
        if(optionalRental.isPresent()){
            return optionalRental.get();
        }else return null;
    }

    public Rental saveRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    public List<Rental> getAllRentals() {
        //TODO : dvp getAllRentals
        throw new UnsupportedOperationException("Unimplemented method 'getAllRentals'");
    }
}
