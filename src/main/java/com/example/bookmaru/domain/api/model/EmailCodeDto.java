package com.example.bookmaru.domain.api.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@RedisHash(value = "emailCode",timeToLive = 300)
public class EmailCodeDto implements Serializable {

  private static final long serialVersionUID = 214490344996507077L;

  @Id
  private String emailAddr;
  private String emailCode;
  private String isValid;

  public EmailCodeDto(String emailCode,String emailAddr) {
    this.emailAddr=emailAddr;
    this.emailCode = emailCode;
    this.isValid = "N";
  }
}
