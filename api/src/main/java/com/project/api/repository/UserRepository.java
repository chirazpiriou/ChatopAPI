package com.project.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.api.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

}
