package com.chatop.api.controller;

import com.chatop.api.model.Rental;
import com.chatop.api.model.RentalForm;
import com.chatop.api.services.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private FileController fileController;

    @GetMapping("")
    public ResponseEntity<Map<String, List<Object>>> getAllRentals() {

        List<Rental> rentals = rentalService.getAllRentals(); 
        List<Object> tmpList = new ArrayList<Object>();
        Map<String, List<Object>> response = new HashMap<>();

        for(Rental rental : rentals){
            tmpList.add(parseRentalObject(rental));
        }
        if(!tmpList.isEmpty()){
            response.put("rentals", tmpList);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getRentalById(@PathVariable Integer id) {
        Rental rental = rentalService.getRentalById(id);
        if (rental != null) {
            return new ResponseEntity<>(parseRentalObject(rental), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<String> createRental(@ModelAttribute RentalForm rentalForm) {
        Rental rental = new Rental();
        rental.setName(rentalForm.getName());
        rental.setSurface(rentalForm.getSurface());
        rental.setPrice(rentalForm.getPrice());
        rental.setDescription(rentalForm.getDescription());
        //TODO : getConnectedUSerId here
        rental.setOwnerId(7); //PLACEHOLDER

        MultipartFile file = rentalForm.getPicture();
        if (file != null && !file.isEmpty()) {
            String imgUrl = fileController.handleFileUpload(file).getBody();
            rental.setPicture(imgUrl);
        } else {
            return new ResponseEntity<>("No file uploaded", HttpStatus.BAD_REQUEST);
        }

        rentalService.saveRental(rental);
        return new ResponseEntity<>("Rental created", HttpStatus.CREATED);
    }

    @PutMapping("/rentals/{id}")
    public ResponseEntity<Rental> updateRental(@PathVariable Integer id, @RequestBody Rental rental) {
        
        throw new UnsupportedOperationException("updateRental not implemented");
    }

    @DeleteMapping("/rentals/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Integer id) {
        throw new UnsupportedOperationException("deleteRental not implemented");
    }

    private Object parseRentalObject (Rental rental){
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
