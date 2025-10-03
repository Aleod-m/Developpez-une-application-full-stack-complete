package com.openclassrooms.mddapi.exceptions;


import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * ResponseEntityExceptionHandler
 */

@ControllerAdvice
public class RestResponseEntityExceptionHandler  extends ResponseEntityExceptionHandler {

	// 403
	@ExceptionHandler(ValidationException.class)
	protected ResponseEntity<Object> handleEntityNotFoundException(final ValidationException ex,
			final WebRequest request) {
		return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
	}

	// 404
	@ExceptionHandler(NotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFoundException(final NotFoundException ex,
			final WebRequest request) {
		return handleExceptionInternal(ex, null, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
}
