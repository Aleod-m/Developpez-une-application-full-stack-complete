package com.openclassrooms.mddapi.domain.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePostRequest {

	String title;

	Long topicId;

	Long authorId;
	
	String content;

}
