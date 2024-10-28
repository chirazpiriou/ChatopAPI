package com.project.api.dto;

import java.time.LocalDateTime;


import lombok.Data;

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
