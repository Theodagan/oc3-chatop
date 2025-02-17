package com.chatop.api.controller;

import com.chatop.api.model.Message;
import com.chatop.api.model.MessageForm;
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

    @PostMapping("/api/messages")
    public ResponseEntity<String> createMessage(@RequestBody MessageForm messageDTO) {

        Message message = new Message();
        message.setRentalId(messageDTO.getRental_id());
        message.setUserId(messageDTO.getUser_id());
        message.setMessage(messageDTO.getMessage());

        message.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        messageService.saveMessage(message);
        
        return new ResponseEntity<>("Rental created", HttpStatus.CREATED);
    }
}
