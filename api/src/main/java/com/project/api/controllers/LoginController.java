package com.project.api.controllers;

import java.util.Collections;
import java.util.Map;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.api.dto.LoginDTO;
import com.project.api.dto.RegisterDTO;
import com.project.api.services.JWTService;
import com.project.api.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

	private JWTService jwtService;
	private UserService userservice;

	
	public LoginController(JWTService jwtService, UserService userservice) {
		this.jwtService = jwtService;
		this.userservice = userservice;
	}
	
	
	
	@PostMapping("/register")
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public ResponseEntity<Map<String, String>> registerUser(@RequestBody RegisterDTO registerDTO) {
		userservice.createNewAccount(registerDTO);
		LoginDTO loginDTO = new LoginDTO();
		loginDTO.setEmail(registerDTO.getEmail());
		loginDTO.setPassword(registerDTO.getPassword());
		Authentication authentication = userservice.authenticateAndStoreUser(loginDTO);
	    String token = jwtService.generateToken(authentication);
	    return ResponseEntity.ok(Collections.singletonMap("token", token));
	}
	
	
	@PostMapping("/login")
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	public ResponseEntity<Map<String, String>> loginUser(@RequestBody LoginDTO loginDTO) {
		try {
			Authentication authentication = userservice.authenticateAndStoreUser(loginDTO);
		    String token = jwtService.generateToken(authentication);
		    return ResponseEntity.ok(Collections.singletonMap("token", token));
		} 
		catch (AuthenticationException exception) 
		{
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		}
	}
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
