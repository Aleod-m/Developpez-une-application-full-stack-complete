package com.openclassrooms.mddapi.controllers;

import java.util.Optional;
import java.util.function.Function;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;


public class AbstractController {
	public <T> ResponseEntity<T> responseFromOptional(Optional<T> object) {
		return object.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
	}
	
	public <T> ResponseEntity<T> responseFromOptional(Optional<T> object, HttpStatusCode responseCode) {
		return object.map(ResponseEntity::ok).orElse(ResponseEntity.status(responseCode).build());
	}

	public <T, U> ResponseEntity<U> responseFromOptional(Optional<T> object, Function<T, ResponseEntity<U>> builder) {
		return object.map(builder).orElse(ResponseEntity.notFound().build());
	}

}
