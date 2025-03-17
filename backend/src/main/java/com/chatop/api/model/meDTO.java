package com.chatop.api.model;

import lombok.Data;

import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class meDTO {
    @Schema(example = "1", required = true)
    private int id;

    @Schema(example = "John", required = true)
	private String name;

    @Schema(example = "user@example.com", required = true)
	private String email;

    @Schema(required = true)
	private Date created_at;
    
    @Schema(required = true)
	private Date updated_at;

    public meDTO(int id, String email, String name, Date created_at, Date updated_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}

