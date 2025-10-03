package com.openclassrooms.mddapi.exceptions;

/**
 * NotFoundException
 */
public class NotFoundException extends AbstractException {

	public NotFoundException(Long id, Class<?>... object_class) {
		super(new Error(object_class.getClass().getName() + " not found for id: " + id));
	}

}
