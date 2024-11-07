package com.project.api.services;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.project.api.dto.MessageDTO;
import com.project.api.model.Message;
import com.project.api.model.Rental;
import com.project.api.model.User;
import com.project.api.repository.MessageRepository;
import com.project.api.repository.RentalRepository;
import com.project.api.repository.UserRepository;


@Service
public class MessageService {

	
private MessageRepository messageRepository;
private RentalRepository rentalRepository;

private UserRepository userRepository;
	
	public MessageService (MessageRepository messageRepository, RentalRepository rentalRepository,  UserRepository userRepository) {
		this.messageRepository = messageRepository;
		this.rentalRepository = rentalRepository;
        this.userRepository = userRepository;
		
	}

	public void sendMessage (MessageDTO messageDTO){
		Message message = new Message();
		message.setMessage(messageDTO.getMessage());
		message.setCreated_at(LocalDateTime.now());
        message.setUpdated_at(LocalDateTime.now());
		User user = userRepository.findById(messageDTO.getUser_id())
			.orElseThrow(() -> new NoSuchElementException("User not found"));;
		Rental rental = rentalRepository.findById(messageDTO.getRental_id())
			.orElseThrow(() -> new NoSuchElementException("Rental not found"));
		message.setUser(user);
		message.setRental(rental);


		messageRepository.save(message);


	}

}
