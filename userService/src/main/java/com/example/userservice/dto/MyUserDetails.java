package com.example.userservice.dto;

import com.example.userservice.model.User;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

// Spring Security에서 유저의 정보를 담는 객체
@Getter
@Setter
public class MyUserDetails implements UserDetails {

    private User user;

    public MyUserDetails(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //return List.of(new SimpleGrantedAuthority("user"));
        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });

        return collection;
    }
    // 스프링 시큐리티에서 권한을 나타내는 객체를 생성하는 코드
    // List.of : 자바 9에서 추가된 정적 팩토리 메소드
    // "user"라는 권한을 가진 사용자를 나타내는 객체를 생성하고 List에 담아 반환

    @Override
    public String getPassword() {
        return user.getPassword();
    }
    // 사용자의 패스워드 반환
    // 반드시 암호화해서 저장한다.

    @Override
    public String getUsername() {
        return user.getEmail();
    }
    // 사용자를 식별할 수 있는 것. 사용되는 값은 반드시 고유해야 한다

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    // 계정이 만료되었는지 확인. true면 만료되지 않음

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    // 계정이 잠금되었는지 확인. true면 잠금되지 않음

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    // 패스워드가 만료되었는지 확인. true면 만료되지 않음

    @Override
    public boolean isEnabled() {
        return true;
    }
    // 계정이 사용 가능한지 확인. true면 사용 가능
}
