package com.dooDoo.dooDoo.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())               // Swagger에서 POST 테스트 되게
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().permitAll()               // 모든 요청 허용 (로그인 페이지 끔)
                )
                .formLogin(form -> form.disable())          // 기본 로그인 폼 끄기
                .httpBasic(basic -> basic.disable());
        return http.build();
    }
}
