package com.example.bookmaru.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
@MappedSuperclass
public class BaseEntity {
  @CreatedDate
  @Column(nullable = false,updatable = false)
  private LocalDateTime createdAt;
  @LastModifiedDate
  @Column(nullable = false)
  private LocalDateTime updatedAt;

  private Boolean isActive;

}
