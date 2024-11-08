package com.project.api.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema (name ="Login DTO")
@Data
public class LoginDTO {
    private String email;
    private String password;


}
