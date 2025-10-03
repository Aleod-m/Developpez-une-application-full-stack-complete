package com.openclassrooms.mddapi.domain.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "USERS", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "email", "username" })
})
@Data
@Getter
@EqualsAndHashCode(of = { "id" })
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
@ToString
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NonNull
	private String username;

	private boolean admin;

	@NonNull
	@Size(max = 50)
	@Email
	private String email;

	@NonNull
	@Size(max = 120)
	private String password;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "SUBSCRIPTION", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "topic_id"))
	private List<Topic> subscriptions;

	@CreationTimestamp
	@Column(name = "created_at", updatable = false)
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
}
