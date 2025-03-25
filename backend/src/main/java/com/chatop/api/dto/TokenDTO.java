package com.chatop.api.dto;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TokenDTO {
    @Schema(required = true)
	private String token;

    public TokenDTO(String token){
        this.token = token;
    }
}
