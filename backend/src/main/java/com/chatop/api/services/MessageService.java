package com.chatop.api.services;

import com.chatop.api.model.Message;
import com.chatop.api.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Optional<Message> createMessage(Message message) {
        message.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        message.setUpdatedAt(Timestamp.valueOf(LocalDateTime.now()));
        return Optional.of(messageRepository.save(message));
    }

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

}
