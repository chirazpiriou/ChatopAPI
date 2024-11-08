package com.project.api.controllers;


import java.util.Collections;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;


@Tag (name ="Rental Controller")
@Validated
@RestController
@RequestMapping("/api/rentals")
public class RentalController {

    private final RentalService rentalService;

    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @Operation(
        summary = "Get all rentals",
        description = "Fetches a list of all rentals available.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved rentals", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalsDTO.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @GetMapping("")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<RentalsDTO> getAllRentals() {
        return ResponseEntity.ok(rentalService.getAllRentals());
    }


    @Operation(
        summary = "Get rental by ID",
        description = "Fetch a rental by its unique ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Rental found", content = @Content(mediaType = "application/json", schema = @Schema(implementation = RentalDTO.class))),
            @ApiResponse(responseCode = "404", description = "Rental not found")
        }
    )
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
	public ResponseEntity<RentalDTO> getRentalById(@Parameter(
        description = "ID of the rental to retrieve",
        name = "id",
        example = "20"
        )@PathVariable("id") final Integer id) {

		Optional<RentalDTO> rentalDto = rentalService.getRentalById(id);

		if (rentalDto.isPresent()) {
			return ResponseEntity.ok(rentalDto.get());
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}





    @Operation(
        summary = "Add a new rental",
        description = "Create a new rental record with the provided information.",
        responses = {
            @ApiResponse(responseCode = "201", description = "Rental created successfully", content = @Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object>  addNewRental(@Valid @ModelAttribute RentalSubmissionDTO rentalSubmissionDTO ) {
        
        try {
            rentalService.addNewRental(rentalSubmissionDTO);
            return new ResponseEntity<>(Collections.singletonMap("message", "Rental created !"), HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("error", "Error while creating rental: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
     
    }



    @Operation(
        summary = "Update rental by ID",
        description = "Updates an existing rental record with the given ID.",
        responses = {
            @ApiResponse(responseCode = "200", description = "Rental updated successfully"),
            @ApiResponse(responseCode = "404", description = "Rental not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
        }
    )
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> updateRental(@Parameter(
        description = "ID of the rental to update",
        name = "id",
        example = "3"
        )@PathVariable("id") final Integer id,@Valid  @ModelAttribute RentalDTO rentalDTO) {
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
