package com.project.api.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema (name ="User DTO")
@Data
public class UserDTO {
	    private Integer id;
	    private String name;
	    private String email;	   
		private LocalDateTime created_at;
		private LocalDateTime updated_at;
		

}

