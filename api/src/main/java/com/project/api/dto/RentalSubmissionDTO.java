package com.project.api.dto;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Schema (name ="Rental Submission DTO")
@Data
public class RentalSubmissionDTO {
    private Integer id;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;


    @Positive(message = "Surface must be greater than 0")
    private Integer surface;


    @Positive(message = "Price must be greater than 0")
    private Integer price;


    private MultipartFile picture;

    @Size(min = 1, message = "Description must be at least 1 character")
    private String description;

    private Integer owner_id;


}


