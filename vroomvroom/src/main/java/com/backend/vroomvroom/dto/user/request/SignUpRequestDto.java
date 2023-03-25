package com.backend.vroomvroom.dto.user.request;

import com.backend.vroomvroom.entity.user.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@ToString
public class SignUpRequestDto {

    @NotBlank(message = "아이디는 필수 입력사항 입니다.")
    private String loginId;

    @NotBlank
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[!@#$%^*+=-])(?=.*[0-9]).{4,15}$",
            message= "비밀번호는 4글자 이상, 16글자 미만 그리고 영문, 숫자, 특수문자 포함이어야 합니다.")
    private String password;

    @NotBlank
    @Pattern(regexp = "[a-zA-z0-9]+@[a-zA-z]+[.]+[a-zA-z.]+", message= "올바른 이메일 형식이어야 합니다.")
    private String email;

    @NotBlank
    @Size(min = 6, max = 6, message = "인증코드는 6글자이어야 합니다.")
    private String authCode;

    @NotBlank
    @Size(min = 2, max = 10, message = "닉네임은 2글자 이상 10글자 이하여야 합니다.")
    private String nickname;

    public static UserEntity mapToEntity(SignUpRequestDto signUpRequestDto) {
        return UserEntity.builder()
                .loginId(signUpRequestDto.getLoginId())
                .password(signUpRequestDto.getPassword())
                .email(signUpRequestDto.getEmail())
                .nickname(signUpRequestDto.getNickname())
                .useYn("Y")
                .build();
    }

}
