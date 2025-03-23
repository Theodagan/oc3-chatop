package com.chatop.api.services;

import com.chatop.api.model.Rental;
import com.chatop.api.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
        return rentalRepository.findAll();
    }

    public void deleteRental(Integer id) {
        Optional<Rental> rental = rentalRepository.findById(id);
        if(rental.isPresent()){
            rentalRepository.deleteById(id);
        }else throw new RuntimeException("Rental not found");
    }

    public Object parseRentalObject (Rental rental){
        Map<String, Object> parsedObject = new HashMap<>();

        // Renaming field to match rentalResponse.interface.ts
        parsedObject.put("id", rental.getId()); 
        parsedObject.put("name", rental.getName()); 
        parsedObject.put("surface", rental.getSurface()); 
        parsedObject.put("price", rental.getPrice()); 
        parsedObject.put("picture", rental.getPicture()); 
        parsedObject.put("description", rental.getDescription()); 
        parsedObject.put("owner_id", rental.getOwnerId()); 
        parsedObject.put("created_at", rental.getCreatedAt()); 
        parsedObject.put("updated_at", rental.getUpdatedAt()); 

        return parsedObject; 
    }
}
