package com.example.userservice.config;

import com.example.userservice.service.MyUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;

    // 스프링 시큐리티에서 제공하는 인증, 인가를 위한 필터 모음
    // Application Context 초기화가 이루어 지면서 HttpSecurity 객체가 설정한 filterChain 형성

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(
                        (authorizeRequest) -> authorizeRequest
                                .requestMatchers("/", "/api/users/login", "/api/users/loginProc", "/api/users/join" , "/api/users/joinProc").permitAll()
                                .requestMatchers("/api/boards").hasRole("USER")
                                .anyRequest().authenticated()
                );

        http
                .csrf(AbstractHttpConfigurer::disable);
                // == .csrf((auth) -> auth.disable());
                // CSRF(Cross-Site-Request Forgery 보호를 비활성화하는 메서드 호출
                // AbstractHttpConfigurer::disable -> AbstractHttpConfigurer에 정의된 disable 메소드에 대한 참조

        http
                .formLogin((auth) -> auth.loginPage("/api/users/login")
                        .loginProcessingUrl("/loginProc")
                        .permitAll());

        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
