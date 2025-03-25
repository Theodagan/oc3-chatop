package com.chatop.api.dto;
import lombok.Data;

import java.sql.Timestamp;

//import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RentalDTO {
    @Schema(example = "1", required = true)
    private int id;

    @Schema(example = "John", required = true)
	private String name;

    @Schema(example = "100.00", required = true)
    private Double surface;

    @Schema(example = "100.00", required = true)
    private Double price;

    @Schema(example = "https://path-to-picture.com/picture.jpg", required = true)
    private String picture;

    @Schema(example = "This a description ", required = true)
    private String description;

    @Schema(example = "1", required = true)
    private int owner_id;

    @Schema(required = true)
    private Timestamp created_at;

    @Schema(required = true)
    private Timestamp updated_at;


    public RentalDTO(int id, String name, Double surface, Double price, String picture, String description, int ownerId, Timestamp createdAt, Timestamp updatedAt){
        this.id = id;
        this.name = name;
        this.surface = surface;
        this.price = price;
        this.picture = picture;
        this.description = description;
        this.owner_id = ownerId;
        this.created_at = createdAt;
        this.updated_at = updatedAt;
    }
}
