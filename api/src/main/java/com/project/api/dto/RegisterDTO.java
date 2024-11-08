package com.project.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema (name ="Register DTO")
@Data
public class RegisterDTO {
    private String name;
    private String email;
    private String password;


}
