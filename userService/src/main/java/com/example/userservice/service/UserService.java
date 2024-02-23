package com.example.userservice.service;

import com.example.userservice.dto.JoinDto;
import com.example.userservice.dto.ResponseUserDto;
import com.example.userservice.dto.loginDto;

public interface UserService {

    void joinProccess(JoinDto joinDto);

    ResponseUserDto loginProccess(loginDto loginDto);
}
