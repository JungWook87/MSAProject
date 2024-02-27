package com.example.userservice.service;

import com.example.userservice.dto.JoinDto;
import com.example.userservice.dto.ResponseUserDto;
import com.example.userservice.dto.LoginDto;

public interface UserService {

    void joinProccess(JoinDto joinDto);

    ResponseUserDto loginProccess(LoginDto loginDto);
}
