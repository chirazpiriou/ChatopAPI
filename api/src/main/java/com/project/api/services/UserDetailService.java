package com.project.api.services;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.project.api.model.User;
import com.project.api.repository.UserRepository;

@Service
public class UserDetailService implements UserDetailsService {
	 private final UserRepository userRepository;

	    public UserDetailService(UserRepository userRepository) {
	        this.userRepository = userRepository;
	    }

	    @Override
	    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        User user = userRepository.findAll().stream()
	                .filter(u -> u.getEmail().equals(username))
	                .findFirst()
	                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
	                .password(user.getPassword())
	                .authorities(new ArrayList<>())
	                .build();
	    }

}
