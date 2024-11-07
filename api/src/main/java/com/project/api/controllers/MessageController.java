package com.project.api.controllers;


import java.util.Collections;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.project.api.dto.MessageDTO;

import com.project.api.services.MessageService;





@RestController
@RequestMapping("/api/messages")
public class MessageController {
    
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Map<String, String>> sendMessage(@RequestBody MessageDTO messageDTO ) {
   
        
       
        messageService.sendMessage(messageDTO);
        return new ResponseEntity<>(Collections.singletonMap("message", "Message send with success"), HttpStatus.CREATED);
      
     
    }

  




}
