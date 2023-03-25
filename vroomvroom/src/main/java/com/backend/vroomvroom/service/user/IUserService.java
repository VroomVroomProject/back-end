package com.backend.vroomvroom.service.user;

import com.backend.vroomvroom.dto.user.request.SignUpRequestDto;
import com.backend.vroomvroom.dto.user.response.SignUpResponseDto;

public interface IUserService {

    public SignUpResponseDto registerUser(SignUpRequestDto signUpRequestDto);

    public boolean checkLoginIdDuplicate(String loginId);
}
