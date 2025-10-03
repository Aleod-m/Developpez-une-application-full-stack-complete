package com.openclassrooms.mddapi.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TOPIC")
public class Topic {
	@Id
	@GeneratedValue
	private Long id;

	@NonNull
	private String label;

	@NonNull
	private String description;
}
