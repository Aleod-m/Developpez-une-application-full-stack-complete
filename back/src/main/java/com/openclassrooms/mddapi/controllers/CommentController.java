package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.openclassrooms.mddapi.domain.CommentDTO;
import com.openclassrooms.mddapi.services.CommentService;

@Controller
@RequestMapping("/api/comments")
class CommentController extends AbstractController {

	final CommentService srvc;

	CommentController(CommentService srvc) {
		this.srvc = srvc;
	}

	@GetMapping
	ResponseEntity<List<CommentDTO>> list(@RequestParam(required = true, name = "post_id") Long postId) {
		return ResponseEntity.ok(srvc.findByPostId(postId));
	}

	@PostMapping
	ResponseEntity<HttpStatus> create(@RequestBody CommentDTO comment) {
		srvc.create(comment);
		return ResponseEntity.ok(HttpStatus.OK);
	}
}
