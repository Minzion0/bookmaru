package com.example.bookmaru.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity(name = "loan_book")
public class LoanBookEntity extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne
  @JoinColumn(name = "book_id")
  private BookEntity bookEntity;

  @ManyToOne
  @JoinColumn(name = "loan_id")
  private LoanEntity loanEntity;

  private int quantity;

  private Boolean isReturned;
}
