package com.chatop.api.dto;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResponseDTO {
    @Schema(example = "This is a message destined to the front-end", required = true)
	private String message;

    public ResponseDTO(String message){
        this.message = message;
    }
}
