package com.project.api.services;

import org.springframework.stereotype.Service;

import com.project.api.repository.MessageRepository;
import com.project.api.repository.RentalRepository;

@Service
public class MessageService {
	
private MessageRepository messageRepository;
	
	public MessageService (MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
		
	}

}