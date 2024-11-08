package com.project.api.controllers;

import java.util.Collections;
import java.util.Map;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.api.dto.LoginDTO;
import com.project.api.dto.RegisterDTO;
import com.project.api.dto.UserDTO;
import com.project.api.services.JWTService;
import com.project.api.services.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag (name ="Login Controller")
@RestController
@RequestMapping("/api/auth")
public class LoginController {

	private JWTService jwtService;
	private UserService userService;

	
	public LoginController(JWTService jwtService, UserService userService) {
		this.jwtService = jwtService;
		this.userService = userService;
	}
	
	
	@Operation(
        summary = "Register a new user",
        description = "This endpoint allows a new user to register by providing an email and password. A JWT token will be generated and returned upon successful registration.",
        responses = {
            @ApiResponse(responseCode = "201", description = "User successfully registered and authenticated", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "409", description = "Email already in use"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResponseEntity<Map<String, String>> registerUser(@RequestBody RegisterDTO registerDTO) {
		try {
		userService.createNewAccount(registerDTO);
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setEmail(registerDTO.getEmail());
		loginDTO.setPassword(registerDTO.getPassword());
		Authentication authentication = userService.authenticateAndStoreUser(loginDTO);
	    String token = jwtService.generateToken(authentication);
	    return ResponseEntity.ok(Collections.singletonMap("token", token));
	} catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Collections.singletonMap("message", "Email already in use."));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonMap("message", "An error occurred."));
    }
	}
	
	
	@Operation(
        summary = "User login",
        description = "This endpoint allows an existing user to log in with email and password. A JWT token is returned upon successful authentication.",
        responses = {
            @ApiResponse(responseCode = "200", description = "User successfully logged in and authenticated", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized - Invalid credentials")
        }
    )
	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK) 
	@ResponseBody
	public ResponseEntity<Map<String, String>> loginUser(@RequestBody LoginDTO loginDTO) {
		try {
			Authentication authentication = userService.authenticateAndStoreUser(loginDTO);
		    String token = jwtService.generateToken(authentication);
		    return ResponseEntity.ok(Collections.singletonMap("token", token));
		} 
		catch (AuthenticationException exception) 
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	
	
	@Operation(
        summary = "Get authenticated user details",
        description = "This endpoint returns the details of the currently authenticated user.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved user details", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "401", description = "Unauthorized - User not authenticated")
        }
    )
	@GetMapping("/me")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<UserDTO> loadAuthenticatedUser(Authentication authentication) 
	{
		UserDTO userDTO = userService.getAuthenticatedUser(authentication);
			return ResponseEntity.ok(userDTO);
	}

	
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
