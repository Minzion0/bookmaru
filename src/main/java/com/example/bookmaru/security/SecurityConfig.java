package com.example.bookmaru.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

  private final TokenProvider tokenProvider;
  //시큐리티 설정
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

    http.csrf(AbstractHttpConfigurer::disable)
        .formLogin(Customizer.withDefaults())
        .authorizeHttpRequests(authorizeRequest ->
            authorizeRequest
                //로그인 부분 모두 접근가능
                .requestMatchers(
                  "/**"
                ).permitAll()
                .anyRequest().authenticated()
        ).headers(
            headersConfigurer ->
                headersConfigurer.frameOptions(
                    HeadersConfigurer.FrameOptionsConfig::sameOrigin
                )
        );
    http.addFilterBefore(new JwtAuthenticationFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  /**
   * 필터를 거치지 않는 요청 설정
   *
   * @return
   */
  @Bean
  public WebSecurityCustomizer webSecurityCustomizer() {
    return (web) -> web.ignoring().requestMatchers(
        PathRequest.toStaticResources().atCommonLocations()
    );
  }
}
