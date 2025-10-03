package com.openclassrooms.mddapi.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.openclassrooms.mddapi.domain.UserDTO;
import com.openclassrooms.mddapi.services.UserService;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/api/users")
class UserController extends AbstractController {

	final UserService srvc;

  	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	UserController(UserService srvc) {
		this.srvc = srvc;

	}

	@GetMapping("{identifier}")
	ResponseEntity<UserDTO> getById(@PathVariable Long identifier) {
		logger.info("Get user by id: {}", identifier);
		return ResponseEntity.ok(srvc.findById(identifier));
	}

	@PutMapping("{identifier}")
	ResponseEntity<UserDTO> update(@PathVariable Long identifier, @Valid @RequestBody UserDTO userUpdate) {
		logger.info("id: {}", identifier);
		return ResponseEntity.ok(srvc.update(identifier, userUpdate));
	}

}
