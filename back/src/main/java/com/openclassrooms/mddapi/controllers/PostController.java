package com.openclassrooms.mddapi.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.openclassrooms.mddapi.services.PostService;

import jakarta.validation.Valid;

import com.openclassrooms.mddapi.domain.PostDTO;
import com.openclassrooms.mddapi.domain.PostSummary;

@Controller
@RequestMapping("/api/posts")
class PostController extends AbstractController {
	
	private final PostService srvc;

	PostController(PostService srvc) {
		this.srvc = srvc;
	}

	@GetMapping
	ResponseEntity<List<PostSummary>> getAll() {
		return ResponseEntity.ok(srvc.findAll());
	}

	@PostMapping
	ResponseEntity<PostDTO> create(@Valid @RequestBody PostDTO body) {
		PostDTO post = srvc.create(body);
		return ResponseEntity.ok(post);
	}

	@GetMapping("{identifier}")
	ResponseEntity<PostDTO> getById(@PathVariable Long identifier) {
		return ResponseEntity.ok(srvc.findById(identifier));
	}

}
