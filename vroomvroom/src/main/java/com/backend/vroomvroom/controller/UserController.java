package com.backend.vroomvroom.controller;

import com.backend.vroomvroom.dto.user.request.SignUpRequestDto;
import com.backend.vroomvroom.dto.user.response.SignUpResponseDto;
import com.backend.vroomvroom.service.user.IEmailService;
import com.backend.vroomvroom.service.user.IUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/vroom/user")
public class UserController {

    private final IEmailService emailService;
    private final IUserService userService;

    @PostMapping("/email-send/{email:.+}")
    @ResponseStatus(HttpStatus.OK)
    public String sendEmail(@PathVariable("email") String email) {
        emailService.authenticationEmail(email);
        return "인증 코드를 발송했습니다.";
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public SignUpResponseDto signup(@Valid @RequestBody SignUpRequestDto signUpRequestDto) {
        log.info("회원가입 요청 정보, signup : {}", signUpRequestDto);

        return userService.registerUser(signUpRequestDto);
    }

    @PostMapping("/signup/duplicateId/{loginId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean duplicateId(@PathVariable("loginId") String loginId) {
        return userService.checkLoginIdDuplicate(loginId);
    }




}
