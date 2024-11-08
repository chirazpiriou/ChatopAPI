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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;




@Tag (name ="Message Controller")
@RestController
@RequestMapping("/api/messages")
public class MessageController {
    
    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

     @Operation(
        summary = "Send a message",
        description = "This endpoint allows the user to send a message. ",
        responses = {
            @ApiResponse(responseCode = "201", description = "Message successfully sent", content = @io.swagger.v3.oas.annotations.media.Content(mediaType = "application/json")),
            @ApiResponse(responseCode = "400", description = "Bad Request - Invalid message data"),
            
        }
    )
    @PostMapping("")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ResponseEntity<Map<String, String>> sendMessage(@RequestBody MessageDTO messageDTO ) {
   
        try {
            messageService.sendMessage(messageDTO);
            return new ResponseEntity<>(Collections.singletonMap("message", "Message send with success"), HttpStatus.CREATED);

        }catch (Exception e) {
            return new ResponseEntity<>(Collections.singletonMap("message", "Failed to send message"), HttpStatus.BAD_REQUEST);
        }
       
        
      
     
    }

  




}
