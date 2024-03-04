package com.example.userservice.controller;

import com.example.userservice.dto.JoinDto;
import com.example.userservice.dto.LoginDto;
import com.example.userservice.dto.ResponseUserDto;
import com.example.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;


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
    public ModelAndView loginProccess(LoginDto loginDto){
        ResponseUserDto responseUserDto =  userService.loginProccess(loginDto);

        String role = responseUserDto.getRole();

        mav = new ModelAndView();

        if(role.equals("ROLE_USER")) mav.setViewName("userMain");
        else if(role.equals("ROLE_ADMIN")) mav.setViewName("adminMain");

        return mav;
    }

    @GetMapping("/admin")
    public ModelAndView adminP(){
        mav = new ModelAndView("adminMain");
        return mav;
    }

    @GetMapping("/main")
    public ModelAndView mainP(){
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
