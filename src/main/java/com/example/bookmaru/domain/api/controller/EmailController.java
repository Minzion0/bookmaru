package com.example.bookmaru.domain.api.controller;

import com.example.bookmaru.domain.api.model.Email;
import com.example.bookmaru.domain.api.service.EmailService;
import com.example.bookmaru.domain.api.model.EmailCodeDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/signup")
@RequiredArgsConstructor
@Tag(name = "EmailController", description = "이메일 관련 API")
public class EmailController {

  private final EmailService emailService;

  /**
   * 이메일 인증 코드를 요청합니다.
   *
   * @param request 이메일 요청 정보
   * @return 응답 엔티티
   */
  @PostMapping("/email")
  @Operation(summary = "이메일 인증 코드 요청", description = "이메일로 인증 코드를 요청합니다.")
  public ResponseEntity<?> emailCode(
      @Valid @RequestBody Email.Request request
  ) {
    emailService.sendEmail(request);
    return ResponseEntity.ok().body("");
  }

  /**
   * 이메일 인증 코드를 확인합니다.
   *
   * @param request 이메일 코드 확인 요청 정보
   * @return 응답 엔티티
   */
  @PutMapping("/email")
  @Operation(summary = "이메일 인증 코드 확인", description = "이메일로 받은 인증 코드를 확인합니다.")
  public ResponseEntity<?> emailCodeCheck(
      @Valid @RequestBody Email.CodeRequest request
  ) {
    emailService.emailCodeCheck(request);
    return ResponseEntity.ok().body("");
  }

}