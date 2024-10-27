package com.project.api.services;

import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
	
	
	

}
