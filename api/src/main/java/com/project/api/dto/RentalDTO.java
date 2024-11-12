package com.project.api.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema (name ="Rental DTO")
@Data
public class RentalDTO {
	 private Integer id;
	 private String name;
	 private Integer surface;
	 private Integer price;
	 private String picture;
	 private String description;
	 private Integer owner_id;
	 private LocalDateTime created_at;
	 private LocalDateTime updated_at;
	 
	 
}
