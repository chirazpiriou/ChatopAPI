package com.project.api.controllers;



import org.springframework.http.HttpStatus;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.api.dto.UserDTO;
import com.project.api.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag (name ="User Controller")
@RestController
@RequestMapping("/api/user")

public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

     @Operation(
        summary = "Get user by ID",
        description = "Fetches the details of a user based on the provided user ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user details", content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserDTO.class))),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @GetMapping("/{id}")
      @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public UserDTO getUser(@Parameter(
        description = "ID of the user to retrieve", 
        name = "id", 
        example = "123"
        )@PathVariable("id") final Integer id) {
        return userService.getUserById(id);
    }




}
