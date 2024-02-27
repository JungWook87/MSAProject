package com.example.userservice.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
// UsernamePasswordAuthenticationFilter는 원래 HttpSecurity#formLogin에서 진행을 했는데
// jwt를 위해 formLogin을 disable 시켰기 때문에 개발자가 구현해줘야 한다

    private final AuthenticationManager authenticationManager;

    @Override
    // 미승인 Authentication 생성하는 메소드
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException{

        // 요청을 가로채서 username과 password를 추출
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        System.out.println("username : " + username);

        // 스프링 시큐리티에서 username과 password를 검증하기 위해서는 token에 담아야 한다
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);
                                                                                            // username, password, role
        // token에 담은 검증을 AuthenticationManager로 전달
        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.out.println("로그인 성공");
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        System.out.println("로그인 실패");
    }
}
