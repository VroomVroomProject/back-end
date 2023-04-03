package com.backend.vroomvroom.dto.user.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@ToString
public class LoginRequestDto {

    @NotBlank(message = "아이디가 존재하지 않습니다.")
    private String loginId;

    @NotBlank(message = "비밀번호가 일치하지 않습니다.")
    private String password;

}
