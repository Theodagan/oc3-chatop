package com.chatop.api.controller;

import com.chatop.api.model.Rental;
import com.chatop.api.model.RentalForm;
import com.chatop.api.services.RentalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rentals")
@SecurityRequirement(name = "Bearer Authentication")
public class RentalController {
    
    @Autowired
    private RentalService rentalService;
    
    @Autowired
    private FileController fileController;
    
    @Autowired
    private DbUserController dbUserController;
    
    @GetMapping("")
    @Operation(summary = "Get all rentals", description = "Returns all rentals as ")
    public ResponseEntity<Map<String, List<Object>>> getAllRentals() {

        //[port]-$WEB_HOST 
        String baseImgUrl = "https://3001-idx-oc3-chatop-1737992916895.cluster-qtqwjj3wgzff6uxtk26wj7fzq6.cloudworkstations.dev/";

        List<Rental> rentals = rentalService.getAllRentals(); 
        List<Object> tmpList = new ArrayList<Object>();
        Map<String, List<Object>> response = new HashMap<>();

        //add images to rental objects
        for(Rental rental : rentals){
            rental.setPicture(baseImgUrl + rental.getPicture());
            tmpList.add(parseRentalObject(rental));
        }
        //parse rentals list for correct front-end format
        if(!tmpList.isEmpty()){
            response.put("rentals", tmpList);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a rental by id", description = "Returns a rental as per the id")
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

        rental.setOwnerId(dbUserController.getCurrentUserId());

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

    @PutMapping("/{id}")
    public ResponseEntity<String> updateRental(@PathVariable Integer id, @ModelAttribute RentalForm rentalForm) {
        Rental rental = rentalService.getRentalById(id);
        if(rental == null){
            return new ResponseEntity<>("Rental not found", HttpStatus.NOT_FOUND);
        }

        rental.setName(rentalForm.getName());
        rental.setSurface(rentalForm.getSurface());
        rental.setPrice(rentalForm.getPrice());
        rental.setDescription(rentalForm.getDescription());

        rentalService.saveRental(rental);

        return new ResponseEntity<>("Rental updated", HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Integer id) {
        Rental rental = rentalService.getRentalById(id);
        if(rental == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        rentalService.deleteRental(id);
        return new ResponseEntity<>(HttpStatus.OK);
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
