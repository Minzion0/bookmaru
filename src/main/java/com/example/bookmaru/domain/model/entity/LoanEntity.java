package com.example.bookmaru.domain.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;

@Entity(name = "loan")
public class LoanEntity extends BaseEntity{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "mamber_id")
  private MemberEntity memberEntity;

  private LocalDate loanDate;

  private LocalDate dueDate;

  private LocalDate returnDate;

  private int totalBooks;
}
