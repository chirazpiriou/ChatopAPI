package com.project.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.api.model.Rental;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Integer> {

}
