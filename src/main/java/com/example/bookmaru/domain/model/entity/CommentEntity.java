package com.example.bookmaru.domain.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "comment")
public class CommentEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "member_id")
  private MemberEntity memberEntity;

  @ManyToOne
  @JoinColumn(name = "review_id")
  private ReviewEntity reviewEntity;

  private String content;

  private Long parentComment;


  public CommentEntity() {
  }
}
