package com.backend.vroomvroom.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BaseDto {
    private String createUserId;
    private String updateUserId;
    private LocalDateTime createDateTime;
    private LocalDateTime updateDateTime;
    private String useYn;
}
