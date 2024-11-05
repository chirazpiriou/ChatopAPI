package com.project.api.controllers;


import java.util.Collections;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.project.api.dto.RentalDTO;
import com.project.api.dto.RentalSubmissionDTO;
import com.project.api.dto.RentalsDTO;
import com.project.api.services.RentalService;



@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<RentalsDTO> getAllRentals() {
        return ResponseEntity.ok(rentalService.getAllRentals());
    }


    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
	public ResponseEntity<RentalDTO> getRentalById(@PathVariable("id") final Integer id) {

		Optional<RentalDTO> rentalDto = rentalService.getRentalById(id);

		if (rentalDto.isPresent()) {
			return ResponseEntity.ok(rentalDto.get());
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object>  addNewRental(@ModelAttribute RentalSubmissionDTO rentalSubmissionDTO ) {
        
        try {
            rentalService.addNewRental(rentalSubmissionDTO);
            return new ResponseEntity<>(Collections.singletonMap("message", "Rental created !"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Error while creating rental: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
     
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> updateRental(@PathVariable("id") final Integer id, @ModelAttribute RentalDTO rentalDTO) {
        try {
            RentalDTO updatedRental = rentalService.updateRental(id, rentalDTO);
            if (updatedRental != null) {
                return new ResponseEntity<>(Collections.singletonMap("message", "Rental updated !"), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(Collections.singletonMap("error", "Rental not found"), HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Error while updating rental: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
