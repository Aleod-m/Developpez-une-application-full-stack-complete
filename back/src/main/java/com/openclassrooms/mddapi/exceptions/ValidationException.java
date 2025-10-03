package com.openclassrooms.mddapi.exceptions;

import java.util.*;

/**
 * ValidationException
 */
public class ValidationException extends AbstractException {

	
	public  ValidationException(List<Error> errors) {
		super(errors);
	}

}
