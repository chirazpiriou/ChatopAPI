package com.project.api.services;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.project.api.dto.RentalDTO;
import com.project.api.dto.RentalSubmissionDTO;
import com.project.api.dto.RentalsDTO;
import com.project.api.model.Rental;
import com.project.api.model.User;
import com.project.api.repository.RentalRepository;
import com.project.api.repository.UserRepository;

import org.modelmapper.ModelMapper;



@Service
public class RentalService {
private RentalRepository rentalRepository;
private ModelMapper modelMapper;
private UserRepository userRepository;
private String serverUrl = "http://localhost:3001/pictures";




	
	public RentalService (RentalRepository rentalRepository, ModelMapper modelMapper,  UserRepository userRepository) {
		this.rentalRepository = rentalRepository;
		this.modelMapper = modelMapper;
        this.userRepository = userRepository;
		
	}

	//Return all rentals
	public RentalsDTO getAllRentals() {
        
		List<Rental> rentals = rentalRepository.findAll();



		List<RentalDTO> rentalsDTOList = rentals.stream()
        .map(rental -> {RentalDTO rentalDTO = modelMapper.map(rental, RentalDTO.class);
        if (rental.getOwner() != null) {
            rentalDTO.setOwner_id(rental.getOwner().getId());
        }
        return rentalDTO;
        }).toList();
		RentalsDTO rentalsDTO = new RentalsDTO();
		rentalsDTO.setRentals(rentalsDTOList);
		return rentalsDTO;
		
	}

	//Get rental by Id
	public Optional<RentalDTO> getRentalById(Integer id) {
		Optional<Rental> rentalOpt = rentalRepository.findById(id);
		if (rentalOpt.isEmpty()){
			return Optional.empty();
		}
        Rental rental = rentalOpt.get();
        RentalDTO rentalDTO = modelMapper.map(rental, RentalDTO.class);
        if (rental.getOwner() != null) {
            rentalDTO.setOwner_id(rental.getOwner().getId());
        }
		return Optional.of((rentalDTO));
		
	}

	//Add new rental
	public void addNewRental(RentalSubmissionDTO rentalSubmissionDTO ) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName(); 
            User authenticatedOwner = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
            Integer ownerId = authenticatedOwner.getId(); 
            


            MultipartFile picture = rentalSubmissionDTO.getPicture();
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(picture.getOriginalFilename()));
            Path path = Paths.get("src/main/resources/static/pictures/" + fileName);
            Files.copy(picture.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            String pictureUrl = serverUrl + "/" + fileName;
    
            RentalDTO rentalDTO = new RentalDTO();
    
            rentalDTO.setId(rentalSubmissionDTO.getId());
            rentalDTO.setName(rentalSubmissionDTO.getName());
            rentalDTO.setSurface(rentalSubmissionDTO.getSurface());
            rentalDTO.setPrice(rentalSubmissionDTO.getPrice());
            rentalDTO.setPicture(pictureUrl);
            rentalDTO.setDescription(rentalSubmissionDTO.getDescription());
            rentalDTO.setOwner_id(ownerId);
            rentalDTO.setCreated_at(LocalDateTime.now());
            rentalDTO.setUpdated_at(LocalDateTime.now());
    
    
            
            Rental rental = modelMapper.map(rentalDTO, Rental.class);
            rental.setOwner(authenticatedOwner);
            rental = rentalRepository.save(rental);


        } catch (Exception e) {
            throw new RuntimeException("Error while creating rental: " + e.getMessage());
        }

     

    
	}


	//Update Rental 
	public RentalDTO updateRental(Integer id, RentalDTO rentalDTO) {
        Optional<Rental> rentalOpt = rentalRepository.findById(id);
        if (rentalOpt.isPresent()) {
            Rental rental = rentalOpt.get();
            LocalDateTime originalCreatedAt = rental.getCreated_at(); 
            
            rentalDTO.setOwner_id(rental.getOwner().getId());
            rentalDTO.setPicture(rental.getPicture());
            Integer originalId = rental.getId(); 
            modelMapper.map(rentalDTO, rental);
            
            rental.setId(originalId); 
            rental.setCreated_at(originalCreatedAt); 
            rental.setUpdated_at(LocalDateTime.now());
            Rental savedRental = rentalRepository.save(rental);
            RentalDTO rentaldto = modelMapper.map(savedRental, RentalDTO.class);


            return rentaldto ;
        } else {
            return null;
        }
    }


}
