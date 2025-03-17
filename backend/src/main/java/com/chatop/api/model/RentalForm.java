package com.chatop.api.model;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RentalForm {
    @Schema(example = "Spacious House", required = true)
    private String name;
    @Schema(example = "123.5", required = true)
    private Double surface;
    @Schema(example = "1203.00", required = true)
    private Double price;
    @Schema(example = "This a description for a Rental", required = true)
    private String description;
    @Schema( example = "1", required = true)
    private Integer ownerId;
    @Schema( required = true)
    private MultipartFile picture;

}