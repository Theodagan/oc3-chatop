package com.chatop.api.controller;

import com.chatop.api.dto.RentalDTO;
import com.chatop.api.dto.RentalListDTO;
import com.chatop.api.dto.ResponseDTO;
import com.chatop.api.model.Rental;
import com.chatop.api.model.RentalForm;
import com.chatop.api.services.DbUserService;
import com.chatop.api.services.FileService;
import com.chatop.api.services.RentalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/rentals")
@SecurityRequirement(name = "Bearer Authentication")
public class RentalController {
    
    @Autowired
    private RentalService rentalService;
    
    @Autowired
    private FileService fileService;

    @Autowired 
    private DbUserService dbUserService;
    
    @GetMapping("")
    @Operation(summary = "Get all rentals", description = "Returns all rentals as a list")
    public ResponseEntity<RentalListDTO> getAllRentals() {

        String baseImgUrl = "https://3001-idx-oc3-chatop-1737992916895.cluster-qtqwjj3wgzff6uxtk26wj7fzq6.cloudworkstations.dev/";

        List<Rental> rentals = rentalService.getAllRentals(); 
        List<RentalDTO> tmpList = new ArrayList<RentalDTO>();

        //add images to rental objects
        for(Rental rental : rentals){
            rental.setPicture(baseImgUrl + rental.getPicture());
            RentalDTO rentalDTO = new RentalDTO(rental.getId(), rental.getName(), rental.getSurface(), rental.getPrice(), rental.getPicture(), rental.getDescription(), rental.getOwnerId(), rental.getCreatedAt(), rental.getUpdatedAt());
            tmpList.add(rentalDTO);
        }

        if(tmpList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // should be a BAD_REQUEST but the 400 response is not part of the mockoon
        }

        RentalListDTO response = new RentalListDTO(tmpList);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a rental by id", description = "Returns a rental as per the id")
    public ResponseEntity<RentalDTO> getRentalById(@PathVariable Integer id) {

        String baseImgUrl = "https://3001-idx-oc3-chatop-1737992916895.cluster-qtqwjj3wgzff6uxtk26wj7fzq6.cloudworkstations.dev/";
        Rental rental = rentalService.getRentalById(id);
       
        if (rental != null) {
            rental.setPicture(baseImgUrl + rental.getPicture());
            RentalDTO response = new RentalDTO(rental.getId(), rental.getName(), rental.getSurface(), rental.getPrice(), rental.getPicture(), rental.getDescription(), rental.getOwnerId(), rental.getCreatedAt(), rental.getUpdatedAt());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<ResponseDTO> createRental(@ModelAttribute RentalForm rentalForm) {
        Rental rental = new Rental();
        rental.setName(rentalForm.getName());
        rental.setSurface(rentalForm.getSurface());
        rental.setPrice(rentalForm.getPrice());
        rental.setDescription(rentalForm.getDescription());

        rental.setOwnerId(dbUserService.getCurrentUserId());

        MultipartFile file = rentalForm.getPicture();
        if (file != null && !file.isEmpty()) {
            String imgUrl = fileService.handleFileUpload(file);

            if(!imgUrl.contains("/")){ // test if the relative path is valid or not
                return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // should be a BAD_REQUEST but the 400 response is not part of the mockoon
            }

            rental.setPicture(imgUrl);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // should be a BAD_REQUEST but the 400 response is not part of the mockoon
        }

        rentalService.saveRental(rental);
        ResponseDTO response = new ResponseDTO("Rental created");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO> updateRental(@PathVariable Integer id, @ModelAttribute RentalForm rentalForm) {

        Rental rental = rentalService.getRentalById(id);
        if(rental == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // should be a NOT_FOUND but the 404 response is not part of the mockoon
        }

        rental.setName(rentalForm.getName());
        rental.setSurface(rentalForm.getSurface());
        rental.setPrice(rentalForm.getPrice());
        rental.setDescription(rentalForm.getDescription());

        rentalService.saveRental(rental);
        ResponseDTO response = new ResponseDTO("Rental updated");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO> deleteRental(@PathVariable Integer id) {
        Rental rental = rentalService.getRentalById(id);
        if(rental == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        rentalService.deleteRental(id);
        ResponseDTO response = new ResponseDTO("Rental deleted");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
