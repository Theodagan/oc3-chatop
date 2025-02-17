package com.chatop.api.model;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RentalForm {
    private String name;
    private Double surface;
    private Double price;
    private String description;
    private Integer ownerId;
    private MultipartFile picture;

}