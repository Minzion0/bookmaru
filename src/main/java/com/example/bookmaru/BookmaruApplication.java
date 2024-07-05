package com.example.bookmaru;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;


@SpringBootApplication
@EnableJpaAuditing
public class BookmaruApplication {

  public static void main(String[] args) {
    SpringApplication.run(BookmaruApplication.class, args);
  }

}
