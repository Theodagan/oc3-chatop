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
import java.util.HashMap;
import java.util.Map;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@SecurityRequirement(name = "Bearer Authentication")
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/api/messages")
    public ResponseEntity<Object> createMessage(@RequestBody MessageForm messageData) {

        Map<String, Object> response = new HashMap<>();

        Message message = new Message();
        message.setRentalId(messageData.getRental_id());
        message.setUserId(messageData.getUser_id());
        message.setMessage(messageData.getMessage());

        if (message.getUserId() == null || message.getRentalId() == null || message.getMessage() == null ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        message.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        messageService.saveMessage(message);

        response.put("message", "Message sent with success");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
