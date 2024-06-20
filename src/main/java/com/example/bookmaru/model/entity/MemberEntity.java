package com.example.bookmaru.model.entity;

import com.example.bookmaru.model.type.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;

@Entity(name = "member")
public class MemberEntity extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String password;
  @Enumerated(EnumType.STRING)
  private Role role;

  private LocalDate birthdate;

  private String email;
  @OneToMany(mappedBy = "memberEntity",
      cascade = CascadeType.ALL,
      fetch = FetchType.LAZY)
  private List<MemberCategoryEntity> memberCategories;

}
