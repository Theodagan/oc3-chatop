package com.chatop.api.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageForm {
    @Schema(example = "This is a message", required = true)
    private String message;
    @Schema(example = "1", required = true)
    private Integer rental_id;
    @Schema(example = "1", required = true)
    private Integer user_id;
}