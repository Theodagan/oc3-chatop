package com.chatop.api.dto;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MessageDTO {
    @Schema(example = "This is a message destined to the creator of the rental", required = true)
	private String message;

    public MessageDTO(String message){
        this.message = message;
    }
}
