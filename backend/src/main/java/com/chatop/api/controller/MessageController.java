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
import java.util.HashMap;
import java.util.Map;

@RestController
public class MessageController {
    @Autowired
    private MessageService messageService;

    @PostMapping("/message")
    public ResponseEntity<?> createMessage(@RequestBody Message message) {
        if(message.getMessage() == null || message.getRentalId() == null || message.getUserId() == null) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Missing informations");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }

        message.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        messageService.saveMessage(message);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Message send with success");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
