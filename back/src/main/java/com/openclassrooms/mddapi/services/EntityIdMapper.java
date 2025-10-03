package com.openclassrooms.mddapi.services;


import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.openclassrooms.mddapi.domain.entity.Comment;
import com.openclassrooms.mddapi.domain.entity.Post;
import com.openclassrooms.mddapi.domain.entity.Topic;
import com.openclassrooms.mddapi.domain.entity.User;
import com.openclassrooms.mddapi.repositories.CommentRepository;
import com.openclassrooms.mddapi.repositories.PostRepository;
import com.openclassrooms.mddapi.repositories.TopicRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;

@Component
@Mapper(componentModel = "spring")
public abstract class EntityIdMapper {

	@Autowired
	UserRepository userRepo;

	@Autowired
	PostRepository postRepo;

	@Autowired
	CommentRepository commentRepo;

	@Autowired
	TopicRepository topicRepo;

	@Named("toId")
    public Long toId(User user) {
		return user.getId();
    }

	@Named("toId")
    public Long toId(Post post) {
		return post.getId();
    }

	@Named("toId")
    public Long toId(Comment comment) {
		return comment.getId();
    }

	@Named("toId")
    public Long toId(Topic topic) {
		return topic.getId();
    }

	@Named("toUser")
    public User toUser(Long id) {
		return id != null ? userRepo.findById(id).orElse(null) : null;
    }

	@Named("toPost")
    public Post toPost(Long id) {
		return id != null ? postRepo.findById(id).orElse(null) : null;
    }

	@Named("toComment")
    public Comment to(Long id) {
		return id != null ? commentRepo.findById(id).orElse(null) : null;
    }

	@Named("toTopic")
    public Topic toTopic(Long id) {
		return id != null ? topicRepo.findById(id).orElse(null) : null;
    }
}
