package com.openclassrooms.mddapi.exceptions;

import java.util.*;


public class AbstractException extends RuntimeException {

	private final List<Error> errors = new ArrayList<>();

	public AbstractException() { }

	public AbstractException(Error error) {
		errors.add(error);
	}

	public AbstractException(List<Error> errors) {
		this.errors.addAll(errors);
	}

}
