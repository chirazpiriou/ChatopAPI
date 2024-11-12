package com.project.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.project.api.model.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer>{

}
