package com.chatop.api.model;

import lombok.Data;

import java.util.Date;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class meDTO {
    private int id;
	private String name;
	private String email;
	private Date created_at;
	private Date updated_at;

    public meDTO(int id, String email, String name, Date created_at, Date updated_at) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }
}

