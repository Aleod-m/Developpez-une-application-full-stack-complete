package com.openclassrooms.mddapi.domain;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Data
@AllArgsConstructor
public class PostSummary {

	Long id;

	@NonNull
	String title;

	@NonNull
	@JsonProperty("author_name")
	String authorName;

	@NonNull
	@JsonProperty("topic_id")
	Long TopicId;

	@NonNull
	@JsonProperty("topic_name")
	String topicName;

	@NonNull
	String content;

	@JsonProperty("created_at")
	LocalDateTime createdAt;

}
