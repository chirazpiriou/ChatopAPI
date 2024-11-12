package com.project.api.services;

import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.api.dto.LoginDTO;
import com.project.api.dto.RegisterDTO;
import com.project.api.dto.UserDTO;
import com.project.api.model.User;
import com.project.api.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	private AuthenticationManager authenticationManager;
	private BCryptPasswordEncoder passwordEncoder;
	private ModelMapper modelMapper;
	
	public UserService (UserRepository userRepository, AuthenticationManager authenticationManager, BCryptPasswordEncoder passwordEncoder ,ModelMapper modelMapper) {
		this.userRepository = userRepository;
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.modelMapper = modelMapper;
		
	}
	
	public UserDTO createNewAccount(RegisterDTO registerDTO) {
		if (userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
			throw new IllegalArgumentException("Email already in use");
		}
		User user = new User();
		user.setEmail( registerDTO.getEmail() );
		user.setName( registerDTO.getName() );
		user.setPassword( passwordEncoder.encode(registerDTO.getPassword()) );
		User userInDatabase = userRepository.save(user);
		return modelMapper.map(userInDatabase, UserDTO.class);
	}
	
	public Authentication authenticateAndStoreUser (LoginDTO loginDTO) {
		try {
			System.out.println("Attempting to authenticate user: " + loginDTO.getEmail());
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken( 
							loginDTO.getEmail(), 
							loginDTO.getPassword())
					);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			System.out.println("User  authenticated successfully: " + loginDTO.getEmail());

		return authentication;
		} catch (BadCredentialsException e) {
			System.out.println("Invalid credentials for user: " + loginDTO.getEmail());
			throw new BadCredentialsException("Invalid username or password");
		} catch (Exception e) {
			e.printStackTrace();
        throw new BadCredentialsException("Invalid username or password");
    }
	}
	
	public UserDTO getAuthenticatedUser(Authentication authentication) {
		String email = authentication.getName();
	    User user = userRepository
	            .findByEmail(email)
	            .orElseThrow(() -> new UsernameNotFoundException("The user cannot be found"));
		
		return modelMapper.map(user, UserDTO.class);
	}
	
	
	public <Optional>UserDTO getUserById(Integer id) {
		User user = userRepository
				.findById(id)
				.orElseThrow(() -> new  NoSuchElementException("No user with id :"+ id +" found"));
		return modelMapper.map(user, UserDTO.class);
		
	}

	
	

}
