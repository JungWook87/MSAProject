package com.example.userservice.jwt;

import com.example.userservice.dto.MyUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {
// UsernamePasswordAuthenticationFilter는 원래 HttpSecurity#formLogin에서 진행을 했는데
// jwt를 위해 formLogin을 disable 시켰기 때문에 개발자가 구현해줘야 한다

    private final AuthenticationManager authenticationManager;

    // JWTUtil 클래스를 통해서 로그인이 성공했을 때 토큰 발급
    // SecurityConfig에 가서 LoginFilter 생성자에 JWTUtil 파라미터도 추가해주어야 한다
    private final JWTUtil jwtUtil;

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
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        MyUserDetails myUserDetails = (MyUserDetails) authentication.getPrincipal();
        String username = myUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();

        String role = auth.getAuthority();

        String token = jwtUtil.createJwt(username, role, 60*60*10L);

        response.addHeader("Authorization", "Bearer " + token);
        // 위의 HTTP인증방식은 RFC 7235 정의에 의해서 공식적으로 형식을 지정해줌
        // Authorization: 타입 인증토큰
        // Authorization: Bearer 인증토큰string
        // "Bearer "의 경우 공백을 끝에 붙여줘야 함
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(401);
    }
}
