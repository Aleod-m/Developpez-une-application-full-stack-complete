package com.openclassrooms.mddapi.services;

import org.springframework.stereotype.Service;
import java.util.*;

import com.openclassrooms.mddapi.exceptions.*;
import com.openclassrooms.mddapi.domain.CommentDTO;
import com.openclassrooms.mddapi.domain.entity.Comment;
import com.openclassrooms.mddapi.domain.entity.User;
import com.openclassrooms.mddapi.repositories.CommentRepository;

@Service
public class CommentService {

	final CommentRepository repo;
	final CommentMapper mapper;
	final UserService userSrvc;

	CommentService(CommentRepository repo, CommentMapper mapper, UserService userSrvc) {
		this.repo = repo;
		this.mapper = mapper;
		this.userSrvc = userSrvc;
	}

	public void create(CommentDTO comment) throws AbstractException {
		Comment new_comment = mapper.toEntity(comment);
		User current_user = userSrvc.loggedInUser();
		validateCreate(current_user, new_comment);
		repo.save(new_comment);
	}

	void validateCreate(User current, Comment comment) throws ValidationException {
		List<Error> errors = new ArrayList<Error>();
		if (comment.getId() != null)
			errors.add(new Error("Cannot have create with an id."));

		if (comment.getCommenter() == null)
			comment.setCommenter(current);
		else if (comment.getCommenter().getId() != current.getId())
			errors.add(new Error("Cannot create comment for another user."));

		if (comment.getPost() == null)
			errors.add(new Error("Post missing."));

		if (comment.getContent() == null)
			errors.add(new Error("No content."));

		if (!errors.isEmpty())
			throw new ValidationException(errors);
	}

	public List<CommentDTO> findByPostId(Long postId) {
		return mapper.toDto(repo.findByPostId(postId));
	}
}
