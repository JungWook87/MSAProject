package com.example.userservice.jwt;

import com.example.userservice.dto.MyUserDetails;
import com.example.userservice.model.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JWTFilter extends OncePerRequestFilter {
        // OncePerRequestFilter 는 요청에 대해 한번만 동작한다

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // request의 해더에 담아진 토큰 정보를 해석하여 유저 정보를 가져와야 한다
        // 이를 JWTUtil이 진행한다

        // 토큰 검증 구현
        // request에서 Authorization 헤더를 찾는다
        String authorization = request.getHeader("Authorization");

        // request 헤더에서 가져온 authorization이 null인지 혹은 Bearer로 시작하는지 확인
        if(authorization == null || !authorization.startsWith("Bearer ")){
            System.out.println("token null");
            
            // filterChain으로 엮여있는 다음 필터에 doFilter를 통해 request와 response를 넘겨준다
            filterChain.doFilter(request, response);

            // 조건에 해당되면 메소드 종료 (필수)
            return;
        }

        // 토큰을 분리하여 소멸 시간 검증
        String token = authorization.split(" ")[1];
        // Bearer 와 뒤의 토큰 정보를 분리하고, 토큰 정보를 받아옴

        // JWTUtil에 구현한 유효기간 검사
        if(jwtUtil.isExpired(token)){

            System.out.println("token expired");

            // filterChain으로 엮여있는 다음 필터에 doFilter를 통해 request와 response를 넘겨준다
            filterChain.doFilter(request, response);

            // 조건에 해당되면 메소드 종료 (필수)
            return;
        }

        // 여기까지 왔으면, 토큰이 있고, 양식도 제대로 되어있고 유효기간도 남아있다는 것
        // 토큰을 기반으로 일시적인 세션을 만들어서 저장한다.
        // 토큰에서 username과 role을 획득한다

        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        // User Entity에 값을 설정하여 생성
        User user = new User();
        user.setEmail(username);
        user.setPassword("tempPassword");
        user.setRole(role);
        // 패스워드의 경우 토큰에 담겨있지 않았는데, 비밀번호도 초기화를 해줘야 한다
        // 비밀번호를 DB에서 매번 요청이 올 때마다 조회를 하면 비효율적이므로 임시로 만들어 초기화

        // MyUserDetails 객체를 만든다.
        MyUserDetails myUserDetails = new MyUserDetails(user);

        // 스프링 시큐리티에 인증 토큰 생성
        Authentication authentication = new UsernamePasswordAuthenticationToken(myUserDetails, null, myUserDetails.getAuthorities());

        // 세션에 사용자 등록
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);

        // 검증 필터를 만들었다면 SecurityConfig에 가서 필터를 등록해주자 
    }

}
