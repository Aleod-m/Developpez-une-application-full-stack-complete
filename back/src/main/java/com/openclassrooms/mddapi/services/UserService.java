package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.domain.entity.User;
import com.openclassrooms.mddapi.exceptions.*;
import com.openclassrooms.mddapi.config.services.UserDetailsImpl;
import com.openclassrooms.mddapi.domain.UserDTO;
import com.openclassrooms.mddapi.repositories.UserRepository;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerErrorException;

@Service
public class UserService {

	final AuthenticationManager authenticationManager;
	final PasswordEncoder passwordEncoder;
	final UserRepository repo;
	final UserMapper mapper;

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	UserService(UserRepository repo, UserMapper mapper, AuthenticationManager authenticationManager,
			PasswordEncoder passwordEncoder) {
		this.authenticationManager = authenticationManager;
		this.passwordEncoder = passwordEncoder;
		this.repo = repo;
		this.mapper = mapper;
	}

	public UserDTO findById(Long id) throws AbstractException {
		return repo.findById(id)
				.map(mapper::toDto)
				.orElseThrow(() -> {
					logger.error("User not found. id: ", id);
					return new NotFoundException(id, User.class);
				});
	}

	public User loggedInUser() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication == null)
			throw new ServerErrorException("The handler assumes a user to be logged in.", null);

		UserDetailsImpl loggedInDetails = (UserDetailsImpl) authentication.getPrincipal();
		return repo.findByEmail(loggedInDetails.getUsername())
				.orElseThrow(() -> new ServerErrorException("The handler assumes an inexistent user to be logged in.",
						null));
	}

	public UserDTO update(Long id, UserDTO user_dto) throws AbstractException {
		User update = repo.findById(id)
				.map(user -> {
					mapper.updateEntityFromDTO(user_dto, user);
					return user;
				})
				.orElseThrow(() -> new NotFoundException(id, User.class));

		User loggedIn = loggedInUser();
		validateUpdate(loggedIn, update);

		String passwordUpdate = update.getPassword();
		if (passwordUpdate != null)
			update.setPassword(passwordEncoder.encode(passwordUpdate));

		repo.save(update);
		return user_dto;
	}

	public void validateUpdate(User loggedIn, User update)
			throws ValidationException {
		List<Error> errors = new ArrayList<Error>();

		if (!loggedIn.isAdmin()) {
			// Check privilege escalation.
			if (update.isAdmin())
				errors.add(new Error("Cannot escalate privileges"));

			// Check non-admin other user data modification.
			if (loggedIn.getId() != update.getId())
				errors.add(new Error("Cannot modify other users data."));
		}

		// Check auto-updated dates.
		if (!update.getCreatedAt().equals(loggedIn.getCreatedAt()))
			errors.add(new Error("Cannot modify creation date."));

		if (!update.getUpdatedAt().equals(loggedIn.getUpdatedAt()))
			errors.add(new Error("Cannot modify modification date."));

		// Check if we found errors.
		if (!errors.isEmpty())
			throw new ValidationException(errors);
	}

}
