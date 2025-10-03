package com.openclassrooms.mddapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.*;

import com.openclassrooms.mddapi.services.TopicService;
import com.openclassrooms.mddapi.domain.TopicDTO;

@Controller
@RequestMapping("/api/topics")
public class TopicController {
	
	final TopicService srvc;

	TopicController(TopicService srvc) {
		this.srvc = srvc;
	}

	@GetMapping
	ResponseEntity<List<TopicDTO>> getAll() {
		return ResponseEntity.ok(srvc.getAll());
	}

	@GetMapping("{identifier}")
	ResponseEntity<TopicDTO> getById(@PathVariable Long identifier) {
		return ResponseEntity.ok(srvc.findById(identifier));
	}

}
