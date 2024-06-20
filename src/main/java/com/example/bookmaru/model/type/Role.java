package com.example.bookmaru.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
  ROLE_ADMIN("ADMIN"),
  ROLE_USER("USER");

  private final String role;

}
