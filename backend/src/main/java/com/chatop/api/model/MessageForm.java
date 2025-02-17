package com.chatop.api.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageForm {
    private String message;
    private Integer rental_id;
    private Integer user_id;
}