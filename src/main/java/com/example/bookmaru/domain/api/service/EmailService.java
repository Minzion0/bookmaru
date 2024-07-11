package com.example.bookmaru.domain.api.service;

import com.example.bookmaru.domain.api.model.Email;
import com.example.bookmaru.domain.api.model.EmailCodeDto;
import com.example.bookmaru.domain.api.repository.EmailCodeRedisRepository;
import com.example.bookmaru.domain.model.entity.MemberEntity;
import com.example.bookmaru.domain.repository.MemberRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender javaMailSender;
  private final MemberRepository memberRepository;
  private final EmailCodeRedisRepository emailCodeRedisRepository;

  @Transactional
  public String sendEmail(Email.Request request) {

    Optional<MemberEntity> memberEntity = memberRepository.findByEmail(request.getEmailAddr());
    if (memberEntity.isPresent()) {
      throw new RuntimeException("이미 가입된 이메일 입니다.");
    }

    String authNum = createCode();
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();

    try {
      MimeMessageHelper mimeMessageHelper =
          new MimeMessageHelper(mimeMessage, false, "UTF-8");

      EmailCodeDto emailCodeDto = emailCodeRedisRepository.findById(request.getEmailAddr())
          .orElse(new EmailCodeDto(authNum, request.getEmailAddr()));

      if (Objects.equals(emailCodeDto.getIsValid(), "Y")) {
        throw new RuntimeException("이미 인증된 메일 입니다.");
      }
      if (!Objects.equals(emailCodeDto.getEmailCode(), authNum) && Objects.equals(
          emailCodeDto.getIsValid(), "N")) {
        emailCodeDto.setEmailCode(authNum);
      }

      mimeMessageHelper.setTo(request.getEmailAddr());//메일 수신자
      mimeMessageHelper.setSubject("이메일 인증 번호");
      mimeMessageHelper.setText(authNum, false);
      javaMailSender.send(mimeMessage);

      emailCodeRedisRepository.save(emailCodeDto);

      log.info("이메일 발송 성공 : {}", request);
      log.info("code : {}", authNum);
      return authNum;

    } catch (MessagingException e) {
      log.info("이메일 검증 오류");
      throw new RuntimeException(e);
    }
  }

  @Transactional
  public void emailCodeCheck(Email.CodeRequest request) {
    EmailCodeDto emailCodeDto = emailCodeRedisRepository.findById(request.getEmailAddr())
        .orElseThrow(() -> new RuntimeException("찾을수 없습니다."));

    if (!Objects.equals(emailCodeDto.getIsValid(), "N")) {
      throw new RuntimeException("확인 완료된 메일 입니다.");
    }

    if (!Objects.equals(emailCodeDto.getEmailCode(), request.getEmailCode())) {
      throw new RuntimeException("코드가 다름니다.");
    }
    emailCodeDto.setIsValid("Y");
    emailCodeRedisRepository.save(emailCodeDto);
  }

  @Transactional(readOnly = true)
  public void tempPasswordSendEmail(String email, String tempPassword) {
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
    try {

      MimeMessageHelper mimeMessageHelper =
          new MimeMessageHelper(mimeMessage, false, "UTF-8");
      mimeMessageHelper.setTo(email);//메일 수신자
      mimeMessageHelper.setSubject("임시 비밀번호");
      mimeMessageHelper.setText(tempPassword, false);
      javaMailSender.send(mimeMessage);

    }catch (MessagingException e){
      throw new RuntimeException("메일 발송 실패");
    }
  }

  private String createCode() {
    SecureRandom random = new SecureRandom();
    StringBuilder key = new StringBuilder();
    // 8자리의 코드를 생성하기 위한 반복문
    for (int i = 0; i < 8; i++) {
      // 0부터 3까지의 난수를 생성
      int index = random.nextInt(4);

      // index 값에 따라 다른 종류의 문자를 추가
      switch (index) {
        // index가 0인 경우: 소문자 (a-z)
        case 0:
          key.append((char) ((int) random.nextInt(26) + 97));
          break;
        // index가 1인 경우: 대문자 (A-Z)
        case 1:
          key.append((char) ((int) random.nextInt(26) + 65));
          break;
        // index가 2 또는 3인 경우: 숫자 (0-8)
        default:
          key.append(random.nextInt(9));
      }
    }
    return key.toString();
  }
}
