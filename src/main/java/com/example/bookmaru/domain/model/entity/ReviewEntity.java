package com.example.bookmaru.domain.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "review")
public class ReviewEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private MemberEntity memberEntity;

  @ManyToOne
  @JoinColumn(name = "book_id")
  private BookEntity bookEntity;

  private String content;

  private Double StarRating;
}
