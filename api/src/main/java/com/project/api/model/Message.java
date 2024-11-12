package com.project.api.model;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;





@Entity
@Data
@Table(name = "messages")
public class Message {
	
	  	@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;
	    private String message;
	    
	    @ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "user_id", nullable = false )
		private User user;
		
		@ManyToOne(fetch = FetchType.EAGER)
		@JoinColumn(name = "rental_id", nullable = false, referencedColumnName = "id")
		private Rental rental;
	    
	    @CreationTimestamp
		private LocalDateTime created_at;
		
		@UpdateTimestamp
		private LocalDateTime updated_at;
	
		
	
	


}
