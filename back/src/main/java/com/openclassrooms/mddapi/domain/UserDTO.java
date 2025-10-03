package com.openclassrooms.mddapi.domain;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

	Long id;

    @Size(max = 20)
	String username;

    @Size(max = 50)
    @Email
	String email;

	boolean admin;

	// Cannot be sent by the api only recieved.
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Size(max = 120)
    private String password;
	
	@JsonProperty("subscription_ids")
	List<Long> subscriptionIds;

	LocalDateTime createdAt;

	LocalDateTime updatedAt;
}
