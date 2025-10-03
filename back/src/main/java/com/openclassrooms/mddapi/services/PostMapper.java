package com.openclassrooms.mddapi.services;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import java.util.*;

import com.openclassrooms.mddapi.domain.PostDTO;
import com.openclassrooms.mddapi.domain.PostSummary;
import com.openclassrooms.mddapi.domain.entity.Post;

@Component
@Mapper(componentModel = "spring", uses = { EntityIdMapper.class })
public interface PostMapper {
	
	@Mapping(target = "authorId", source = "author", qualifiedByName="toId")
	@Mapping(target = "topicId", source = "topic", qualifiedByName="toId")
    PostDTO toDto(Post entity);

    PostDTO toDto(PostSummary entity);

	@Mapping(target = "authorId", source = "author", qualifiedByName="toId")
	@Mapping(target = "topicId", source = "topic", qualifiedByName="toId")
    List<PostDTO> toDto(List<Post> entity);

	@Mapping(target = "author", source = "authorId", qualifiedByName="toUser")
	@Mapping(target = "topic", source = "topicId", qualifiedByName="toTopic")
	Post toEntity(PostDTO dto);

	@Mapping(target = "author", source = "authorId", qualifiedByName="toUser")
	@Mapping(target = "topic", source = "topicId", qualifiedByName="toTopic")
	List<Post> toEntity(List<PostDTO> dto);
}
