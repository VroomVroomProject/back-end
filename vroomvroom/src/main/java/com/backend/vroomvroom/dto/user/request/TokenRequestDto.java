package com.backend.vroomvroom.dto.user.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TokenRequestDto {

    @NotBlank
    private String accessToken;

    @NotBlank
    private String refreshToken;

}
