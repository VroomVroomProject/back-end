package com.backend.vroomvroom.config.security.user;

import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDto {

    private Long userId;

    private String loginId;

    private String password;

    private String email;

    private String nickName;

    private Long money;

    @Builder
    public UserDto(Long userId, String loginId, String password, String email, String nickName, Long money) {
        this.userId = userId;
        this.loginId = loginId;
        this.password = password;
        this.email = email;
        this.nickName = nickName;
        this.money = money;
    }
}
