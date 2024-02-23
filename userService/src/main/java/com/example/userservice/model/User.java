package com.example.userservice.model;

import com.example.userservice.dto.JoinDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Entity
@Getter
@NoArgsConstructor
public class User {

    @Id
    private String email;

    @Column(nullable = false)
    private String password;

    @Column
    private String role;

    public User(JoinDto joinDto){
        this.email = joinDto.getEmail();
        this.password = joinDto.getPassword();
        this.role = "ROLE_USER";
    }

}
