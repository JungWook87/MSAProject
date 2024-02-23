package com.example.userservice.controller;

import com.example.userservice.dto.JoinDto;
import com.example.userservice.dto.ResponseUserDto;
import com.example.userservice.dto.loginDto;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Collection;
import java.util.Iterator;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private ModelAndView mav;

    // 로그인 페이지
    @GetMapping("/login")
    public ModelAndView loginP(){
        mav = new ModelAndView("login");
        return mav;
    }

    @PostMapping("/loginProc")
    public ModelAndView loginProccess(loginDto loginDto){
        ResponseUserDto responseUserDto =  userService.loginProccess(loginDto);

        mav = new ModelAndView("userMain");
        return mav;
    }

    // 회원가입 페이지
    @GetMapping("/join")
    public ModelAndView joinP(){
        mav = new ModelAndView("join");
        return mav;
    }

    // 회원가입
    @PostMapping("/joinProc")
    public ModelAndView joinProccess(JoinDto joinDto){

        userService.joinProccess(joinDto);

        mav = new ModelAndView("login");
        return mav;
    }
}
