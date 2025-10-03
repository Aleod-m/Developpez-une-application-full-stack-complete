package com.openclassrooms.mddapi.domain;
import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Setter
@Getter
@NoArgsConstructor
public class TopicDTO {

	Long id;

	String label;

	String description;

}
