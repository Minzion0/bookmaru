package com.example.bookmaru.domain.service;

import com.example.bookmaru.domain.api.model.EmailCodeDto;
import com.example.bookmaru.domain.api.repository.EmailCodeRedisRepository;
import com.example.bookmaru.domain.api.service.EmailService;
import com.example.bookmaru.domain.model.Auth;
import com.example.bookmaru.domain.model.entity.MemberEntity;
import com.example.bookmaru.domain.model.type.Role;
import com.example.bookmaru.domain.repository.MemberRepository;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService implements UserDetailsService {

  private final MemberRepository memberRepository;
  private final EmailCodeRedisRepository emailCodeRedisRepository;
  private final PasswordEncoder passwordEncoder;
  private final EmailService emailService;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    log.debug("사용자 이름으로 사용자 로드 시도: {}", username);
    return memberRepository.findByEmail(username)
        .orElseThrow(() -> {
          log.error("사용자를 찾을 수 없음: {}", username);
          return new RuntimeException("존재하지 않습니다.");
        });
  }

  @Transactional()
  public void customerSignup(Auth.Signup request) {
    log.debug("고객 회원가입 요청: {}", request);
    EmailCodeDto emailCodeDto = emailCodeRedisRepository.findById(request.getEmail())
        .orElseThrow(() -> {
          log.error("이메일 확인을 찾을 수 없음: {}", request.getEmail());
          return new RuntimeException("이메일 확인을 먼저 해주세요.");
        });

    if (!Objects.equals(emailCodeDto.getIsValid(), "Y")) {
      log.error("이메일이 확인되지 않음: {}", request.getEmail());
      throw new RuntimeException("이메일 확인을 먼저 해주세요.");
    }

    request.setPassword(passwordEncoder.encode(request.getPassword()));
    MemberEntity memberEntity = MemberEntity.from(request, Role.ROLE_CUSTOMER);

    memberRepository.save(memberEntity);
    emailCodeRedisRepository.delete(emailCodeDto);
    log.info("새 고객 회원가입: {}", request.getEmail());
  }
  @Transactional(readOnly = true)
  public MemberEntity customerSignIn(Auth.SignIn request) {
    log.debug("고객 로그인 요청: {}", request);
    MemberEntity memberEntity = getMemberEntity(request.getEmail());

    if (!passwordEncoder.matches(request.getPassword(), memberEntity.getPassword())) {
      log.error("비밀번호 불일치: {}", request.getEmail());
      throw new RuntimeException("비밀번호가 일치하지 않습니다.");
    }

    log.info("고객 로그인 성공: {}", request.getEmail());
    return memberEntity;
  }

  @Transactional
  public void patchCustomer(Auth.Patch request) {
    log.debug("고객 정보 수정 요청: {}", request);
    MemberEntity memberEntity = getMemberEntity(request.getEmail());

    if (!Objects.equals(memberEntity.getBirthdate(), request.getBirthdate())) {
      log.error("생년월일 불일치: {}", request.getEmail());
      throw new RuntimeException("정보가 일치하지 않습니다.");
    }

    // 임시 비밀번호 생성 로직 (8자리 대소문자와 숫자를 포함)
    String code = generateTemporaryPassword();

    // 생성된 임시 비밀번호를 이메일로 전송
    emailService.tempPasswordSendEmail(request.getEmail(), code);
    log.info("임시 비밀번호 생성 및 이메일 전송: {}", request.getEmail());

    // 회원 엔티티에 임시 비밀번호를 설정하고 저장
    memberEntity.tempPassword(passwordEncoder.encode(code));
  }

  // 8자리의 임시 비밀번호를 생성하는 메서드
  private String generateTemporaryPassword() {
    SecureRandom random = new SecureRandom();
    StringBuilder key = new StringBuilder();
    boolean hasUpper = false, hasLower = false, hasDigit = false;

    // 8자리의 비밀번호를 생성하기 위한 반복문
    for (int i = 0; i < 8; i++) {
      // 0부터 2까지의 난수를 생성
      int index = random.nextInt(3);

      // index 값에 따라 다른 종류의 문자를 추가
      switch (index) {
        // index가 0인 경우: 소문자 (a-z)
        case 0:
          key.append((char) (random.nextInt(26) + 97));
          hasLower = true;
          break;
        // index가 1인 경우: 대문자 (A-Z)
        case 1:
          key.append((char) (random.nextInt(26) + 65));
          hasUpper = true;
          break;
        // index가 2인 경우: 숫자 (0-9)
        case 2:
          key.append(random.nextInt(10));
          hasDigit = true;
          break;
      }
    }

    // 대문자가 포함되지 않은 경우 랜덤한 위치에 대문자 추가
    if (!hasUpper) {
      key.setCharAt(random.nextInt(8), (char) (random.nextInt(26) + 65));
    }
    // 소문자가 포함되지 않은 경우 랜덤한 위치에 소문자 추가
    if (!hasLower) {
      key.setCharAt(random.nextInt(8), (char) (random.nextInt(26) + 97));
    }
    // 숫자가 포함되지 않은 경우 랜덤한 위치에 숫자 추가
    if (!hasDigit) {
      key.setCharAt(random.nextInt(8), (char) (random.nextInt(10) + '0'));
    }

    // 비밀번호를 랜덤하게 섞음
    return shuffleString(key.toString());
  }

  // 주어진 문자열을 랜덤하게 섞는 메서드
  private static String shuffleString(String input) {
    List<Character> characters = new ArrayList<>();
    for (char c : input.toCharArray()) {
      characters.add(c);
    }
    Collections.shuffle(characters, new SecureRandom());
    StringBuilder output = new StringBuilder(characters.size());
    for (char c : characters) {
      output.append(c);
    }
    return output.toString();
  }

  // 이메일로부터 회원 엔티티를 가져오는 메서드
  private MemberEntity getMemberEntity(String email) {
    log.debug("회원 엔티티 로드 시도: {}", email);
    return memberRepository.findByEmail(email)
        .orElseThrow(() -> {
          log.error("회원 엔티티를 찾을 수 없음: {}", email);
          return new RuntimeException("존재하지 않습니다.");
        });
  }
}