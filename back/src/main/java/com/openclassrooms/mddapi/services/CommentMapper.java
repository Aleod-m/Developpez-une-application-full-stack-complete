package com.openclassrooms.mddapi.services;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import java.util.*;

import com.openclassrooms.mddapi.domain.CommentDTO;
import com.openclassrooms.mddapi.domain.entity.Comment;

@Component
@Mapper(componentModel = "spring", uses = {EntityIdMapper.class})
public interface CommentMapper {

	@Mapping(target = "postId", source = "post", qualifiedByName="toId")
	@Mapping(target = "commenterId", source = "commenter", qualifiedByName="toId")
    CommentDTO toDto(Comment entity);

	@Mapping(target = "post", source = "postId", qualifiedByName="toPost")
	@Mapping(target = "commenter", source = "commenterId", qualifiedByName="toUser")
    Comment toEntity(CommentDTO entity);

	@Mapping(target = "postId", source = "post", qualifiedByName="toId")
	@Mapping(target = "commenterId", source = "commenter", qualifiedByName="toId")
    List<CommentDTO> toDto(List<Comment> entity);

	@Mapping(target = "post", source = "postId", qualifiedByName="toPost")
	@Mapping(target = "commenter", source = "commenterId", qualifiedByName="toUser")
    List<Comment> toEntity(List<CommentDTO> entity);

}
