package com.example.userservice.service;

import com.example.userservice.dto.JoinDto;
import com.example.userservice.dto.ResponseUserDto;
import com.example.userservice.dto.loginDto;
import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void joinProccess(JoinDto joinDto) {
        boolean check = userRepository.existsByEmail(joinDto.getEmail());
        if(check) return;

        joinDto.setPassword(bCryptPasswordEncoder.encode(joinDto.getPassword()));
        User user = new User(joinDto);

        userRepository.save(user);
    }

    @Override
    public ResponseUserDto loginProccess(loginDto loginDto) {
        User user = userRepository.findById(loginDto.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("user not found")
        );

        String providerPassword = loginDto.getPassword();
        if(bCryptPasswordEncoder.matches(providerPassword, user.getPassword())){
            try{
                ResponseUserDto responseUserDto = new ResponseUserDto();
                responseUserDto.setEmail(user.getEmail());
                responseUserDto.setRole(user.getRole());
                return responseUserDto;
            } catch (Exception e){
                e.printStackTrace();
            }
        }

        return null;
    }
}
