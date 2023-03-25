package com.backend.vroomvroom.dto.user.response;

import com.backend.vroomvroom.entity.user.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpResponseDto {

    private String loginId;

    private String email;

    private String nickname;

    @Builder
    public SignUpResponseDto(String loginId, String email, String nickname) {
        this.loginId = loginId;
        this.email = email;
        this.nickname = nickname;
    }

    public static SignUpResponseDto mapToDto(UserEntity userEntity) {
        return SignUpResponseDto.builder()
                .loginId(userEntity.getLoginId())
                .email(userEntity.getEmail())
                .nickname(userEntity.getNickname())
                .build();
    }

}
