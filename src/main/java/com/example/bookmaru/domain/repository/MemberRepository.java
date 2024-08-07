package com.example.bookmaru.domain.repository;

import com.example.bookmaru.domain.model.entity.MemberEntity;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity,Long> {
  Optional<MemberEntity> findByEmail(String email);

}
