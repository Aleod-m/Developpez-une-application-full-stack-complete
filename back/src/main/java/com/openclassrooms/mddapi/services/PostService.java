package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.exceptions.*;
import com.openclassrooms.mddapi.repositories.PostRepository;

import com.openclassrooms.mddapi.domain.PostDTO;
import com.openclassrooms.mddapi.domain.PostSummary;
import com.openclassrooms.mddapi.domain.entity.Post;
import com.openclassrooms.mddapi.domain.entity.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PostService {

	final PostRepository repo;
	final PostMapper mapper;
	final UserService userSrvc;
	
  	private static final Logger logger = LoggerFactory.getLogger(PostService.class);


	PostService(PostMapper mapper, PostRepository repo, UserService userSrvc) {
		this.repo = repo;
		this.mapper = mapper;
		this.userSrvc = userSrvc;
	}

	public List<PostSummary> findAll() {
		return repo.findAllSummary();
	}

	public PostDTO findById(Long id) {
		return repo.findById(id)
			.map(mapper::toDto)
			.orElseThrow(() -> new NotFoundException(id, Post.class));
	}

	public PostDTO create(PostDTO create) throws AbstractException {
		logger.info("Creating: {}", create.getTitle());
		logger.info("Creating: {}", create.getContent());
		logger.info("Creating: {}", create.getId());
		logger.info("Creating: {}", create.getTopicId());
		logger.info("Creating: {}", create.getAuthorId());

		Post post = mapper.toEntity(create);
		validateCreate(post);
		return mapper.toDto(repo.save(post));
	}

	void validateCreate(Post post) throws ValidationException {
		List<Error> errors = new ArrayList<Error>();

		User current = userSrvc.loggedInUser();
		if (post.getAuthor() != null && current.getId() != post.getAuthor().getId()) {
			errors.add(new Error("Cannot create a new post instead of another user."));
			logger.error("Cannot create a new post instead of another user.");
		}
		if (post.getTopic() == null) {
			errors.add(new Error("Topic not found or missing."));
			logger.error("Topic not found or missing.");
		}
		if (!errors.isEmpty())
			throw new ValidationException(errors);
			

	}
}

