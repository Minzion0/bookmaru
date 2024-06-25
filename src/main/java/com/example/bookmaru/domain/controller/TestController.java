package com.example.bookmaru.domain.controller;

import com.example.bookmaru.domain.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TestController {

  private final EmailService emailService;
  @GetMapping
  public ResponseEntity<?>testSwagger(){
    return ResponseEntity.ok().body(null);
  }

  @PostMapping("/email")
  public ResponseEntity<?>emailCode(String email){
    emailService.sendEmail(email);

    return ResponseEntity.ok().body("");
  }

}
