package com.project.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema (name ="Rentals DTO")
@Data
public class RentalsDTO {
    private Iterable<RentalDTO> rentals;

}
