package com.openclassrooms.mddapi.domain.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {

  private String token;

  @Builder.Default
  private final String type = "Bearer";

  private Long id;

  private String username;

  private Boolean admin;

}
