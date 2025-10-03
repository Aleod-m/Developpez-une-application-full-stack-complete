package com.openclassrooms.mddapi.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
public class PostDTO {

	Long id;

	@NonNull
	String title;

	@NonNull
	@JsonProperty("author_id")
	Long authorId;

	@NonNull
	@JsonProperty("topic_id")
	Long topicId;

	@NonNull
	String content;

	@JsonProperty("created_at")
	LocalDateTime createdAt;

}
