package com.example.bookmaru.domain.api.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

public class Email {
  @Data
  public static class Request{
    @NotBlank(message = "필수값 입니다.")
    @jakarta.validation.constraints.Email(message = "유효하지않은 이메일 형식 입니다.")
    private String emailAddr;
  }
  @Data
  public static class CodeRequest{
    @NotBlank(message = "필수값 입니다.")
    @jakarta.validation.constraints.Email(message = "유효하지않은 이메일 형식 입니다.")
    private String emailAddr;
    @NotBlank(message = "필수값 입니다.")
    private String emailCode;
  }
}
