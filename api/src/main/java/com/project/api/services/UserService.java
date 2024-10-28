package com.project.api.services;

import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
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
	
	public void createNewAccount(RegisterDTO registerDTO) {
		User user = new User();
		user.setEmail( registerDTO.getEmail() );
		user.setName( registerDTO.getName() );
		user.setPassword( passwordEncoder.encode(registerDTO.getPassword()) );
		User userInDatabase = userRepository.save(user);
		modelMapper.map(userInDatabase, UserDTO.class);
	}
	
	public Authentication authenticateAndStoreUser (LoginDTO loginDTO) {
		Authentication authentication = authenticationManager.authenticate(
				UsernamePasswordAuthenticationToken( 
						loginDTO.getEmail(), 
						loginDTO.getPassword())
				);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		return authentication;
	}
	
	public UserDTO getAuthenticatedUser(Authentication authentication) {
		String email = authentication.getName();
	    User user = userRepository
	            .findByEmail(email)
	            .orElseThrow(() -> new UsernameNotFoundException("The user cannot be found"));
		
		return modelMapper.map(user, UserDTO.class);
	}
	
	
	public UserDTO getUserById(Integer id) {
		User user = userRepository
				.findById(id)
				.orElseThrow(() -> new  NoSuchElementException("No user with id :"+ id +" found"));
		return modelMapper.map(user, UserDTO.class);
		
	}

	
	

}
