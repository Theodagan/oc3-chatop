package com.chatop.api.dto;
import lombok.Data;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class RentalListDTO {

    @Schema(required = true)
	private List<RentalDTO> rentals;

    public RentalListDTO(List<RentalDTO> rentals){
        this.rentals = rentals;
    }
}
