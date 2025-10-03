package com.openclassrooms.mddapi.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@Table(name = "POST")
public class Post {
	
  	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

  	@NonNull
	@Size(max = 256)
	private String title;

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "topic_id")
	private Topic topic;

    @ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "author_id")
	private User author;

	@Size(max = 2000)
	private String content;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

}
