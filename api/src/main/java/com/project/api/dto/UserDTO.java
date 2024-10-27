package com.project.api.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class UserDTO {
	    private Integer id;
	    private String name;
	    private String email;	   
		private LocalDateTime created_at;
		private LocalDateTime updated_at;
		
}

