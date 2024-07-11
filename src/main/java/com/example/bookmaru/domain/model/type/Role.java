package com.example.bookmaru.domain.model.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
  ROLE_ADMIN("ADMIN"),
  ROLE_CUSTOMER("CUSTOMER");

  private final String role;

}
