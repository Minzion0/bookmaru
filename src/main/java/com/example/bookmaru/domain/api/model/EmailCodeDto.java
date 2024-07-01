package com.example.bookmaru.domain.api.model;

import lombok.Data;

public class Email {

  @Data
  public static class Sand {
    private String to;//수신자
    private String subject;
    private String content;//메일 내용

  }

}
