package com.example.userservice.config;

import com.example.userservice.jwt.JWTFilter;
import com.example.userservice.jwt.JWTUtil;
import com.example.userservice.jwt.LoginFilter;
import com.example.userservice.service.MyUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig {

    private final MyUserDetailsService myUserDetailsService;

    private final AuthenticationConfiguration authenticationConfiguration;

    private final JWTUtil jwtUtil;

    // 로그인필터의 생성자를 위해
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception{
        return configuration.getAuthenticationManager();
    }


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    // 스프링 시큐리티에서 제공하는 인증, 인가를 위한 필터 모음
    // Application Context 초기화가 이루어 지면서 HttpSecurity 객체가 설정한 filterChain 형성

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // JWT를 사용하기 위해 로그인 방식 두가지 disable
        http
                .formLogin(AbstractHttpConfigurer::disable);
        // == formLogin((auth) -> auth.disable());

        http
                .httpBasic(AbstractHttpConfigurer::disable);

        http
                .csrf(AbstractHttpConfigurer::disable);
        // == .csrf((auth) -> auth.disable());
        // CSRF(Cross-Site-Request Forgery 보호를 비활성화하는 메서드 호출
        // AbstractHttpConfigurer::disable -> AbstractHttpConfigurer에 정의된 disable 메소드에 대한 참조

        http
                .authorizeHttpRequests(
                        (authorizeRequest) -> authorizeRequest
                                .requestMatchers("/", "/api/users/login", "/api/users/loginProc", "/api/users/join" , "/api/users/joinProc").permitAll()
                                .requestMatchers("/api/users/admin").hasRole("ADMIN")
                                .anyRequest().authenticated()
                );

        // jwt 구현으로 인해 수동적으로 loginFilter를 만들었기 때문에 시큐리티에서 filter를 인식하도록 해야함
        // 필터등록은 addFilter가 있는데
        // addFilterAt() 은 그 자리에 등록 , addFilterAfter()는 특정 필터 뒤에 등록 , addFilterBefore() 특정 필터 전에 등록
        // 첫번째 인자는 무슨 필터를, 두번째 인자는 위치
        // UsernamePasswordAuthenticationFilter의 역할을 수동적으로 만들어줬기 때문에 위치가 여기가 됨

        // 토큰 검증 필터 등록
        http
                .addFilterBefore(new JWTFilter(jwtUtil), LoginFilter.class);

        LoginFilter loginFilter = new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil);
        loginFilter.setFilterProcessesUrl("/api/users/loginProc");
        http
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);


        /*
        JWT가 없는 상태에서 시큐리티만 적용 , form 로그인을 통한 로그인
        JWT를 사용하기 위해 주석 처리
        http
                .formLogin((auth) -> auth.loginPage("/api/users/login")
                        .loginProcessingUrl("/loginProc")
                        .permitAll());
        */

        // 세션 설정. STATELESS 상태로 변경
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }

}
