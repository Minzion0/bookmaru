package com.example.bookmaru.domain.controller;

import com.example.bookmaru.domain.model.Auth;
import com.example.bookmaru.domain.model.entity.MemberEntity;
import com.example.bookmaru.domain.model.type.Role;
import com.example.bookmaru.domain.service.AuthService;
import com.example.bookmaru.security.TokenProvider;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Tag(name = "AuthController", description = "인증 관련 API")
public class AuthController {

  private final AuthService authService;
  private final TokenProvider tokenProvider;

  /**
   * 고객 회원가입 요청을 처리합니다.
   *
   * @param request 회원가입 요청 정보
   * @return 응답 엔티티
   */
  @PostMapping("/signup")
  @Operation(summary = "회원가입", description = "고객의 회원가입 요청을 처리합니다.")
  public ResponseEntity<?> signup(@Valid @RequestBody Auth.Signup request) {
    log.debug("회원가입 요청: {}", request);
    authService.customerSignup(request);
    log.info("회원가입 성공: {}", request.getEmail());
    return ResponseEntity.ok("");
  }

  /**
   * 고객 로그인 요청을 처리합니다.
   *
   * @param request 로그인 요청 정보
   * @return JWT 토큰을 포함한 응답 엔티티
   */
  @PostMapping("/signin")
  @Operation(summary = "로그인", description = "고객의 로그인 요청을 처리합니다.")
  public ResponseEntity<?> signIn(@Valid @RequestBody Auth.SignIn request) {
    log.debug("로그인 요청: {}", request);
    MemberEntity memberEntity = authService.customerSignIn(request);

    String token = tokenProvider
        .generateToken(memberEntity.getEmail(), List.of(Role.ROLE_CUSTOMER.getRole()));
    log.info("로그인 성공: {}", request.getEmail());

    return ResponseEntity.ok(token);
  }

  /**
   * 고객 정보 수정 요청을 처리합니다.
   *
   * @param request 고객 정보 수정 요청 정보
   * @return 응답 엔티티
   */
  @PatchMapping("/signin")
  @Operation(summary = "고객 정보 수정", description = "고객의 정보 수정 요청을 처리합니다.")
  public ResponseEntity<?> patchCustomer(@Valid @RequestBody Auth.Patch request) {
    log.debug("고객 정보 수정 요청: {}", request);
    authService.patchCustomer(request);
    log.info("고객 정보 수정 성공: {}", request.getEmail());
    return ResponseEntity.ok("");
  }
}