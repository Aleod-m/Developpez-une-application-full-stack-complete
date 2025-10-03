package com.openclassrooms.mddapi.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class CommentDTO {
	Long id;

	@JsonProperty("post_id")
	Long postId;

	@JsonProperty("commenter_id")
	Long commenterId;

	String content;

	@JsonProperty("created_at")
	LocalDateTime createdAt;
}
