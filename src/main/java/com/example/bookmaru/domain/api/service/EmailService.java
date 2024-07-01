package com.example.bookmaru.domain.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import java.security.SecureRandom;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

  private final JavaMailSender javaMailSender;

  public String sendEmail(String email){
    String authNum = createCode();
    MimeMessage mimeMessage = javaMailSender.createMimeMessage();

    try{
      MimeMessageHelper mimeMessageHelper =
          new MimeMessageHelper(mimeMessage,false,"UTF-8");

      mimeMessageHelper.setTo(email);//메일 수신자
      mimeMessageHelper.setSubject("이메일 검즘 번호");
      mimeMessageHelper.setText(authNum,false);
      javaMailSender.send(mimeMessage);

      log.info("이메일 발송 성공 : {}",email);
      log.info("code : {}",authNum);
      return authNum;

    }catch (MessagingException e){
      log.info("이메일 검증 오류");
      throw new RuntimeException(e);
    }
  }

  public String createCode(){
    SecureRandom random = new SecureRandom();
    StringBuilder key = new StringBuilder();
    // 8자리의 코드를 생성하기 위한 반복문
    for (int i = 0; i < 8; i++) {
      // 0부터 3까지의 난수를 생성
      int index = random.nextInt(4);

      // index 값에 따라 다른 종류의 문자를 추가
      switch (index){
        // index가 0인 경우: 소문자 (a-z)
        case 0: key.append((char) ((int) random.nextInt(26) + 97)); break;
        // index가 1인 경우: 대문자 (A-Z)
        case 1: key.append((char) ((int) random.nextInt(26) + 65)); break;
        // index가 2 또는 3인 경우: 숫자 (0-8)
        default: key.append(random.nextInt(9));
      }
    }
    return key.toString();
  }




}
