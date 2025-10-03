package com.openclassrooms.mddapi.domain.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.*;

import lombok.*;

@Data
@AllArgsConstructor
public class SignupRequest {
  @NotBlank
  @Size(max = 50)
  @Email
  private String email;

  @NotBlank
  @Size(max = 50)
  @JsonProperty("user_name")
  private String userName;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;
}
