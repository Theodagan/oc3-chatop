package com.chatop.api.controller;

import com.chatop.api.model.Message;
import com.chatop.api.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/message")
    public ResponseEntity<String> createMessage(@RequestBody Message message) {
        if(message.getMessage() == null || message.getRentalId() == null || message.getUserId() == null) {
            return new ResponseEntity<>("Missing informations", HttpStatus.BAD_REQUEST);
        }

        message.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        messageService.saveMessage(message);
        
        return new ResponseEntity<>("Rental created", HttpStatus.CREATED);
    }
}
