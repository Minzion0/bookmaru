package com.example.bookmaru.domain.model.entity;

import com.example.bookmaru.domain.model.Auth;
import com.example.bookmaru.domain.model.type.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Entity(name = "member")
public class MemberEntity extends BaseEntity implements UserDetails {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  private String password;
  @Enumerated(EnumType.STRING)
  private Role role;
  @Getter
  private LocalDate birthdate;
  @Column(unique = true)
  @Getter
  private String email;


  private MemberEntity(String name, String password, Role role, LocalDate birthdate, String email) {
    this.name = name;
    this.password = password;
    this.role = role;
    this.birthdate = birthdate;
    this.email = email;
  }

  public static MemberEntity from(Auth.Signup request, Role role) {
    return new MemberEntity(request.getName(), request.getPassword(), role, request.getBirthdate(),
        request.getEmail());

  }

  public MemberEntity tempPassword(String tempPassword){
    this.password=tempPassword;
    return this;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(new SimpleGrantedAuthority(this.role.getRole()));

  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.email;
  }

}
