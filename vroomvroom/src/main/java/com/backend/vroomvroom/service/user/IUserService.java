package com.backend.vroomvroom.service.user;

import com.backend.vroomvroom.dto.user.request.LoginRequestDto;
import com.backend.vroomvroom.dto.user.request.SignUpRequestDto;
import com.backend.vroomvroom.dto.user.request.TokenRequestDto;
import com.backend.vroomvroom.dto.user.response.LoginResponseDto;
import com.backend.vroomvroom.dto.user.response.SignUpResponseDto;
import com.backend.vroomvroom.dto.user.response.TokenResponseDto;

public interface IUserService {

    public SignUpResponseDto registerUser(SignUpRequestDto signUpRequestDto);

    public boolean checkLoginIdDuplicate(String loginId);

    public LoginResponseDto loginUser(LoginRequestDto loginRequestDto);

    public TokenResponseDto reissue(TokenRequestDto tokenRequestDto);

}
