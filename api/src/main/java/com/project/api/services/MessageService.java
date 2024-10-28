package com.project.api.services;

import org.springframework.stereotype.Service;

import com.project.api.repository.MessageRepository;


@Service
public class MessageService {
	
private MessageRepository messageRepository;
	
	public MessageService (MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
		
	}

}
