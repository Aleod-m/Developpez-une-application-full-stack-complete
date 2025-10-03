package com.openclassrooms.mddapi.services;

import org.springframework.stereotype.Service;
import java.util.*;

import com.openclassrooms.mddapi.exceptions.*;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.domain.TopicDTO;
import com.openclassrooms.mddapi.domain.entity.Topic;

@Service
public class TopicService {

	final TopicRepository repo;
	final TopicMapper mapper;

	TopicService(TopicRepository repo, TopicMapper mapper) {
		this.repo = repo;
		this.mapper = mapper;
	}


	public List<TopicDTO> getAll() {
		return mapper.toDto(repo.findAll());
	}

	public TopicDTO findById(Long id) throws AbstractException {
		return repo.findById(id)
			.map(mapper::toDto)
			.orElseThrow(() -> new NotFoundException(id, Topic.class));
	}

}

