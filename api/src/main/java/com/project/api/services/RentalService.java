package com.project.api.services;

import org.springframework.stereotype.Service;

import com.project.api.repository.RentalRepository;



@Service
public class RentalService {
private RentalRepository rentalRepository;
	
	public RentalService (RentalRepository rentalRepository) {
		this.rentalRepository = rentalRepository;
		
	}

}
