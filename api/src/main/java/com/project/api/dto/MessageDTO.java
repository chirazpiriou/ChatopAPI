package com.project.api.dto;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema (name ="Message DTO")
@Data
public class MessageDTO {

    private Integer id;
    private String message;
    private Integer rental_id;
    private Integer user_id;
	private LocalDateTime created_at;
	private LocalDateTime updated_at;
	
}
